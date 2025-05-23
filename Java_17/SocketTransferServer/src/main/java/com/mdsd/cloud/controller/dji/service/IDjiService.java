package com.mdsd.cloud.controller.dji.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;

/**
 * @author WangYunwei [2024-11-01]
 */
public interface IDjiService {

    /**
     * 开始 UDP 监听
     */
    void startUdpListening();

    /**
     * 处理WEB SOCKET
     */
    void handleWebSocket(JsonNode jsonNode);

    /**
     * 处理UDP SOCKET
     */
    void handleUdpSocket(DjiProtoBuf.Payload payload);
}
