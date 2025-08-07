package com.mdsd.cloud.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2024-10-29]
 */
@Slf4j
@Component
public class EventMonitor {

    final ITyjwService tyjwService;

    final IDjiService djiService;

    public EventMonitor(ITyjwService tyjwService, IDjiService djiService) {
        this.tyjwService = tyjwService;
        this.djiService = djiService;
    }

    @EventListener
    public void listen(CommonEvent param) throws JsonProcessingException {
        switch (param.getSource()) {
            case WEB_SOCKET_TYJW -> tyjwService.handleWebSocket(param.getJsonNode());
            case WEB_SOCKET_DJI -> djiService.handleWebSocket(param.getJsonNode());
            case TCP_SOCKET_TYJW -> tyjwService.handleTcpClient(param.getByteBuf());
            case UDP_SOCKET_DJI -> djiService.handleUdpSocket(param.getJsonNode());
            default -> log.error("未知平台指令!");
        }
    }
}
