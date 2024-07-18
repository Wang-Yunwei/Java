package com.mdsd.cloud.controller.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.transfer.dto.BaseInp;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.socket.WebSocketServer;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
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
                InstructEnum anEnum;
                int instruct = byteBuf.getByte(4) & 0xFF;
                int active = byteBuf.getByte(6) & 0xFF;
                // 指令过滤
                switch (instruct){
                    case 0x01:
                    case 0x02:
                    case 0x09:
                    case 0x0C:
                    case 0xA8:
                    case 0xA9:
                    case 0xDC:
                        anEnum = InstructEnum.getEnum(instruct);
                        break;
                    default:
                        anEnum = InstructEnum.getEnum(instruct,active);
                        break;
                }
                handleSocketClientData(anEnum,byteBuf);
            }
        } else {
            throw new RuntimeException("未知事件源!");
        }
    }

    private void handleSocketClientData(InstructEnum param,ByteBuf buf) {

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
        switch (anEnum){
            case 注册:
                BaseInp baseInp = new BaseInp().setCompanyId(AuthSingleton.getInstance().getCompanyId())
                        .setAccessToken(param.get("accessToken").getBytes());
                nettyClient.create(boxSn, baseInp);
                break;
            case 状态数据:

                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register(Integer companyId,String accessToken,String boxSn){

        BaseInp baseInp = new BaseInp();

    }

}
