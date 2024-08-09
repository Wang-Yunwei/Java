package com.mdsd.cloud.controller.transfer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.transfer.dto.TyjwProtoBuf;
import com.mdsd.cloud.controller.transfer.socket.SocketClient;
import com.mdsd.cloud.controller.transfer.socket.WebSocketServer;
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

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private final SocketClient socketClient;

    private final WebSocketServer webSocketServer;

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    JsonFormat.Printer printer = JsonFormat.printer();

    public TransferService(SocketClient socketClient, WebSocketServer webSocketServer) {

        this.socketClient = socketClient;
        this.webSocketServer = webSocketServer;
    }

    @EventListener
    public void handleSocketEvent(SocketEvent evn) {


        // 访问事件源和消息
        Object source = evn.getSource();
        if (source instanceof WebSocketServer) {
            webSocketServerChannelReadListener(evn.getMap());
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
                    case 注册:// 注册
                    case 心跳:// 心跳
                    case 图片上传完成通知:// 图片上传完成通知
                    case 云盒开关机通知:// 云盒开关机通知
                    case 信道质量:// 信道质量
                    case 状态数据:// 实时状态
                    case 遥测数据:// 实时遥测
                    case MOP数据透传:// MOP数据透传
//                        log.info("SocketClient_Instruct <<< {}", String.format("0x%02X", instruct));
                        break;
                    default:
                        int active = byteBuf.getByte(6) & 0xFF;
                        anEnum = InstructEnum.getEnum(instruct, active);
                        log.info("SocketClient_Instruct_Active <<< {}_{}", String.format("0x%02X", instruct), String.format("0x%02X", active));
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
        byte[] contentByte;
        byte isSuccess;
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

    private void webSocketServerChannelReadListener(Map<String, String> map) {

        Assert.notNull(map.get("指令编号"), "指令不能为: NULL");
        int instruct = Integer.parseInt(map.get("指令编号"), 16);
        if (socketClient.isActiveChannel()) {
            Assert.notNull(map.get("云盒编号"), "云盒编号不能为: NULL");
            InstructEnum anEnum;
            if (null != map.get("动作编号")) {
                int active = Integer.parseInt(map.get("动作编号"), 16);
                anEnum = InstructEnum.getEnum(instruct, active);
            } else {
                anEnum = InstructEnum.getEnum(instruct);
            }
            Assert.notNull(anEnum, "枚举类中未查询到指令!");
            ByteBuf buffer = aDefault.buffer();
            buffer.writeShort(InstructEnum.请求帧头.getInstruct());// 帧头
            buffer.writeShort(0);// 数据长度,占位临时赋值为0
            buffer.writeBytes(ByteUtil.stringToByte(map.get("云盒编号")));// 云盒编号
            buffer.writeByte(anEnum.getInstruct());// 指令编号
            buffer.writeByte(Byte.parseByte(map.get("加密标志")));// 加密标志
            buffer.writeByte(anEnum.getAction());// 动作编号
            switch (anEnum) {
                case 云台转动_基于角度_回中:
                case 云台转动_基于角度_上:
                case 云台转动_基于角度_右上:
                case 云台转动_基于角度_右:
                case 云台转动_基于角度_右下:
                case 云台转动_基于角度_下:
                case 云台转动_基于角度_左下:
                case 云台转动_基于角度_左:
                case 云台转动_基于角度_左上:
                case 云台转动_基于角度_停止:
                    buffer.writeByte(Integer.parseInt(map.get("转动角度")));
                    break;
                case 云台转动_基于角度_绝对值控制:
                    buffer.writeFloat(Float.parseFloat(map.get("俯仰")));
                    buffer.writeFloat(Float.parseFloat(map.get("平移")));
                    buffer.writeFloat(Float.parseFloat(map.get("横滚")));
                    break;
                case 变倍加:
                case 变倍减:
                case 变倍复位:
                case 变倍停止:
                    // TODO 变倍停止
                    break;
                case 变倍到指定倍数:
                    buffer.writeByte(Integer.parseInt(map.get("变倍数值")));
                    break;
                case 航线规划:
                    buffer.writeBytes(ByteUtil.stringToByte(map.get("航线数据")));
                    break;
                case 起飞:
                    buffer.writeFloat(Float.parseFloat(map.get("飞起高度")));
                    break;
                case 返航:
                case 返航到指定机场:
                    buffer.writeShort(Short.parseShort(map.get("返航高度")));
                    buffer.writeDouble(Double.parseDouble(map.get("机库经度")));
                    buffer.writeDouble(Double.parseDouble(map.get("机库纬度")));
                    buffer.writeDouble(Double.parseDouble(map.get("备降点经度")));
                    buffer.writeDouble(Double.parseDouble(map.get("备降点纬度")));
                    buffer.writeBytes(ByteUtil.stringToByte(map.get("机库ID")));
                    break;
                case 取消返航:
                case 降落:
                case 取消降落:
                case 开始航线:
                case 暂停航线:
                case 恢复航线:
                case 结束航线:
                    // TODO 结束航线
                    break;
                case 实时激光测距:
                case 云台设置跟随模式:
                case 云台设置姿态:
                case 设置返航高度:
                case 切换无人机控制权:
                case 紧急制动:
                case 水平避障设置:
                case 上避障设置:
                case 下避障设置:
                case 自动拍照:
                case 打开或关闭照片回传:
                case 设置入网方式:
                case 切换SIM卡:
                case 打开关闭AI识别:
                case 链路设置:
                case 喊话模式切换:
                case 设置循环播放:
                    buffer.writeByte(Integer.parseInt(map.get("数据")));
                    break;
                case 手动激光测距:
                case 对焦:
                case 打开单点测温:
                    buffer.writeFloat(Float.parseFloat(map.get("X点坐标")));
                    buffer.writeFloat(Float.parseFloat(map.get("Y点坐标")));
                    break;
                case 设置相机模式:
                case 切换视频源:
                case 切换摄像头:
                    buffer.writeByte(Integer.parseInt(map.get("动作参数")));
                    break;
                case 拍照:
                case 开始录像:
                case 停止录像:
                    // TODO 停止录像
                    break;
                case 方向控制:
                    buffer.writeFloat(Float.parseFloat(map.get("前后速度")));
                    buffer.writeFloat(Float.parseFloat(map.get("左右速度")));
                    buffer.writeFloat(Float.parseFloat(map.get("上下速度")));
                    buffer.writeFloat(Float.parseFloat(map.get("偏航角")));
                    buffer.writeShort(Short.parseShort(map.get("执行时间")));
                    break;
                case 强制降落:
                case 关闭单点测温:
                    // TODO 关闭单点测温
                    break;
                case 打开区域测温:
                    buffer.writeFloat(Float.parseFloat(map.get("X1点坐标")));
                    buffer.writeFloat(Float.parseFloat(map.get("Y1点坐标")));
                    buffer.writeFloat(Float.parseFloat(map.get("X2点坐标")));
                    buffer.writeFloat(Float.parseFloat(map.get("Y2点坐标")));
                    break;
                case 关闭区域测温:
                    // TODO 关闭区域测温
                    break;
                case 设置返航点:
                    buffer.writeDouble(Double.parseDouble(map.get("经度")));
                    buffer.writeDouble(Double.parseDouble(map.get("纬度")));
                    break;
                case 指点飞行:
                    buffer.writeDouble(Double.parseDouble(map.get("经度")));
                    buffer.writeDouble(Double.parseDouble(map.get("经度")));
                    buffer.writeFloat(Float.parseFloat(map.get("高度")));
                    buffer.writeFloat(Float.parseFloat(map.get("速度")));
                    buffer.writeByte(Integer.parseInt(map.get("飞行模式")));
                    break;
                case 停止指点飞行:
                case 格式化存储卡:
                    // TODO 格式化存储卡
                    break;
                case 云台转动_基于速度:
                    buffer.writeFloat(Float.parseFloat(map.get("俯仰")));
                    buffer.writeFloat(Float.parseFloat(map.get("平移")));
                    buffer.writeFloat(Float.parseFloat(map.get("横滚")));
                    buffer.writeShort(Short.parseShort(map.get("执行时间")));
                    break;
                case 设置视频码流:
                    buffer.writeShort(Short.parseShort(map.get("水平像素")));
                    buffer.writeShort(Short.parseShort(map.get("垂直像素")));
                    buffer.writeByte(Integer.parseInt(map.get("帧率")));
                    buffer.writeShort(Short.parseShort(map.get("码率")));
                    break;
                case 实时喊话:
                case 录音喊话:
                    buffer.writeBytes(Base64.getDecoder().decode(map.get("音频数据")));
                    break;
                case 文字喊话:
                    buffer.writeByte(Integer.parseInt(map.get("语速设置")));
                    buffer.writeByte(Integer.parseInt(map.get("声音")));
                    buffer.writeBytes(ByteUtil.stringToByte(map.get("文字数据")));
                    break;
                case 设置音量:
                    buffer.writeByte(Integer.parseInt(map.get("音量")));
                    break;
                case 停止喊话:
                    // TODO 停止喊话
                    break;
                case 开关灯:
                case 开关爆闪:
                case 功率限制开关:
                case 亮度设置:
                case 设置俯仰角:
                case 解锁开关:
                case 点火开关:
                case 喷火开关:
                case 燃料开关:
                case 其它开关:
                case 压力设置:
                case 档位设置:
                case 转动控制:
                case 设置喷火时间:
                case 避开喷火区开关:
                    buffer.writeByte(Integer.parseInt(map.get("动作数据")));
                    break;
                case MOP数据透传:
                    // TODO MOP数据透传
                    break;
                default:
                    throw new BusinessException("未知指令!");
            }
            buffer.setShort(2, buffer.readableBytes() - 4);//重新赋值数据长度
            socketClient.sendMessage(buffer);// 发送 TCP
        } else {
            if (instruct == InstructEnum.注册.getInstruct()) {
                socketClient.connect();
                return;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("action", "SERVER_ERROR");
            result.put("error", "客户端连接不存在, 请发送注册指令!");
            webSocketServer.sendMessage(map.get("云盒编号"), result);
        }
    }
}
