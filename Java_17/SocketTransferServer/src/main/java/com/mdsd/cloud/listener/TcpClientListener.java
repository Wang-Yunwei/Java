package com.mdsd.cloud.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.rpc.TcpClient;
import com.mdsd.cloud.rpc.WsServer;
import com.mdsd.cloud.controller.tyjw.dto.TyjwProtoBuf;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-09-04]
 */
@Slf4j
@Component
public class TcpClientListener {

    JsonFormat.Printer printer = JsonFormat.printer();

    private final WsServer wsServer;

    public TcpClientListener(WsServer wsServer) {
        this.wsServer = wsServer;
    }

    @EventListener
    public void listen(SocketEvent evn) {
        if (evn.getSource() instanceof TcpClient) {
            ByteBuf buf = evn.getByteBuf();
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
                        log.info("SocketClient_Receive <<< {}_{}", String.format("0x%02X", instruct), String.format("0x%02X", active));
                        break;
                }
                if (null != anEnum) {
                    ConcurrentHashMap<String, Channel> channelMap = wsServer.getChannelMap();
                    if (channelMap.isEmpty()) {
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
                            isSuccess = buf.readByte();
                            result.put("是否成功", isSuccess);
                            if (isSuccess == 0) {
                                result.put("action", "ERROR_MESSAGE");
                            }
                            channelMap.forEach((key, value) -> wsServer.sendMessage(key, result));
                            break;
                        case 心跳:
                            result.put("时间戳", buf.readLong());
                            channelMap.forEach((key, value) -> wsServer.sendMessage(key, result));
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
                            result.put("状态", buf.readByte());
                            buf.readBytes(boxSnByte);
                            result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                            log.info("云盒开关机通知_状态: {}",result);
                            wsServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                        case 机场任务完成通知:
                            result.put("加密标志", buf.readByte());
                            result.put("动作编号", String.format("0x%02X", buf.readByte()));
                            result.put("媒体文件数量", buf.readShort());
                            buf.readBytes(boxSnByte);
                            result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                            wsServer.sendMessage(result.get("云盒SN号").toString(), result);
                            break;
                        default:
                            throw new BusinessException("TcpClient <<< 未知指令!");
                    }
                }
            }
        }
    }
}
