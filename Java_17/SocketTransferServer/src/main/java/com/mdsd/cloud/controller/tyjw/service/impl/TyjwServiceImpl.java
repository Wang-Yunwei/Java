package com.mdsd.cloud.controller.tyjw.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.controller.web.dto.WsChannelDetails;
import com.mdsd.cloud.controller.web.service.IWebSocketService;
import com.mdsd.cloud.controller.web.service.impl.WebSocketServiceImpl;
import com.mdsd.cloud.enums.CommonEnum;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.enums.TyjwReturnCodeEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.util.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.lang.String.valueOf;

/**
 * @author WangYunwei [2024-09-03]
 */
@Slf4j
@Service
public class TyjwServiceImpl implements ITyjwService {

    @Value("${env.ip.tyjw}")
    private String host;

    @Value("${env.port.tyjw.tcp}")
    private int port;

    private Channel tcpChannel;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    private final JsonFormat.Printer printer = JsonFormat.printer();

    private final ObjectMapper obm = new ObjectMapper();

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    private final EApiFeign feign;
    private final ApplicationEventPublisher publisher;
    private final IWebSocketService webSocketService;

    public TyjwServiceImpl(EApiFeign feign, ApplicationEventPublisher publisher, IWebSocketService webSocketService) {
        this.feign = feign;
        this.publisher = publisher;
        this.webSocketService = webSocketService;
    }

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
            throw new BusinessException(valueOf(result.getState()), result.getState() == -201 ? result.getMessage() + "(后台系统将在3分钟后自动尝试重新登录)" : result.getMessage());
        }
    }

    private void sendMessage(ByteBuf byteBuf) {
        if (tcpChannel == null || !tcpChannel.isActive()) {
            throw new BusinessException("TCP 连接不存在!");
        }
        tcpChannel.writeAndFlush(byteBuf);
        log.info(">>> {}", byteBuf);
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

    @Override
    public void handleWebSocket(JsonNode jsonNode) {
        String boxNumber = jsonNode.get("云盒编号").asText();
        String instruct = jsonNode.get("指令编号").asText();
        String action = jsonNode.get("动作编号").asText();
        if (StringUtils.isEmpty(instruct) && StringUtils.isEmpty(action)) {
            webSocketService.sendMessage(boxNumber, String.format(WebSocketServiceImpl.errorMessage,"指令编号或动作编号不能为空!"));
        }
        TyjwEnum anEnum = TyjwEnum.getEnum(Integer.parseInt(instruct, 16), Integer.parseInt(action, 16));
        if (null != tcpChannel && tcpChannel.isActive()) {
            log.info(anEnum.name());
            switch (anEnum) {
                case 航线飞行_航线规划 -> sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> {
                    try {
                        PlanLineDataDTO planLineDataDto = obm.readValue(jsonNode.get("航线数据").asText(), PlanLineDataDTO.class);
                        TyjwProtoBuf.PlanLineData planLineData = TyjwParameterMapping.getPlanLineData(planLineDataDto);
                        arg1.writeBytes(planLineData.toByteArray());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
                case 手动飞行_切换无人机控制权 -> {
                    WsChannelDetails wsChannelDetails = webSocketService.getWsChannels().get(boxNumber);
                    if (wsChannelDetails != null) {
                        String userId = jsonNode.get("用户ID").asText();
                        wsChannelDetails.setControlPower(userId);
                        log.info("修改 {} 的控制权为 {}", boxNumber, userId);
                        sendByteBuf(aDefault.buffer(), anEnum, jsonNode, (arg1, arg2, arg3) -> arg1.writeByte(Byte.parseByte(arg3.get("数据").asText())));
                    }
                }
                case 喊话器_实时喊话 -> {
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
            MQClient.publish(format(MQClient.taskTopic, boxNumber, jsonNode.get("任务ID").asText()), jsonNode.toString().getBytes(), 1, false);
        } else {
            webSocketService.sendMessage(boxNumber, String.format(WebSocketServiceImpl.errorMessage,"TCP 连接不存在!"));
        }
    }

    @ChannelHandler.Sharable
    class TyjwChannelInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
            publisher.publishEvent(new CommonEvent(CommonEnum.TCP_SOCKET_TYJW, msg));
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
            log.error(cause.getMessage());
        }
    }

    @Override
    public void startTcpConnect() {
        ChannelFuture tcpClient = SocketUtil.createTcpClient(new TyjwChannelInboundHandler(), host, port);
        tcpClient.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                // 连接成功后,发送注册请求
                log.info("连接 {}:{} 成功, 开始发送注册请求!", host, port);
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
                throw new BusinessException(format("连接 TCP 服务, 失败 -> %s:%s", host, port));
            }
        });
    }

    @Override
    public void handleTcpClient(ByteBuf buf) {
        if (buf.getShort(0) == 0x6A77) {
            int instruct = buf.getByte(4) & 0xFF;
            TyjwEnum anEnum;
            // 指令过滤, 以下项没有 action
            if (instruct == TyjwEnum.注册.getInstruct() || instruct == TyjwEnum.心跳.getInstruct() || instruct == TyjwEnum.状态通知_图片上传完成通知.getInstruct() || instruct == TyjwEnum.状态通知_云盒开关机通知.getInstruct() || instruct == TyjwEnum.无人机实时数据_信道质量.getInstruct() || instruct == TyjwEnum.无人机实时数据_状态数据.getInstruct() || instruct == TyjwEnum.无人机实时数据_遥测数据.getInstruct() || instruct == TyjwEnum.MOP数据透传.getInstruct()) {
                anEnum = TyjwEnum.getEnum(instruct, 0);
                if (instruct == TyjwEnum.注册.getInstruct()) {
                    log.info("<<< {}:{} {}成功!", host, port, anEnum.name());
                    return;
                }
                if (instruct == TyjwEnum.心跳.getInstruct()) {
//                    log.info("<<< TCP: {}", anEnum.name());
                    return;
                }
            } else {
                anEnum = TyjwEnum.getEnum(instruct, buf.getByte(6) & 0xFF);
            }
            if (null != anEnum) {
                buf.skipBytes(5);// 跳过 帧头、数据长度、指令编号
                Map<String, Object> wsReturnMap = new HashMap<>();
                wsReturnMap.put("指令编码", format("0x%02X", anEnum.getInstruct()));
                wsReturnMap.put("action", "NEW_MESSAGE");
                byte[] boxSnByte = new byte[15];// 云盒编号
                byte[] contentByte;// buffer中的内容
                byte isSuccess;// 是否成功
                switch (anEnum) {
                    case 状态通知_图片上传完成通知:
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
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 状态通知_云盒开关机通知:
                        byte isShutdown = buf.readByte();
                        wsReturnMap.put("状态", isShutdown);
                        buf.readBytes(boxSnByte);
                        String boxNumber = ByteUtil.bytesToStringUTF8(boxSnByte);
                        wsReturnMap.put("云盒SN号", boxNumber);
                        webSocketService.sendMessage(boxNumber, wsReturnMap.toString());
                        // 云盒关机后判断此次任务结束
                        if (isShutdown != 1) {
                            WsChannelDetails wsChannelDetails = webSocketService.getWsChannels().get(wsReturnMap.get("云盒SN号").toString());
                            if (null != wsChannelDetails.getTaskId()) {
                                log.info("云盒 {} 即将关机,任务 {} 执行完成!", boxNumber, wsChannelDetails.getTaskId());
                                // 记录操作日志到 MQTT
                                MQClient.publish(format(MQClient.taskTopic, boxNumber, wsChannelDetails.getTaskId()), "{\"missionStatus\":0}".getBytes(), 1, false);
                                wsChannelDetails.setTaskId(null);// 任务置空
                                wsChannelDetails.setControlPower(null);// 控制权置空
                            }
                        }
                        // TODO 当收到关机通知后5分钟,判断是否需要执行充电(暂不启用) if(isShutdown == -1){chargingUav();}
                        break;
                    case 无人机实时数据_信道质量:
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
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 无人机实时数据_状态数据:
                    case 无人机实时数据_遥测数据:
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        try {
                            if (anEnum.getInstruct() == 0xA8) {
                                TyjwProtoBuf.UavState uavState = TyjwProtoBuf.UavState.parseFrom(contentByte);
                                wsReturnMap.put("云盒SN号", uavState.getBoxSn());
                                wsReturnMap.put("数据", printer.print(uavState));
                                wsReturnMap.put("用户ID", webSocketService.getWsChannels().get(uavState.getBoxSn()));
//                                batteryPower = uavState.getBatteryState().getBatteryPower(); //不断获取电池电量
                            } else {
                                TyjwProtoBuf.TelemetryData telemetryData = TyjwProtoBuf.TelemetryData.parseFrom(contentByte);
                                wsReturnMap.put("云盒SN号", telemetryData.getBoxSn());
                                wsReturnMap.put("数据", printer.print(telemetryData));
                            }
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e);
                        }
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 航线飞行_航线规划:
                    case 手动飞行_起飞:
                    case 手动飞行_返航:
                    case 手动飞行_取消返航:
                    case 手动飞行_降落:
                    case 手动飞行_取消降落:
                    case 航线飞行_开始航线:
                    case 航线飞行_暂停航线:
                    case 航线飞行_恢复航线:
                    case 航线飞行_结束航线:
                    case 无人机设置_设置返航高度:
                    case 相机_设置相机模式:
                    case 相机_拍照:
                    case 相机_开始录像:
                    case 相机_停止录像:
                    case 手动飞行_强制降落:
                    case 状态通知_航线结束通知:
                    case 无人机设置_设置返航点:
                    case 手动飞行_紧急制动:
                    case 无人机设置_水平避障设置:
                    case 无人机设置_上避障设置:
                    case 无人机设置_下避障设置:
                    case 手动飞行_指点飞行:
                    case 手动飞行_返航到指定机场:
                    case 相机_格式化存储卡:
                    case 无人机设置_设置视频码流:
                    case 无人机设置_切换SIM卡:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        wsReturnMap.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            wsReturnMap.put("action", "ERROR_MESSAGE");
                        }
                        wsReturnMap.put("错误码", buf.readInt());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 相机_实时激光测距:
                    case 相机_手动激光测距:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
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
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 相机_打开单点测温:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
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
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 相机_打开区域测温:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
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
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 状态通知_无人机准备完成通知:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
                        wsReturnMap.put("电池电量", buf.readByte());
                        wsReturnMap.put("经度", buf.readDouble());
                        wsReturnMap.put("纬度", buf.readDouble());
                        wsReturnMap.put("海拔高度", buf.readInt());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
                        break;
                    case 返回码:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
                        int returnCode = buf.readInt();
                        wsReturnMap.put("action", "ERROR_MESSAGE");
                        wsReturnMap.put("code", returnCode);
                        wsReturnMap.put("message", TyjwReturnCodeEnum.getMsg(returnCode));
                        webSocketService.sendMessage("M13220230801135", wsReturnMap.toString());
                        break;
                    case 状态通知_机场任务完成通知:
                        wsReturnMap.put("加密标志", buf.readByte());
                        wsReturnMap.put("动作编号", format("0x%02X", buf.readByte()));
                        wsReturnMap.put("媒体文件数量", buf.readShort());
                        buf.readBytes(boxSnByte);
                        wsReturnMap.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        webSocketService.sendMessage(wsReturnMap.get("云盒SN号").toString(), wsReturnMap.toString());
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
    @Scheduled(cron = "0 0/3 * * * ?")
    @Override
    public void getToken() {
        if ("Linux1".equals(System.getProperties().getProperty("os.name"))) {
            if (Strings.isBlank(auth.getAccessToken())) {
                getToken(new GetTokenInp());
                log.info("GetAccessToken: 0 0/3 * * * ?");
            }
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
            throw new BusinessException(valueOf(result.getState()), result.getMessage());
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
            Map<String, String> lineData = Map.of("lineData", Base64.getEncoder().encodeToString(TyjwParameterMapping.getPlanLineData(param).toByteArray()));
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
