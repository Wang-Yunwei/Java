package com.example.wangyunwei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class Java8Application {

    public static void main(final String[] args) {

        SpringApplication.run(Java8Application.class, args);
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
