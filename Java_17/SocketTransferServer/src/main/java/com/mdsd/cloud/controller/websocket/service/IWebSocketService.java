package com.mdsd.cloud.controller.websocket.service;

import com.mdsd.cloud.controller.websocket.dto.WsChannelDetails;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2025-03-04]
 */

public interface IWebSocketService {

    void startWebListening();

    void sendMessage(String key, String data);

    ConcurrentHashMap<String, WsChannelDetails> getWsChannels();
}
