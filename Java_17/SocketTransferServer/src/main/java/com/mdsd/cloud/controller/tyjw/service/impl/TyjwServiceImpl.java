package com.mdsd.cloud.controller.tyjw.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.enums.CommandEnum;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.enums.TyjwReturnCodeEnum;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    final EApiFeign feign;

    final TcpClient tcpClient;

    final WsServer wsServer;

    final IHangarService hangarService;

    public final AuthSingleton auth = AuthSingleton.getInstance();

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    JsonFormat.Printer printer = JsonFormat.printer();

    ObjectMapper obm = new ObjectMapper();

    final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    String batteryPower = null;

    public TyjwServiceImpl(EApiFeign feign, TcpClient tcpClient,WsServer wsServer, IHangarService hangarService ) {
        this.feign = feign;
        this.tcpClient = tcpClient;
        this.wsServer = wsServer;
        this.hangarService = hangarService;
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
            throw new BusinessException(String.valueOf(result.getState()), result.getState() == -201 ? result.getMessage() + "(后台系统将在3分钟后自动尝试重新登录)" : result.getMessage());
        }
    }

    /**
     * 获取 AccessToken
     */
    @Override
    public void getToken() {
        getToken(new GetTokenInp());
    }

    /**
     * 获取 AccessToken
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {

        log.info(">>> 获取 AccessToken");
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

    @Override
    public void mdsdWebSocket(Map<String, String> map)throws JsonProcessingException {
        if (StringUtils.isEmpty(map.get("动作编号"))) {
            wssErrorMessage(map.get("云盒编号"), "动作编号不能为: NULL");
        }
        TyjwEnum anEnum = TyjwEnum.getEnum(Integer.parseInt(map.get("指令编号"), 16), Integer.parseInt(map.get("动作编号"), 16));
        if (tcpClient.isActiveChannel()) {
            log.info("<<< {}", anEnum.name());
            switch (anEnum) {
                case 航线规划 -> {
                    PlanLineDataDTO planLineDataDto = obm.readValue(map.get("航线数据"), PlanLineDataDTO.class);
                    TyjwProtoBuf.PlanLineData planLineData = ParameterMapping.getPlanLineData(planLineDataDto);
                    ByteBuf buf = aDefault.buffer();
                    sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> arg1.writeBytes(planLineData.toByteArray()));
                }
                case 实时喊话 -> {
                    // TODO 暂不支持
                    byte[] inData = Base64.getDecoder().decode(map.get("音频数据"));
                    List<byte[]> bytes = ByteUtil.splitByteArray(inData, 110);
                    for (byte[] by : bytes) {
                        ByteBuf buf = aDefault.buffer();
                        sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> arg1.writeBytes(by));
                    }
                }
                case MOP数据透传 -> {
                    // TODO 暂未使用
                }
                default -> {
                    ByteBuf buf = aDefault.buffer();
                    sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> {
                        if (null != arg2) {
                            String[] split = arg2.split(";");
                            Arrays.stream(split).forEach(el -> {
                                String[] split1 = el.split("-");
                                switch (split1[1]) {
                                    case "byte" -> arg1.writeByte(Byte.parseByte(arg3.get(split1[0])));
                                    case "bytes" -> arg1.writeBytes(ByteUtil.stringToByte(arg3.get(split1[0])));
                                    case "short" -> arg1.writeShort(Short.parseShort(arg3.get(split1[0])));
                                    case "int" -> arg1.writeInt(Integer.parseInt(arg3.get(split1[0])));
                                    case "long" -> arg1.writeLong(Long.parseLong(arg3.get(split1[0])));
                                    case "float" -> arg1.writeFloat(Float.parseFloat(arg3.get(split1[0])));
                                    case "double" -> arg1.writeDouble(Double.parseDouble(arg3.get(split1[0])));
                                    case "base64" ->
                                            arg1.writeBytes(Base64.getDecoder().decode(arg3.get(split1[0])));
                                }
                            });
                        }
                    });
                }
            }
        } else {
            wssErrorMessage(map.get("云盒编号"), "云盒端连接不存在, 正在尝试重新连接并尝试重新执行 {} 指令!");
            tcpClient.connect();
            mdsdWebSocket(map);
        }
    }

    private void sendByteBuf(ByteBuf buf, TyjwEnum anEnum, Map<String, String> map, TyjwServiceImpl.WebSocketFunction<ByteBuf, String, Map<String, String>> fun) {
        buf.writeShort(TyjwEnum.请求帧头.getInstruct());// 帧头
        buf.writeShort(0);// 数据长度,占位临时赋值为0
        buf.writeBytes(ByteUtil.stringToByte(map.get("云盒编号")));// 云盒编号
        buf.writeByte(anEnum.getInstruct());// 指令编号
        buf.writeByte(Byte.parseByte(map.get("加密标志")));// 加密标志
        buf.writeByte(anEnum.getAction());// 动作编号
        fun.dataHandle(buf, anEnum.getArgs(), map);// 参数处理
        buf.setShort(2, buf.readableBytes() - 4);// 重新计算数据长度
        tcpClient.sendMessage(buf);
    }

    private void wssErrorMessage(String boxSn, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("action", "ERROR_MESSAGE");
        result.put("message", message);
        log.info(result.toString());
        wsServer.sendMessage(boxSn, result);
    }

    @Override
    public void tyjwTcpClient(ByteBuf buf) {
        if (buf.getShort(0) == 0x6A77) {
            int instruct = buf.getByte(4) & 0xFF;
            TyjwEnum anEnum = TyjwEnum.getEnum(instruct);
            // 指令过滤
            switch (anEnum) {
                case 注册:
                case 心跳:
                case 图片上传完成通知:
                case 云盒开关机通知:
                case 信道质量:
                case 状态数据:
                case 遥测数据:
                case MOP数据透传:
                    break;
                default:
                    int active = buf.getByte(6) & 0xFF;
                    anEnum = TyjwEnum.getEnum(instruct, active);
                    log.info("<<< {}_{}", String.format("0x%02X", instruct), String.format("0x%02X", active));
                    break;
            }
            if (null != anEnum) {
                ConcurrentHashMap<String, List<Channel>> channels = wsServer.getChannels();
                if (CollectionUtils.isEmpty(channels)) {
                    return;
                }
                buf.skipBytes(5);// 跳过 帧头、数据长度、指令编号
                Map<String, Object> result = new HashMap<>();
                result.put("指令编码", String.format("0x%02X", anEnum.getInstruct()));
                result.put("action", "NEW_MESSAGE");
                byte[] boxSnByte = new byte[15];// 云盒编号
                byte[] contentByte;// buffer中的内容
                byte isSuccess;// 是否成功
                switch (anEnum) {
                    case 注册:
                        result.put("是否成功", buf.readByte());
                        log.info("注册: {}", result);
                    case 心跳:
                        break;
                    case 图片上传完成通知:
                        result.put("加密标志", buf.readByte());
                        result.put("经度", buf.readDouble());
                        result.put("纬度", buf.readDouble());
                        result.put("时间戳", buf.readLong());
                        result.put("原图大小", buf.readLong());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        result.put("原图地址", ByteUtil.bytesToStringUTF8(contentByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 云盒开关机通知:
                        byte isShutdown = buf.readByte();
                        result.put("状态", isShutdown);
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        // TODO 暂不启用, 当收到关机通知后5分钟,判断是否需要执行充电
//                            log.info("云盒开关机通知_状态: {}", result);
//                            if(isShutdown == -1){
//                                chargingUav();
//                            }
                        break;
                    case 信道质量:
                        result.put("时间戳", buf.readUnsignedInt());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        try {
                            TyjwProtoBuf.SignalInfo signalInfo = TyjwProtoBuf.SignalInfo.parseFrom(contentByte);
                            result.put("数据", printer.print(signalInfo));
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e);
                        }
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 状态数据:
                    case 遥测数据:
                        contentByte = new byte[buf.readableBytes()];
                        buf.readBytes(contentByte);
                        try {
                            if (anEnum.getInstruct() == 0xA8) {
                                TyjwProtoBuf.UavState uavState = TyjwProtoBuf.UavState.parseFrom(contentByte);
                                result.put("云盒SN号", uavState.getBoxSn());
                                result.put("数据", printer.print(uavState));
                                batteryPower = uavState.getBatteryState().getBatteryPower(); //不断获取电池电量
                            } else {
                                TyjwProtoBuf.TelemetryData telemetryData = TyjwProtoBuf.TelemetryData.parseFrom(contentByte);
                                result.put("云盒SN号", telemetryData.getBoxSn());
                                result.put("数据", printer.print(telemetryData));
                            }
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e);
                        }
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        result.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            result.put("action", "ERROR_MESSAGE");
                        }
                        result.put("错误码", buf.readInt());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 实时激光测距:
                    case 手动激光测距:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        result.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            result.put("action", "ERROR_MESSAGE");
                        }
                        result.put("经度", buf.readDouble());
                        result.put("纬度", buf.readDouble());
                        result.put("海拔高度", buf.readFloat());
                        result.put("距离", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 打开单点测温:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        result.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            result.put("action", "ERROR_MESSAGE");
                        }
                        result.put("X点坐标", buf.readFloat());
                        result.put("Y点坐标", buf.readFloat());
                        result.put("温度", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 打开区域测温:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        isSuccess = buf.readByte();
                        result.put("执行结果", isSuccess);
                        if (isSuccess == 0) {
                            result.put("action", "ERROR_MESSAGE");
                        }
                        result.put("X1点坐标", buf.readFloat());
                        result.put("Y1点坐标", buf.readFloat());
                        result.put("X2点坐标", buf.readFloat());
                        result.put("Y2点坐标", buf.readFloat());
                        result.put("平均温度", buf.readFloat());
                        result.put("最低温度", buf.readFloat());
                        result.put("最高温度", buf.readFloat());
                        result.put("最低温度x坐标", buf.readFloat());
                        result.put("最低温度y坐标", buf.readFloat());
                        result.put("最高温度x坐标", buf.readFloat());
                        result.put("最高温度y坐标", buf.readFloat());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 无人机准备完成通知:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        result.put("电池电量", buf.readByte());
                        result.put("经度", buf.readDouble());
                        result.put("纬度", buf.readDouble());
                        result.put("海拔高度", buf.readInt());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                    case 返回码:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        int returnCode = buf.readInt();
                        result.put("action", "ERROR_MESSAGE");
                        result.put("code", returnCode);
                        result.put("message", TyjwReturnCodeEnum.getMsg(returnCode));
                        wsServer.sendMessage(null, result);
                        break;
                    case 机场任务完成通知:
                        result.put("加密标志", buf.readByte());
                        result.put("动作编号", String.format("0x%02X", buf.readByte()));
                        result.put("媒体文件数量", buf.readShort());
                        buf.readBytes(boxSnByte);
                        result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                        wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                        break;
                }
            }
        }
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
}
