package com.mdsd.cloud.configuration;

import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.utils.MQClient;
import io.minio.MinioClient;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author WangYunwei [2024-08-10]
 */
@Slf4j
@Configuration
public class RegBeanConf {

    final ITyjwService tyjwService;
    final IDjiService djiService;

    public RegBeanConf(ITyjwService tyjwService, IDjiService djiService) {
        this.tyjwService = tyjwService;
        this.djiService = djiService;
    }

    private final AuthSingleton auth = AuthSingleton.getInstance();

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    CommandLineRunner startImmediatelyExecute() {
        return args -> {
            log.info("================== 【START-UP SUCCESSFUL】 ==================");
            if ("Linux".equals(System.getProperties().getProperty("os.name"))) {
                tyjwService.getToken();
                if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
                    log.info("Access token: {}", auth.getAccessToken());
                    tyjwService.startTcpClient();
                }
                tyjwService.startWebSocket();
            } else {
                djiService.startUdp();
            }
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("SocketTransferServer").description("This is a forwarding service").version("20140712"));
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }
}
