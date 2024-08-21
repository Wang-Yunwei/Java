package com.mdsd.cloud.component;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author WangYunwei [2024-08-10]
 */
@Slf4j
@Component
public class ScheduledTasks {

    AuthService authService;

    SocketClient socketClient;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    public ScheduledTasks(AuthService authService, SocketClient socketClient) {
        this.authService = authService;
        this.socketClient = socketClient;
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void TimedTask_AUTH_Token() {
        log.info("IsToken_AUTH >>> Every 5min!");
        if (Strings.isBlank(auth.getAccessToken())) {
            authService.getToken();
        }
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void TimedTask_SC_Connect() {
        log.info("IsConnect_SC >>> Every 30second!");
        if (!socketClient.isActiveChannel()) {
            socketClient.connect();
        }
    }
}
