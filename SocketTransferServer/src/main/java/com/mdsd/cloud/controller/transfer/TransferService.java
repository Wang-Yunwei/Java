package com.mdsd.cloud.controller.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.socket.WebSocketServer;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author WangYunwei [2024-07-11]
 */
@Slf4j
@Service
public class TransferService {

    private SocketClient nettyClient;

    private WebSocketServer webSocketServer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public TransferService(SocketClient nettyClient, WebSocketServer webSocketServer) {

        this.nettyClient = nettyClient;
        this.webSocketServer = webSocketServer;
    }

    @EventListener
    public void handleSocketEvent(SocketEvent evn) {

        // 访问事件源和消息
        Object source = evn.getSource();

        if (source instanceof WebSocketServer) {
            Object msg = evn.getMsg();
            if (msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame textMsg = (TextWebSocketFrame) msg;
                String text = textMsg.text();
                try {
                    Map<String, String> map = objectMapper.readValue(text, Map.class);
                    log.info(map.toString());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("数据解析失败");
                }
            } else if (msg instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binaryMsg = (BinaryWebSocketFrame) msg;
                ByteBuf cnt = binaryMsg.content();
                byte[] array = cnt.array();
                log.error("二进制数据 >>> {}", array.toString());
                cnt.release();
            } else {
                throw new RuntimeException("未知数据类型");
            }
        } else if (source instanceof SocketClient) {
            ByteBuf byteBuf = (ByteBuf) evn.getMsg();

            if(byteBuf.getShort(0) == 0x6A77){
//                handleInstruct()
            }


            if(byteBuf.getShort(0) == 0x6A77){
                switch (byteBuf.getByte(4)) {//指令编码
                    case 0x01:
                        break;
                    case 0x02:
                    default:
                        break;
                }
            }

//            nettyClient.sendMessage(cnt);
        } else {

        }
    }

    private void handleInstruct(InstructEnum param){

        switch (param){

        }
    }
}
