package com.mdsd.cloud.controller.transfer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.component.SocketClient;
import com.mdsd.cloud.component.WebSocketServer;
import com.mdsd.cloud.controller.transfer.dto.TyjwProtoBuf;
import com.mdsd.cloud.enums.InstructEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    JsonFormat.Printer printer = JsonFormat.printer();

    private final SocketClient socketClient;

    private final WebSocketServer webSocketServer;

    public TransferService(SocketClient socketClient, WebSocketServer webSocketServer) {

        this.socketClient = socketClient;
        this.webSocketServer = webSocketServer;
    }

    @EventListener
    public void handleSocketEvent(SocketEvent evn) {

        Object source = evn.getSource();
        if (source instanceof WebSocketServer) {
            try {
                webSocketServerChannelReadListener(evn.getMap());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (source instanceof SocketClient) {
            ByteBuf byteBuf = evn.getByteBuf();
            if (byteBuf.getShort(0) == 0x6A77) {
                int instruct = byteBuf.getByte(4) & 0xFF;
                InstructEnum anEnum = InstructEnum.getEnum(instruct);
                if (null == anEnum) {
                    return;
                }
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
                        int active = byteBuf.getByte(6) & 0xFF;
                        anEnum = InstructEnum.getEnum(instruct, active);
                        log.info("SocketClient_Receive <<< {}_{}", String.format("0x%02X", instruct), String.format("0x%02X", active));
                        break;
                }
                if (null != anEnum) {
                    socketClientChannelRead0Listener(anEnum, byteBuf);
                }
            }
        } else {
            throw new BusinessException("未知事件源!");
        }
    }

    private void socketClientChannelRead0Listener(InstructEnum param, ByteBuf buf) {

        ConcurrentHashMap<String, Channel> channelMap = webSocketServer.getChannelMap();
        if (channelMap.isEmpty()) {
            return;
        }
        buf.skipBytes(5);// 跳过 帧头、数据长度、指令编号
        Map<String, Object> result = new HashMap<>();
        result.put("指令编码", String.format("0x%02X", param.getInstruct()));
        result.put("action", "NEW_MESSAGE");
        byte[] boxSnByte = new byte[15];// 云盒编号
        byte[] contentByte;// buffer中的内容
        byte isSuccess;// 是否成功
        switch (param) {
            case 注册:
                isSuccess = buf.readByte();
                result.put("是否成功", isSuccess);
                if (isSuccess == 0) {
                    result.put("action", "ERROR_MESSAGE");
                }
                channelMap.forEach((key, value) -> webSocketServer.sendMessage(key, result));
                break;
            case 心跳:
                result.put("时间戳", buf.readLong());
                channelMap.forEach((key, value) -> webSocketServer.sendMessage(key, result));
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
                break;
            case 云盒开关机通知:
                result.put("状态", buf.readByte());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
                break;
            case 状态数据:
            case 遥测数据:
                contentByte = new byte[buf.readableBytes()];
                buf.readBytes(contentByte);
                try {
                    if (param.getInstruct() == 0xA8) {
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
                break;
            case 机场任务完成通知:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", String.format("0x%02X", buf.readByte()));
                result.put("媒体文件数量", buf.readShort());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), result);
                break;
            default:
                throw new BusinessException("未知指令!");
        }
    }

    private void webSocketServerChannelReadListener(Map<String, String> map) throws IOException {

        Assert.notNull(map.get("指令编号"), "指令不能为: NULL");
        int instruct = Integer.parseInt(map.get("指令编号"), 16);
        if (socketClient.isActiveChannel()) {
            Assert.notNull(map.get("云盒编号"), "云盒编号不能为: NULL");
            Assert.notNull(map.get("动作编号"), "动作编号不能为: NULL");
            InstructEnum anEnum = InstructEnum.getEnum(instruct, Integer.parseInt(map.get("动作编号"), 16));
            if (null != anEnum) {
                log.info("<<< {}", anEnum.name());
                switch (anEnum) {
                    case 实时喊话 -> {
                        // TODO 暂不支持
                        byte[] inData = Base64.getDecoder().decode(map.get("音频数据"));
                        List<byte[]> bytes = ByteUtil.splitByteArray(inData, 110);
                        for (byte[] by : bytes) {
                            ByteBuf buf = aDefault.buffer();
                            sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> {
                                arg1.writeBytes(by);
                            });
                        }
                    }
                    case MOP数据透传 -> {
                        // TODO 暂未使用
                    }
                    default -> {
                        ByteBuf buf = aDefault.buffer();
                        sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> {
                            if (null != arg2) {
                                for (String str : arg2) {
                                    String[] split = str.split("-");
                                    switch (split[1]) {
                                        case "byte" -> arg1.writeByte(Byte.parseByte(arg3.get(split[0])));
                                        case "bytes" -> arg1.writeBytes(ByteUtil.stringToByte(arg3.get(split[0])));
                                        case "short" -> arg1.writeShort(Short.parseShort(arg3.get(split[0])));
                                        case "int" -> arg1.writeInt(Integer.parseInt(arg3.get(split[0])));
                                        case "long" -> arg1.writeLong(Long.parseLong(arg3.get(split[0])));
                                        case "float" -> arg1.writeFloat(Float.parseFloat(arg3.get(split[0])));
                                        case "double" -> arg1.writeDouble(Double.parseDouble(arg3.get(split[0])));
                                        case "base64" ->
                                                arg1.writeBytes(Base64.getDecoder().decode(arg3.get(split[0])));
                                    }
                                }
                            }
                        });
                    }
                }
            } else {
                wssErrorMessage(map.get("云盒编号"), "未知指令!");
            }
        } else {
            if (instruct == InstructEnum.注册.getInstruct()) {
                socketClient.connect();
                return;
            }
            wssErrorMessage(map.get("云盒编号"), "客户端连接不存在, 请发送注册指令!");
        }
    }

    private void sendByteBuf(ByteBuf buf, InstructEnum anEnum, Map<String, String> map, TransferFunction fun) {

        buf.writeShort(InstructEnum.请求帧头.getInstruct());// 帧头
        buf.writeShort(0);// 数据长度,占位临时赋值为0
        buf.writeBytes(ByteUtil.stringToByte(map.get("云盒编号")));// 云盒编号
        buf.writeByte(anEnum.getInstruct());// 指令编号
        buf.writeByte(Byte.parseByte(map.get("加密标志")));// 加密标志
        buf.writeByte(anEnum.getAction());// 动作编号
        fun.argHandle(buf, anEnum.getArgs(), map);// 参数处理
        buf.setShort(2, buf.readableBytes() - 4);// 重新计算数据长度
        socketClient.sendMessage(buf);
    }

    private void wssErrorMessage(String boxSn, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("action", "SERVER_ERROR");
        result.put("error", message);
        log.info(result.toString());
        webSocketServer.sendMessage(boxSn, result);
    }
}
