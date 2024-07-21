package com.mdsd.cloud.controller.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.transfer.dto.BaseInp;
import com.mdsd.cloud.controller.transfer.dto.ResultOup;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.socket.WebSocketServer;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private SocketClient nettyClient;

    private WebSocketServer webSocketServer;

    private ObjectMapper om = new ObjectMapper();

    private PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    public TransferService(SocketClient nettyClient, WebSocketServer webSocketServer) {

        this.nettyClient = nettyClient;
        this.webSocketServer = webSocketServer;
    }

    @EventListener
    public void handleSocketEvent(SocketEvent evn) {

        // 访问事件源和消息
        Object source = evn.getSource();
        if (source instanceof WebSocketServer) {
            Map<String, String> msg = (Map<String, String>) evn.getMsg();
            webSocketHandle(msg);
        } else if (source instanceof SocketClient) {
            ByteBuf byteBuf = (ByteBuf) evn.getMsg();
            if (byteBuf.getShort(0) == 0x6A77) {
                InstructEnum anEnum;
                int instruct = byteBuf.getByte(4) & 0xFF;
                // 指令过滤
                switch (instruct){
                    case 0x01:// 注册
                    case 0x02:// 心跳
                    case 0x09:// 图片上传完成通知
                    case 0x0C:// 信道质量
                    case 0xA8:// 实时状态
                    case 0xA9:// 实时遥测
                    case 0xDC:// MOP数据透传
                        anEnum = InstructEnum.getEnum(instruct);
                        break;
                    default:
                        int active = byteBuf.getByte(6) & 0xFF;
                        anEnum = InstructEnum.getEnum(instruct,active);
                        break;
                }
                socketClientHandle(anEnum,byteBuf);
            }
        } else {
            throw new RuntimeException("未知事件源!");
        }
    }

    private void socketClientHandle(InstructEnum param,ByteBuf buf) {

        switch (param) {
            case 注册:
                byte isSuccess = buf.getByte(5);
                if(isSuccess == 0){
                    log.info("客户端连接TY成功!");
                }
                break;
            case 心跳:
                // 将心跳回复发送给所有 WebSocketClient
                ByteBuf slice = buf.slice(5, 8);
                ResponseDto<ResultOup> resultOupResponseDto = ResponseDto.wrapSuccess(new ResultOup().setInstructNum(param.getInstruct()).setTimestamp(slice.getLong(0)));
                ConcurrentHashMap<String, Channel> channelMap = webSocketServer.getChannelMap();
                channelMap.forEach((key,value) -> webSocketServer.sendMessage(key,resultOupResponseDto));
                break;
            case 图片上传完成通知:

                break;
            case 信道质量:

                break;
            case 状态数据:

                break;
            case 遥测数据:

                break;
            default:
                break;
        }
    }

    private void webSocketHandle(Map<String, String> param) {

        Assert.notNull(param.get("instructNum"),"指令不能为: NULL");
        Assert.notNull(param.get("boxSn"),"云盒编号不能为: NULL");
        int instruct = Integer.parseInt(param.get("instructNum"), 16);
        String boxSn = param.get("boxSn");
        InstructEnum anEnum;
        if(null != param.get("activeNum")){
            int active = Integer.parseInt(param.get("activeNum"), 16);
            anEnum = InstructEnum.getEnum(instruct, active);
        }else{
            anEnum = InstructEnum.getEnum(instruct);
        }
        ByteBuf buffer = aDefault.buffer();
        buffer.writeShort(InstructEnum.请求帧头.getInstruct());// 帧头
        switch (anEnum){

            case 起飞:
                buffer.writeShort(0);// 数据长度,占位临时赋值为0
                buffer.writeBytes(ByteUtil.stringToByte(boxSn));// 云盒编号
                buffer.writeByte(anEnum.getInstruct());// 指令编号
                buffer.writeByte(0);// 加密标志
                buffer.writeByte(anEnum.getAction());// 动作编号
                if (null != param.get("data")){
                    buffer.writeFloat(Float.parseFloat(param.get("data")));// 起飞高度
                }
                buffer.setShort(2, buffer.readableBytes()-4);//重新赋值数据长度
                byte[] bytes = new byte[buffer.readableBytes()];
                buffer.readBytes(bytes);
                nettyClient.sendMessage(bytes);// 发送 TCP
                buffer.release();// 释放 ByteBuf
                break;
            case 降落:
                buffer.writeShort(18);// 数据长度,占位临时赋值为0
                buffer.writeBytes(boxSn.getBytes());// 云盒编号
                buffer.writeByte(anEnum.getInstruct());// 指令编号
                buffer.writeByte(0);// 加密标志
                buffer.writeByte(anEnum.getAction());// 动作编号
                nettyClient.sendMessage(buffer);
                buffer.release();
                break;
            default:
                break;
        }
    }
}
