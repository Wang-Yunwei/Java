package com.mdsd.cloud.configuration;

import com.mdsd.cloud.component.SocketClient;
import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.service.AuthService;
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

    final AuthService authService;

    final SocketClient socketClient;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    public RegSomeBean(AuthService authService, SocketClient socketClient) {
        this.authService = authService;
        this.socketClient = socketClient;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SocketTransferServer")
                        .description("This is a forwarding service")
                        .version("20140712"));
    }

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }

    /**
     * 程序启动后立即执行
     */
    @Bean
    CommandLineRunner startImmediatelyExecute(){

        return args -> {
            log.info("================== 【START-UP SUCCESSFUL】 ==================");
            authService.getToken();
            if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
                socketClient.connect();
            }
        };
    }
}
