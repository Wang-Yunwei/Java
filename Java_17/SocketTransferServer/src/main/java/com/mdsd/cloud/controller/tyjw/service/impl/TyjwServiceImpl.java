package com.mdsd.cloud.controller.tyjw.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.enums.CommandEnum;
import com.mdsd.cloud.enums.ServerEnum;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.enums.TyjwReturnCodeEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author WangYunwei [2024-09-03]
 */
@Slf4j
@Service
public class TyjwServiceImpl implements ITyjwService {

    private final EApiFeign feign;
    private final IHangarService hangarService;
    private final ApplicationEventPublisher publisher;

    public TyjwServiceImpl(EApiFeign feign, ApplicationEventPublisher publisher, IHangarService hangarService) {
        this.feign = feign;
        this.publisher = publisher;
        this.hangarService = hangarService;
    }

    private final AuthSingleton auth = AuthSingleton.getInstance();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final JsonFormat.Printer printer = JsonFormat.printer();

    private final ObjectMapper obm = new ObjectMapper();

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    private final ConcurrentHashMap<String, WsChannelDetails> wsChannels = new ConcurrentHashMap<>();

    @Value("${env.ip.tyjw}")
    private String host;

    @Value("${env.port.tyjw.tcp}")
    private int tcpPort;

    @Value("${env.port.sts.web_socket_server}")
    private int wsPort;

    private String batteryPower;

    private Channel tcpChannel;

    private static final String topic = "STS/%s/PUBLISH/%s";

    @FunctionalInterface
    private interface WebSocketFunction<T1, T2, T3> {
        void dataHandle(T1 arg1, T2 arg2, T3 arg3);
    }

    private <T> T handleAuth(Supplier<T> supplier) {
        if (auth.getCompanyId() == null || !StringUtils.isNoneBlank(auth.getAccessToken())) {
            getToken(new GetTokenInp());
        }
        return supplier.get();
    }

    private <T> T processResult(ResponseTy<T> result) {
        if (result.getState() == 0) {
            return result.getContent();
        } else {
            auth.setAccessToken(null);
            throw new BusinessException(String.valueOf(result.getState()), result.getState() == -201 ? result.getMessage() + "(后台系统将在3分钟后自动尝试重新登录)" : result.getMessage());
        }
    }

    private void publishEvent(JsonNode jsonNode) {
        publisher.publishEvent(new CommonEvent(ServerEnum.MDSD_WEB_SOCKET, jsonNode));
    }

    private void publishEvent(ByteBuf byteBuf) {
        publisher.publishEvent(new CommonEvent(ServerEnum.TYJW_TCP_CLIENT, byteBuf));
    }

    private void sendMessage(String key, Map<String, Object> resp) {
        if (!StringUtils.isEmpty(key)) {
            WsChannelDetails wsChannelDetails = wsChannels.get(key);
            if (wsChannelDetails != null && StringUtils.isNoneBlank(wsChannelDetails.getTaskId())) {
                Map<String, Channel> channels = wsChannelDetails.getChannels();
                String sendDate;
                try {
                    sendDate = obm.writeValueAsString(resp);
                    // 记录操作日志到 MQTT
                    MQClient.publish(String.format(topic, key,wsChannelDetails.getTaskId()), sendDate.getBytes(), 1, false);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                channels.forEach((k, v) -> {
                    if (v.isActive()) {
                        v.writeAndFlush(new TextWebSocketFrame(sendDate));
                    }
                });
            }
        }
    }

    private void sendMessage(ByteBuf byteBuf) {
        if (tcpChannel == null || !tcpChannel.isActive()) {
            throw new BusinessException("TCP 连接不存在!");
        }
        log.info(">>> {}", byteBuf);
        tcpChannel.writeAndFlush(byteBuf);
    }

    private void chargingUav() {
        scheduledExecutorService.schedule(() -> {
            Map<String, String> operate = hangarService.operate(new OperateInp().setCommand(CommandEnum.机库_状态.getCommand()).setAction(CommandEnum.机库_状态.getAction()));
            if (!CollectionUtils.isEmpty(operate) && "close".equals(operate.get("charge_state"))) {
                String[] split = batteryPower.split("_"); // 当前 batteryPower 是云盒关机前的电量信息
                // 当任意一边电池电量小于 90 时, 判定为需要充电
                if (Integer.parseInt(split[0]) < 90 || Integer.parseInt(split[1]) < 90) {
                    log.info("当前电量小于 90% 开始执行充电: {}", batteryPower);
                    // 执行充电
                    hangarService.operate(new OperateInp().setCommand(CommandEnum.机库_充电操作_充电.getCommand()).setAction(CommandEnum.机库_充电操作_充电.getAction()));
                    // 关闭推流
                } else {
                    log.info("电池电量大于 90% 不执行充电: {}", batteryPower);
                }
            }
        }, 5, TimeUnit.MINUTES);
    }

    private void sendByteBuf(ByteBuf buf, TyjwEnum anEnum, JsonNode jsonNode, TyjwServiceImpl.WebSocketFunction<ByteBuf, String, JsonNode> fun) {
        buf.writeShort(TyjwEnum.请求帧头.getInstruct());// 帧头
        buf.writeShort(0);// 数据长度,占位临时赋值为0
        buf.writeBytes(ByteUtil.stringToByte(jsonNode.get("云盒编号").asText()));// 云盒编号
        buf.writeByte(anEnum.getInstruct());// 指令编号
        buf.writeByte(Byte.parseByte(jsonNode.get("加密标志").asText()));// 加密标志
        buf.writeByte(anEnum.getAction());// 动作编号
        fun.dataHandle(buf, anEnum.getArgs(), jsonNode);// 参数处理
        buf.setShort(2, buf.readableBytes() - 4);// 重新计算数据长度
        sendMessage(buf);
    }

    @ChannelHandler.Sharable
    class TyjwWsChannelInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        Map<String, Object> wsReturnMap = new HashMap<>();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame tws) throws JsonProcessingException {

            String text = tws.text();
            if (StringUtils.isNoneBlank(text)) {
                JsonNode jsonNode = obm.readTree(text);
                // 心跳数据直接回复
                if (null != jsonNode.get("action") && "PING_MESSAGE".equals(jsonNode.get("action").asText())) {
                    wsReturnMap.put("action", "PONG_MESSAGE");
                    ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(wsReturnMap)));
                } else {
                    String boxNumber = jsonNode.get("云盒编号").asText();
                    String userId = jsonNode.get("用户ID").asText();
                    String taskId = jsonNode.get("任务ID").asText();
                    if (StringUtils.isNoneBlank(boxNumber) && StringUtils.isNoneBlank(userId) && StringUtils.isNoneBlank(taskId)) {
                        if (wsChannels.containsKey(boxNumber)) {
                            log.info("<<< {}", text);
                            // 云盒已经注册, 判断用户是否注册
                            WsChannelDetails wsChannelDetails = wsChannels.get(boxNumber);
                            Map<String, Channel> channels = wsChannelDetails.getChannels();
                            if (channels.containsKey(userId)) {
                                // 用户已注册,判断 channel 是否活跃
                                Channel wsChannel = channels.get(userId);
                                if (wsChannel == null || !wsChannel.isActive()) {
                                    // 用户下无活跃的 channel
                                    channels.put(userId, ctx.channel());
                                    log.info("用户已经注册, 且没有活跃的channel: {}", userId);
                                }else{
                                    log.info("用户已经注册, 有活跃的channel: {}", userId);
                                }
                            } else {
                                // 用户未注册
                                if (wsChannelDetails.getTaskId().equals(taskId)) {
                                    channels.put(userId, ctx.channel());
                                }
                                log.info("用户未注册, 执行注册: {}", userId);
                            }
                            // 判断任务是否注册
                            if(null == wsChannelDetails.getTaskId()){
                                wsChannelDetails.setTaskId(taskId);
                            }
                            // 判断是否有控制权
                            if(null != jsonNode.get("指令编号") && null != jsonNode.get("动作编号")){
                                if (wsChannelDetails.getControlPower().equals(userId) || ("D1".equals(jsonNode.get("指令编号").asText()) && "30".equals(jsonNode.get("动作编号").asText()))) {
                                    publishEvent(jsonNode);
                                }
                            }
                        } else {
                            // 云盒未注册
                            log.info("云盒未注册: {}", boxNumber);
                            synchronized (wsChannels) {
                                wsChannels.put(boxNumber, new WsChannelDetails()
                                        .setTaskId(taskId)
                                        .setControlPower(userId)
                                        .setChannels(new HashMap<>() {{
                                            put(userId, ctx.channel());
                                        }}));
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.executor().schedule(() -> {
                wsReturnMap.put("action", "READY_MESSAGE");
                try {
                    ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(wsReturnMap)));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }, 1, TimeUnit.SECONDS);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error(">>> {}", cause.getMessage());
        }
    }

    /**
     * 启动 WEBSOCKET_SERVER
     */
    @Override
    public void startWebSocket() {
        WsServer.createWebSocketServer(new TyjwWsChannelInboundHandler(), wsPort);
    }

    /**
     * WEBSOCKET_SERVER 消息处理
     */
    @Override
    public void handleWebSocket(JsonNode jsonNode) {
        Map<String, Object> wsReturnMap = new HashMap<>();
        String boxNumber = jsonNode.get("云盒编号").asText();
        String instruct = jsonNode.get("指令编号").asText();
        String action = jsonNode.get("动作编号").asText();
        if (StringUtils.isEmpty(instruct) && StringUtils.isEmpty(action)) {
            wsReturnMap.put("action", "ERROR_MESSAGE");
            wsReturnMap.put("message", "指令编号或动作编号不能为空!");
            sendMessage(boxNumber, wsReturnMap);
        }
        TyjwEnum anEnum = TyjwEnum.getEnum(Integer.parseInt(instruct, 16), Integer.parseInt(action, 16));
        if (null != tcpChannel && tcpChannel.isActive()) {
            log.info("<<< {}", anEnum.name());
            switch (anEnum) {
                case 航线规划 -> sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> {
                    try {
                        PlanLineDataDTO planLineDataDto = obm.readValue(jsonNode.get("航线数据").asText(), PlanLineDataDTO.class);
                        TyjwProtoBuf.PlanLineData planLineData = ParameterMapping.getPlanLineData(planLineDataDto);
                        arg1.writeBytes(planLineData.toByteArray());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                case 切换无人机控制权 -> {
                    WsChannelDetails wsChannelDetails = wsChannels.get(boxNumber);
                    if (wsChannelDetails != null) {
                        String userId = jsonNode.get("用户ID").asText();
                        wsChannelDetails.setControlPower(userId);
                        log.info(">>> 修改 {} 的控制权为 {}", boxNumber, userId);
                        sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> arg1.writeByte(Byte.parseByte(arg3.get("数据").asText())));
                    }
                }
                case 实时喊话 -> {
                    // TODO 暂不支持
                    byte[] inData = Base64.getDecoder().decode(jsonNode.get("音频数据").asText());
                    List<byte[]> bytes = ByteUtil.splitByteArray(inData, 110);
                    for (byte[] by : bytes) {
                        sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> arg1.writeBytes(by));
                    }
                }
                case MOP数据透传 -> {
                    // TODO 暂未使用
                }
                default -> sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> {
                    if (null != arg2) {
                        String[] split = arg2.split(";");
                        Arrays.stream(split).forEach(el -> {
                            String[] split1 = el.split("-");
                            switch (split1[1]) {
                                case "byte" -> arg1.writeByte(Byte.parseByte(arg3.get(split1[0]).asText()));
                                case "bytes" -> arg1.writeBytes(ByteUtil.stringToByte(arg3.get(split1[0]).asText()));
                                case "short" -> arg1.writeShort(Short.parseShort(arg3.get(split1[0]).asText()));
                                case "int" -> arg1.writeInt(Integer.parseInt(arg3.get(split1[0]).asText()));
                                case "long" -> arg1.writeLong(Long.parseLong(arg3.get(split1[0]).asText()));
                                case "float" -> arg1.writeFloat(Float.parseFloat(arg3.get(split1[0]).asText()));
                                case "double" -> arg1.writeDouble(Double.parseDouble(arg3.get(split1[0]).asText()));
                                case "base64" ->
                                        arg1.writeBytes(Base64.getDecoder().decode(arg3.get(split1[0]).asText()));
                            }
                        });
                    }
                });
            }
            // 记录操作日志到 MQTT
            MQClient.publish(String.format(topic, boxNumber, jsonNode.get("任务ID").asText()), jsonNode.toString().getBytes(), 1, false);
        } else {
            wsReturnMap.put("action", "ERROR_MESSAGE");
            wsReturnMap.put("message", "TCP 连接不存在!");
            sendMessage(boxNumber, wsReturnMap);
        }
    }

    @ChannelHandler.Sharable
    class TyjwChannelInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
            publishEvent(msg);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            // 发送心跳
            if (evt instanceof IdleStateEvent) {
                ByteBuf buf = Unpooled.buffer();
                buf.writeShort(TyjwEnum.请求帧头.getInstruct());
                buf.writeShort(0x09);
                buf.writeByte(TyjwEnum.心跳.getInstruct());
                buf.writeLong(System.currentTimeMillis());
                ctx.writeAndFlush(buf);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error(">>> {}", cause.getMessage());
        }
    }

    /**
     * 开始 TCP 连接
     */
    @Override
    public void startTcpClient() {
        ChannelFuture tcpClient = TcpClient.createTcpClient(new TyjwChannelInboundHandler(), host, tcpPort);
        tcpClient.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                // 连接成功后,发送注册请求
                log.info(">>> 连接 {}:{} 成功, 开始发送注册请求!", host, tcpPort);
                tcpChannel = future.channel();
                ByteBuf buf = Unpooled.buffer();
                byte[] bytes = AuthSingleton.getInstance().getAccessToken().getBytes();
                buf.writeShort(TyjwEnum.请求帧头.getInstruct());
                buf.writeShort(bytes.length + 5);
                buf.writeByte(TyjwEnum.注册.getInstruct());
                buf.writeInt(AuthSingleton.getInstance().getCompanyId());
                buf.writeBytes(bytes);
                tcpChannel.writeAndFlush(buf);
            } else {
                throw new BusinessException(String.format(">>> 连接 TCP 服务, 失败 -> %s:%s", host, tcpPort));
            }
        });
    }

    /**
     * TCP_CLIENT 消息处理
     */
    @Override
    public void handleTcpClient(ByteBuf buf) {
        if (buf.getShort(0) == 0x6A77) {
            int instruct = buf.getByte(4) & 0xFF;
            TyjwEnum anEnum;
            // 指令过滤, 以下项没有 action
            if (instruct == TyjwEnum.注册.getInstruct() ||
                    instruct == TyjwEnum.心跳.getInstruct() ||
                    instruct == TyjwEnum.图片上传完成通知.getInstruct() ||
                    instruct == TyjwEnum.云盒开关机通知.getInstruct() ||
                    instruct == TyjwEnum.信道质量.getInstruct() ||
                    instruct == TyjwEnum.状态数据.getInstruct() ||
                    instruct == TyjwEnum.遥测数据.getInstruct() ||
                    instruct == TyjwEnum.MOP数据透传.getInstruct()
            ) {
                anEnum = TyjwEnum.getEnum(instruct, 0);
                if (instruct == TyjwEnum.注册.getInstruct()) {
                    log.info(">>> {}:{} {}成功!", host, tcpPort, anEnum.name());
                    return;
                }
                if (instruct == TyjwEnum.心跳.getInstruct()) {
//                    log.info(">>> TCP: {}", anEnum.name());
                    return;
                }
            } else {
                int active = buf.getByte(6) & 0xFF;
                log.info("<<< TCP: {}_{}", String.format("0x%02X", instruct), String.format("0x%02X", active));
                anEnum = TyjwEnum.getEnum(instruct, active);
            }
            if (null != anEnum) {
                buf.skipBytes(5);// 跳过 帧头、数据长度、指令编号
                Map<String, Object> wsReturnMap = new HashMap<>();
                wsReturnMap.put("指令编码", String.format("0x%02X", anEnum.getInstruct()));
                wsReturnMap.put("action", "NEW_MESSAGE");
                byte[] boxSnByte = new byte[15];// 云盒编号
                byte[] contentByte;// buffer中的内容
                byte isSuccess;// 是否成功
                switch (anEnum) {
                    case 图片上传完成通知:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("经度", buf.readDouble());
                        wsReturnMap.put("纬度", buf.readDouble());
                        wsReturnMap.put("时间戳", buf.readLong());
                        wsReturnMap.put("原图大小", buf.readLong());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        wsReturnMap.put("原图地址", ByteUtil.bytesToStringUTF8(contentByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 云盒开关机通知:
                        byte isShutdown = buf.readByte();
                        wsReturnMap.put("状态", isShutdown);
                        buf.readBytes(boxSnByte);
                        String boxNumber = ByteUtil.bytesToStringUTF8(boxSnByte);
                        wsReturnMap.put("云盒SN号", boxNumber);
                        sendMessage(boxNumber, wsReturnMap);
                        // 云盒关机后判断此次任务结束
                        if (isShutdown != 1) {
                            WsChannelDetails wsChannelDetails = wsChannels.get(wsReturnMap.get("云盒SN号").toString());
                            if(null != wsChannelDetails.getTaskId()){
                                try {
                                    MQClient.publish(String.format(topic, boxNumber, wsChannelDetails.getTaskId()), obm.writeValueAsBytes(wsReturnMap), 1, false);
                                    log.info(">>> 云盒 {} 即将关机, 任务 {} 执行完成!", boxNumber, wsChannelDetails.getTaskId());
                                    wsChannelDetails.setTaskId(null); // 清除任务
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        // TODO 当收到关机通知后5分钟,判断是否需要执行充电(暂不启用) if(isShutdown == -1){chargingUav();}
                        break;
                    case 信道质量:
                        wsReturnMap.put("时间戳", buf.readUnsignedInt());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        try {
                            TyjwProtoBuf.SignalInfo signalInfo = TyjwProtoBuf.SignalInfo.parseFrom(contentByte);
                            wsReturnMap.put("数据", printer.print(signalInfo));
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e);
                        }
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 状态数据:
                    case 遥测数据:
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        try {
                            if (anEnum.getInstruct() == 0xA8) {
                                TyjwProtoBuf.UavState uavState = TyjwProtoBuf.UavState.parseFrom(contentByte);
                                wsReturnMap.put("云盒SN号", uavState.getBoxSn());
                                wsReturnMap.put("数据", printer.print(uavState));
                                wsReturnMap.put("用户ID", wsChannels.get(uavState.getBoxSn()));
                                batteryPower = uavState.getBatteryState().getBatteryPower(); //不断获取电池电量
                            } else {
                                TyjwProtoBuf.TelemetryData telemetryData = TyjwProtoBuf.TelemetryData.parseFrom(contentByte);
                                wsReturnMap.put("云盒SN号", telemetryData.getBoxSn());
                                wsReturnMap.put("数据", printer.print(telemetryData));
                            }
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e);
                        }
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 航线规划:
                    case 起飞:
                    case 返航:
                    case 取消返航:
                    case 降落:
                    case 取消降落:
                    case 开始航线:
                    case 暂停航线:
                    case 恢复航线:
                    case 结束航线:
                    case 设置返航高度:
                    case 设置相机模式:
                    case 拍照:
                    case 开始录像:
                    case 停止录像:
                    case 强制降落:
                    case 航线结束通知:
                    case 设置返航点:
                    case 紧急制动:
                    case 水平避障设置:
                    case 上避障设置:
                    case 下避障设置:
                    case 指点飞行:
                    case 返航到指定机场:
                    case 格式化存储卡:
                    case 设置视频码流:
                    case 切换SIM卡:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        wsReturnMap.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            wsReturnMap.put("action", "ERROR_MESSAGE");
                        }
                        wsReturnMap.put("错误码", buf.readInt());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 实时激光测距:
                    case 手动激光测距:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        wsReturnMap.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            wsReturnMap.put("action", "ERROR_MESSAGE");
                        }
                        wsReturnMap.put("经度", buf.readDouble());
                        wsReturnMap.put("纬度", buf.readDouble());
                        wsReturnMap.put("海拔高度", buf.readFloat());
                        wsReturnMap.put("距离", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 打开单点测温:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        wsReturnMap.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            wsReturnMap.put("action", "ERROR_MESSAGE");
                        }
                        wsReturnMap.put("X点坐标", buf.readFloat());
                        wsReturnMap.put("Y点坐标", buf.readFloat());
                        wsReturnMap.put("温度", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 打开区域测温:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        wsReturnMap.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            wsReturnMap.put("action", "ERROR_MESSAGE");
                        }
                        wsReturnMap.put("X1点坐标", buf.readFloat());
                        wsReturnMap.put("Y1点坐标", buf.readFloat());
                        wsReturnMap.put("X2点坐标", buf.readFloat());
                        wsReturnMap.put("Y2点坐标", buf.readFloat());
                        wsReturnMap.put("平均温度", buf.readFloat());
                        wsReturnMap.put("最低温度", buf.readFloat());
                        wsReturnMap.put("最高温度", buf.readFloat());
                        wsReturnMap.put("最低温度x坐标", buf.readFloat());
                        wsReturnMap.put("最低温度y坐标", buf.readFloat());
                        wsReturnMap.put("最高温度x坐标", buf.readFloat());
                        wsReturnMap.put("最高温度y坐标", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 无人机准备完成通知:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        wsReturnMap.put("电池电量", buf.readByte());
                        wsReturnMap.put("经度", buf.readDouble());
                        wsReturnMap.put("纬度", buf.readDouble());
                        wsReturnMap.put("海拔高度", buf.readInt());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    case 返回码:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        int returnCode = buf.readInt();
                        wsReturnMap.put("action", "ERROR_MESSAGE");
                        wsReturnMap.put("code", returnCode);
                        wsReturnMap.put("message", TyjwReturnCodeEnum.getMsg(returnCode));
                        sendMessage(null, wsReturnMap);
                        break;
                    case 机场任务完成通知:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", String.format("0x%02X", buf.readByte()));
                        wsReturnMap.put("媒体文件数量", buf.readShort());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 获取 AccessToken
     */
//    @Scheduled(cron = "0 0/3 * * * ?")
    @Override
    public void getToken() {
        log.info(">>> AccessToken: 0 0/3 * * * ?");
        if (Strings.isBlank(auth.getAccessToken())) {
            getToken(new GetTokenInp());
        }
    }

    /**
     * 获取 AccessToken
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {
        String str = param.getAccessKeyId() + param.getAccessKeySecret() + param.getTimeStamp();
        param.setEncryptStr(MD5HashGenerator.generateMD5(str));
        ResponseTy<GetTokenOup> result = feign.getToken(param);
        if (0 == result.getState()) {
            auth.setCompanyId(result.getContent().getCompanyId());
            auth.setAccessToken(result.getContent().getAccessToken());
            return result.getContent();
        } else {
            throw new BusinessException(String.valueOf(result.getState()), result.getMessage());
        }
    }

    /**
     * 云盒 - 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {
        return handleAuth(() -> {
            ResponseTy<List<GetCloudBoxListOup>> result = feign.cloudBoxList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 修改云盒设置
     */
    @Override
    public String updateCloudBox(UpdateCloudBoxInp param) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.updateCloudBox(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {
        return handleAuth(() -> {
            ResponseTy<List<HistoryOup>> result = feign.history(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 修改推流地址
     */
    @Override
    public String updateLive(UpdateLiveInp param) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.updateLive(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 开始直播推流
     */
    @Override
    public String openLive(String boxSn) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.openLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 结束直播推流
     */
    @Override
    public String closeLive(String boxSn) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.closeLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取直播地址
     */
    @Override
    public GetLiveAddressOup getLiveAddress(String boxSn) {
        return handleAuth(() -> {
            ResponseTy<GetLiveAddressOup> result = feign.getLiveAddress(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {
        return handleAuth(() -> {
            ResponseTy<List<GetPhotosOup>> result = feign.getPhotos(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 航线转换
     */
    @Override
    public String convert(MultipartFile file) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.convert(file, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 立即执行航线模板任务
     */
    @Override
    public String template(TemplateInp param) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.template(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 机库列表
     */
    @Override
    public List<RechargeDTO> hangarList() {
        return handleAuth(() -> {
            ResponseTy<List<RechargeDTO>> result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 机库控制
     */
    @Override
    public String hangarControl(String hangarId, Integer instructId) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.hangarControl(hangarId, instructId, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 规划机库航线
     */
    @Override
    public String line(PlanLineDataDTO param) {
        return handleAuth(() -> {
            Map<String, String> lineData = Map.of("lineData", Base64.getEncoder().encodeToString(ParameterMapping.getPlanLineData(param).toByteArray()));
            ResponseTy<String> result = feign.line(lineData, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 修改机库信息
     */
    @Override
    public String updateAirport(UpdateAirportInp param) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }
}
