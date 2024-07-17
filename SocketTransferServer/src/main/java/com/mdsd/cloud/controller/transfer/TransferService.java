package com.mdsd.cloud.controller.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.socket.WebSocketServer;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private SocketClient nettyClient;

    private WebSocketServer webSocketServer;

    private ObjectMapper om = new ObjectMapper();

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
            handleWebSocketData(msg);
        } else if (source instanceof SocketClient) {
            ByteBuf byteBuf = (ByteBuf) evn.getMsg();
            if (byteBuf.getShort(0) == 0x6A77) {
                int instruct = byteBuf.getByte(4) & 0xFF;
                int active = byteBuf.getByte(6) & 0xFF;
                handleSocketClientData(byteBuf,InstructEnum.getEnum(instruct,active));
            }
        } else {
            throw new RuntimeException("未知事件源!");
        }
    }

    private void handleSocketClientData(ByteBuf buf,InstructEnum param) {

        switch (param) {
            case 注册:

//                webSocketServer.sendMessage(AuthSingleton);

                break;
            case 心跳:

                break;
            default:
                break;
        }
    }

    private void handleWebSocketData(Map<String, String> param) {

        int instruct = Integer.parseInt(param.get("instructNum"), 16);
        int active = Integer.parseInt(param.get("activeNum"), 16);
        InstructEnum anEnum = InstructEnum.getEnum(instruct, active);
        switch (anEnum){
//            case 注册 -> "xx";

        }
    }

    /**
     * 注册
     */
    private void register(){

    }

}
