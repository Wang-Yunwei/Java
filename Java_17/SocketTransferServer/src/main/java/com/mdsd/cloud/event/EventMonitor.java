package com.mdsd.cloud.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2024-10-29]
 */
@Component
public class EventMonitor {

    final ITyjwService service;

    public EventMonitor(ITyjwService service) {
        this.service = service;
    }

    @EventListener
    public void listen(CommonEvent param) throws JsonProcessingException {
        switch (param.getSource()) {
            case MDSD_WEB_SOCKET -> service.handleWebSocket(param.getMap());
            case TYJW_TCP_CLIENT -> service.handleTcpClient(param.getByteBuf());
            case JDI_UDP_SOCKET -> System.out.println("1");
            default -> System.out.println("default");
        }
    }
}
