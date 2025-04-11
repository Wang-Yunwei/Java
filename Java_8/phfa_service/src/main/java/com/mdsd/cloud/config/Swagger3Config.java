package com.mdsd.cloud.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangYunwei [2021-10-28]
 */
@EnableOpenApi
@Configuration
public class Swagger3Config {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder().title("MedicalSystem - PHFA")//标题
                        .description("麦迪斯顿 - 院前急救")//描述
                        .version("3.0")//版本
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(new ArrayList<SecurityScheme>() {{
                    this.add(new ApiKey("jwt-token", "jwt-token", "header"));
                }})
                .securityContexts(this.securityContexts());
    }

    private List<SecurityContext> securityContexts() {

        final List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(this.defaultAuth())
                .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        final List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("jwt-token", authorizationScopes));
        return securityReferences;
    }
}
