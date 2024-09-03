package com.mdsd.cloud.mdsdadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class MdsdAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdsdAdminApplication.class, args);
    }

}
