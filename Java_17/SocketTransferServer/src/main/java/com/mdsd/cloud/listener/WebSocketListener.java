package com.mdsd.cloud.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.netty.SocketClient;
import com.mdsd.cloud.netty.WebSocketServer;
import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.tyjw.dto.TyjwProtoBuf;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.utils.ByteUtil;
import com.mdsd.cloud.utils.ParameterMapping;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2024-09-04]
 */
@Slf4j
@Component
public class WebSocketListener {

    ObjectMapper obm = new ObjectMapper();

    private final PooledByteBufAllocator aDefault = PooledByteBufAllocator.DEFAULT;

    private final SocketClient socketClient;

    private final WebSocketServer webSocketServer;

    public WebSocketListener(SocketClient socketClient, WebSocketServer webSocketServer) {
        this.socketClient = socketClient;
        this.webSocketServer = webSocketServer;
    }

    @FunctionalInterface
    private interface WebSocketFunction<T1, T2, T3> {

        void dataHandle(T1 arg1, T2 arg2, T3 arg3);
    }

    @EventListener
    public void listen(SocketEvent evn) throws JsonProcessingException {
        if (evn.getSource() instanceof WebSocketServer) {
            Map<String, String> map = evn.getMap();
            int instruct = Integer.parseInt(map.get("指令编号"), 16);
            if (socketClient.isActiveChannel()) {
                Assert.notNull(map.get("云盒编号"), "云盒编号不能为: NULL");
                Assert.notNull(map.get("动作编号"), "动作编号不能为: NULL");
                TyjwEnum anEnum = TyjwEnum.getEnum(instruct, Integer.parseInt(map.get("动作编号"), 16));
                if (null != anEnum) {
                    log.info("<<< {}", anEnum.name());
                    switch (anEnum) {
                        case 实时喊话 -> {
                            // TODO 暂不支持
                            byte[] inData = Base64.getDecoder().decode(map.get("音频数据"));
                            List<byte[]> bytes = ByteUtil.splitByteArray(inData, 110);
                            for (byte[] by : bytes) {
                                ByteBuf buf = aDefault.buffer();
                                sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> arg1.writeBytes(by));
                            }
                        }
                        case 航线规划 -> {
                            PlanLineDataDTO planLineDataDto = obm.readValue(map.get("航线数据"), PlanLineDataDTO.class);
                            TyjwProtoBuf.PlanLineData planLineData = ParameterMapping.getPlanLineData(planLineDataDto);
                            ByteBuf buf = aDefault.buffer();
                            sendByteBuf(buf, anEnum, map, (arg1, arg2, arg3) -> arg1.writeBytes(planLineData.toByteArray()));
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
                if (instruct == TyjwEnum.注册.getInstruct()) {
                    socketClient.connect();
                    return;
                }
                wssErrorMessage(map.get("云盒编号"), "客户端连接不存在, 请发送注册指令!");
            }
        }
    }

    private void sendByteBuf(ByteBuf buf, TyjwEnum anEnum, Map<String, String> map, WebSocketFunction<ByteBuf, String[], Map<String, String>> fun) {

        buf.writeShort(TyjwEnum.请求帧头.getInstruct());// 帧头
        buf.writeShort(0);// 数据长度,占位临时赋值为0
        buf.writeBytes(ByteUtil.stringToByte(map.get("云盒编号")));// 云盒编号
        buf.writeByte(anEnum.getInstruct());// 指令编号
        buf.writeByte(Byte.parseByte(map.get("加密标志")));// 加密标志
        buf.writeByte(anEnum.getAction());// 动作编号
        fun.dataHandle(buf, anEnum.getArgs(), map);// 参数处理
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
