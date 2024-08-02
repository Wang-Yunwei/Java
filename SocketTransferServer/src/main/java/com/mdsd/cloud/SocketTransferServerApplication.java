package com.mdsd.cloud;

import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class SocketTransferServerApplication {

    static AuthService authService;

    public SocketTransferServerApplication(AuthService authService) {

        SocketTransferServerApplication.authService = authService;
    }

    public static void main(String[] args) {

        SpringApplication.run(SocketTransferServerApplication.class, args);

        authService.getToken(new GetTokenInp());
    }

    /**
     * 程序启动后立即执行
     */
    @Bean
    CommandLineRunner startImmediatelyExecute(){

        return args -> {
            log.info("================== 【START-UP SUCCESSFUL】 ==================");
        };
    }

}
