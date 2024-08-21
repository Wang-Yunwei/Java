package com.mdsd.cloud.component;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final AuthSingleton auth = AuthSingleton.getInstance();

    public ScheduledTasks(AuthService authService, SocketClient socketClient) {
        this.authService = authService;
        this.socketClient = socketClient;
    }

    @Scheduled(cron = "* */5 * * * ?")
    public void TimedTask_AUTH_Token() {
        log.info("IsToken_AUTH >>> Every 5min!");
        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
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
