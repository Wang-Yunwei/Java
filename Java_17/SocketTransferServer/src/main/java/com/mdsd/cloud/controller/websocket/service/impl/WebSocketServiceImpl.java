package com.mdsd.cloud.controller.websocket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.websocket.dto.WsChannelDetails;
import com.mdsd.cloud.controller.websocket.service.IWebSocketService;
import com.mdsd.cloud.enums.CommonEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.util.MQClient;
import com.mdsd.cloud.util.SocketUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * @author WangYunwei [2025-03-04]
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements IWebSocketService {

    @Value("${env.port.sts.web_socket_server}")
    private int port;

    private final ConcurrentHashMap<String, WsChannelDetails> wsChannels = new ConcurrentHashMap<>();

    private final ObjectMapper obm = new ObjectMapper();

    private static final String pongMessage = "{\"action\":\"PONG_MESSAGE\"}";

    private static final String readyMessage = "{\"action\":\"READY_MESSAGE\"}";

    public static final String errorMessage = "{\"action\":\"PONG_MESSAGE\",\"message\":\"%s\"}";

    private final ApplicationEventPublisher publisher;

    public WebSocketServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ChannelHandler.Sharable
    class WebChannelInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame tws) throws JsonProcessingException {
            String text = tws.text();
            if (StringUtils.isNoneBlank(text)) {
                JsonNode jsonNode = obm.readTree(text);
                // 心跳数据直接回复
                if (null != jsonNode.get("action") && "PING_MESSAGE".equals(jsonNode.get("action").asText())) {
                    ctx.writeAndFlush(new TextWebSocketFrame(pongMessage));
                } else {
                    String boxNumber = jsonNode.get("云盒编号").asText();
                    String userId = jsonNode.get("用户ID").asText();
                    String taskId = jsonNode.get("任务ID").asText();
                    if (StringUtils.isNoneBlank(boxNumber) && StringUtils.isNoneBlank(userId) && StringUtils.isNoneBlank(taskId)) {
                        // 判断云盒是否已经注册
                        if (wsChannels.containsKey(boxNumber)) {
                            log.info("<<< {}", text);
                            WsChannelDetails wsChannelDetails = wsChannels.get(boxNumber);
                            // 云盒已经注册,判断任务是否注册
                            if (null != wsChannelDetails.getTaskId()) {
                                // 任务已经注册,判断用户是否注册
                                Map<String, Channel> channels = wsChannelDetails.getChannels();
                                if (channels.containsKey(userId)) {
                                    // 用户已注册,判断 channel 是否活跃
                                    Channel wsChannel = channels.get(userId);
                                    if (wsChannel == null || !wsChannel.isActive()) {
                                        // 用户下无活跃的 channel
                                        channels.put(userId, ctx.channel());
                                        log.info("用户 {} 已经注册,无活跃的channel,替换为当前的channel", userId);
                                    }
                                } else {
                                    log.info("用户 {} 未注册,执行注册到 {}", userId, boxNumber);
                                    channels.put(userId, ctx.channel());
                                    // 记录操作日志到 MQTT
                                    MQClient.publish(MQClient.userTopic, format(MQClient.userContent, userId, Boolean.TRUE).getBytes(), 1, false);
                                }
                            } else {
                                // 任务未注册,开始注册任务
                                wsChannelDetails.setTaskId(taskId);
                                log.info("任务 {} 未注册,执行注册到 {}", taskId, boxNumber);
                            }
                            // 判断是否有控制权
                            if (null != jsonNode.get("模块") && null != jsonNode.get("指令")) {
//                                if (userId.equals(wsChannelDetails.getControlPower()) || ("D1".equals(jsonNode.get("指令编号").asText()) && "30".equals(jsonNode.get("动作编号").asText()))) {
//                                    publisher.publishEvent(new CommonEvent( CommonEnum.getEnumByDesc(jsonNode.get("平台标识").asText()), jsonNode));
//                                }
                                publisher.publishEvent(new CommonEvent( CommonEnum.getEnumByDesc(jsonNode.get("平台标识").asText()), jsonNode));
                            }
                        } else {
                            // 云盒未注册
                            log.info("云盒 {} 未注册,开始注册云盒、任务、用户", boxNumber);
                            synchronized (wsChannels) {
                                wsChannels.put(boxNumber, new WsChannelDetails().setTaskId(taskId).setControlPower(userId).setChannels(new HashMap<>() {{
                                    put(userId, ctx.channel());
                                }}));
                                // 记录操作日志到 MQTT
                                MQClient.publish(MQClient.userTopic, format(MQClient.userContent, userId, Boolean.TRUE).getBytes(), 1, false);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.executor().schedule(() -> {
                ctx.writeAndFlush(new TextWebSocketFrame(readyMessage));
            }, 1, TimeUnit.SECONDS);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            ArrayList<String> keysToRemove = new ArrayList<>();
            wsChannels.forEach((key, value) -> {
                Map<String, Channel> channels = value.getChannels();
                Optional.ofNullable(channels).ifPresent(m -> m.forEach((k, v) -> {
                    if (!v.isActive()) {
                        log.info("用户 {} 断开连接", k);
                        // 记录操作日志到 MQTT
                        MQClient.publish(MQClient.userTopic, format(MQClient.userContent, k, Boolean.FALSE).getBytes(), 1, false);
                        // 判断任务是否为NULL
                        if (null == value.getTaskId()) {
                            // 任务执行完成
                            keysToRemove.add(key);
                        } else {
                            // 特殊处理: 判断断开连接的用户是否拥有当前飞控任务的控制权,如果有则按任务无法正常执行完成处理
                            if (k.equals(value.getControlPower())) {
                                keysToRemove.add(key);
                                // 记录操作日志到 MQTT
                                MQClient.publish(format(MQClient.taskTopic, key, value.getTaskId()), "{\"missionStatus\":1}".getBytes(), 1, false);
                            }
                        }
                    }
                }));
            });
            if (!keysToRemove.isEmpty()) {
                keysToRemove.forEach(wsChannels::remove);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error(cause.getMessage());
        }

    }

    @Override
    public void startWebListening() {
        SocketUtil.createWebSocketServer(new WebChannelInboundHandler(), port);
    }

    @Override
    public void sendMessage(String key, String data) {
        if (!StringUtils.isEmpty(key)) {
            WsChannelDetails wsChannelDetails = wsChannels.get(key);
            if (wsChannelDetails != null && StringUtils.isNoneBlank(wsChannelDetails.getTaskId())) {
                Map<String, Channel> channels = wsChannelDetails.getChannels();
                // 记录操作日志到 MQTT
                MQClient.publish(format(MQClient.taskTopic, key, wsChannelDetails.getTaskId()), data.getBytes(), 1, false);
                channels.forEach((k, v) -> {
                    if (v.isActive()) {
                        v.writeAndFlush(new TextWebSocketFrame(data));
                    }
                });
            }
        }
    }

    @Override
    public ConcurrentHashMap<String, WsChannelDetails> getWsChannels() {
        return wsChannels;
    }
}
