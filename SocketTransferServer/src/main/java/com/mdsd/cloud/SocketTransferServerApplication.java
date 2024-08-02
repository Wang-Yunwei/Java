package com.mdsd.cloud;

import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

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

}
