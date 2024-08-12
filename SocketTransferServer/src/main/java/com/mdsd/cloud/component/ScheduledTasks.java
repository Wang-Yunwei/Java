package com.mdsd.cloud.component;

import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2024-08-10]
 */
@Slf4j
@Component
public class ScheduledTasks {

    AuthService authService;

    SocketClient socketClient;

    public ScheduledTasks(AuthService authService, SocketClient socketClient) {
        this.authService = authService;
        this.socketClient = socketClient;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void every_5min() {
        log.info("every_5min");
        if (!socketClient.isActiveChannel()) {
            authService.getToken(new GetTokenInp());
        }
    }
}
