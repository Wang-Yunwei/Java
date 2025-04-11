package com.mdsd.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class PhfaServerApplication {

    public static void main(final String[] args) {

        SpringApplication.run(PhfaServerApplication.class, args);
        log.info("================== 【START-UP SUCCESSFUL】 ==================");
    }
}
