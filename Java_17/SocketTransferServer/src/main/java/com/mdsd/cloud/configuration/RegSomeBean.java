package com.mdsd.cloud.configuration;

import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import io.minio.MinioClient;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangYunwei [2024-08-10]
 */
@Slf4j
@Configuration
public class RegSomeBean {

    final ITyjwService tyjwService;

    final IDjiService djiService;

    public RegSomeBean(ITyjwService tyjwService, IDjiService djiService) {
        this.tyjwService = tyjwService;
        this.djiService = djiService;
    }

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    @Bean
    CommandLineRunner startImmediatelyExecute() {

        return args -> {
            log.info("================== 【START-UP SUCCESSFUL】 ==================");
            tyjwService.getToken();
            if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
                tyjwService.startTcpClient();
            }
            tyjwService.startWebSocket();
//            djiService.startUdp();
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
