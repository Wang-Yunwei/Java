package com.mdsd.cloud.configuration;

import com.mdsd.cloud.utils.TcpClient;
import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2024-08-10]
 */
@Slf4j
@Component
public class ScheduledTasks {

    ITyjwService tyjwService;

    TcpClient tcpClient;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    public ScheduledTasks(ITyjwService tyjwService, TcpClient tcpClient) {
        this.tyjwService = tyjwService;
        this.tcpClient = tcpClient;
    }

    @Scheduled(cron = "0 0 19 * * ?")
    public void TimedTask_Token() {
        log.info("Get Access Token >>> Every day at 19:00");
        if (Strings.isBlank(auth.getAccessToken())) {
            tyjwService.getToken();
        }
    }

//    @Scheduled(cron = "*/30 * * * * ?")
//    public void TimedTask_Connect() {
//        log.info("Estimate_ConnectStatus >>> Every 30second!");
//        if (!tcpClient.isActiveChannel()) {
//            tcpClient.connect();
//        }
//    }
}
