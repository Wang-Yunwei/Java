package com.mdsd.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class PiatServerApplication {

    public static void main(final String[] args) {

        SpringApplication.run(PiatServerApplication.class, args);
        log.info("================== 【START-UP SUCCESSFUL】 ==================");
    }
}
