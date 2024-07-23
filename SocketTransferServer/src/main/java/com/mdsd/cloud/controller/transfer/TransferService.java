package com.mdsd.cloud.controller.transfer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.socket.WebSocketServer;
import com.mdsd.cloud.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private final SocketClient nettyClient;

    private final WebSocketServer webSocketServer;

    private final ObjectMapper om = new ObjectMapper();

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    public TransferService(SocketClient nettyClient, WebSocketServer webSocketServer) {

        this.nettyClient = nettyClient;
        this.webSocketServer = webSocketServer;
    }

    @EventListener
    public void handleSocketEvent(SocketEvent evn) {

        // 访问事件源和消息
        Object source = evn.getSource();
        if (source instanceof WebSocketServer) {
            Map<String, String> msg = evn.getMap();
            webSocketServerChannelReadListener(msg);
        } else if (source instanceof SocketClient) {
            ByteBuf byteBuf = evn.getByteBuf();
            if (byteBuf.getShort(0) == 0x6A77) {
                InstructEnum anEnum;
                int instruct = byteBuf.getByte(4) & 0xFF;
                // 指令过滤
                switch (instruct) {
                    case 0x01:// 注册
                    case 0x02:// 心跳
                    case 0x09:// 图片上传完成通知
                    case 0x0A:// 云盒开关机通知
                    case 0x0C:// 信道质量
                    case 0xA8:// 实时状态
                    case 0xA9:// 实时遥测
                        anEnum = InstructEnum.getEnum(instruct);
                        break;
                    case 0xDC:// MOP数据透传
                        return;
                    default:
                        int active = byteBuf.getByte(6) & 0xFF;
                        anEnum = InstructEnum.getEnum(instruct, active);
                        break;
                }
                if (null != anEnum) {
                    socketClientChannelRead0Listener(anEnum, byteBuf);
                }
            }
        } else {
            throw new RuntimeException("未知事件源!");
        }
    }

    private void socketClientChannelRead0Listener(InstructEnum param, ByteBuf buf) {

        buf.skipBytes(5);
        Map<String, Object> result = new HashMap<>();
        result.put("指令编码", param.getInstruct());
        ConcurrentHashMap<String,Channel> channelMap = webSocketServer.getChannelMap();
        byte[] boxSnByte = new byte[15];// 云盒编号
        byte[] dataByte;
        switch (param) {
            case 注册:
                result.put("是否成功", buf.readByte());
                channelMap.forEach((key, value) -> webSocketServer.sendMessage(key, ResponseDto.wrapSuccess(result)));
                break;
            case 心跳:
                // 将心跳回复发送给所有 WebSocketClient
                result.put("时间戳", buf.readLong());// 此值为心跳指令发送的时间戳原样返回
                channelMap.forEach((key, value) -> webSocketServer.sendMessage(key, ResponseDto.wrapSuccess(result)));
                break;
            case 图片上传完成通知:
                result.put("加密标志", buf.readByte());
                result.put("经度", buf.readDouble());
                result.put("纬度", buf.readDouble());
                result.put("时间戳", buf.readLong());
                result.put("原图大小", buf.readLong());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                dataByte = new byte[buf.readableBytes()];
                buf.readBytes(dataByte);
                result.put("原图地址", ByteUtil.bytesToStringUTF8(dataByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 云盒开关机通知:
                result.put("状态", buf.readByte());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 信道质量:
                result.put("时间戳", buf.readUnsignedInt());// 终端到平台的延时
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                dataByte = new byte[buf.readableBytes()];
                buf.readBytes(dataByte);
                result.put("数据", ByteUtil.bytesToStringUTF8(dataByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 状态数据:
            case 遥测数据:
                dataByte = new byte[buf.readableBytes()];
                buf.readBytes(dataByte);
                try {
                    Map<String,String> map = om.readValue(dataByte, new TypeReference<>() {

                    });
                    result.put("云盒SN号", map.get("boxSn"));
                } catch (IOException e) {
                    throw new RuntimeException("状态数据 byte[] 转 Map 异常!");
                }
                result.put("数据", ByteUtil.bytesToStringUTF8(dataByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
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
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("执行结果", buf.readByte());
                result.put("错误码", buf.readInt());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 实时激光测距:
            case 手动激光测距:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("执行结果", buf.readByte());
                result.put("经度", buf.readDouble());
                result.put("纬度", buf.readDouble());
                result.put("海拔高度", buf.readFloat());
                result.put("距离", buf.readFloat());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 打开单点测温:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("执行结果", buf.readByte());
                result.put("X点坐标", buf.readFloat());
                result.put("Y点坐标", buf.readFloat());
                result.put("温度", buf.readFloat());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 打开区域测温:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("执行结果", buf.readByte());
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
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 无人机准备完成通知:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("电池电量", buf.readByte());
                result.put("经度", buf.readDouble());
                result.put("纬度", buf.readDouble());
                result.put("海拔高度", buf.readInt());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            case 机场任务完成通知:
                result.put("加密标志", buf.readByte());
                result.put("动作编号", buf.readByte() & 0xFF);
                result.put("媒体文件数量", buf.readShort());
                buf.readBytes(boxSnByte);
                result.put("云盒SN号", ByteUtil.bytesToStringUTF8(boxSnByte));
                webSocketServer.sendMessage(result.get("云盒SN号").toString(), ResponseDto.wrapSuccess(result));
                break;
            default:
                throw new RuntimeException("未知指令!");
        }
    }

    private void webSocketServerChannelReadListener(Map<String, String> map) {

        Assert.notNull(map.get("指令编号"), "指令不能为: NULL");
        Assert.notNull(map.get("云盒编号"), "云盒编号不能为: NULL");
        int instruct = Integer.parseInt(map.get("指令编号"), 16);
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
                buffer.writeBytes(ByteUtil.stringToByte(map.get("音频数据")));
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
                throw new RuntimeException("未知指令!");
        }
        buffer.setShort(2, buffer.readableBytes() - 4);//重新赋值数据长度
        nettyClient.sendMessage(buffer);// 发送 TCP
        buffer.release();// 释放 ByteBuf
    }
}
