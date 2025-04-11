package com.mdsd.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wcq
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Value("${linux.attachment.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/getAttachment/**").addResourceLocations(String.format("file:%s", "Linux".equals(System.getProperties().getProperty("os.name")) ? this.filePath : "D:\\"));
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(this.tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html", "/webjars/**", "/v3/api-docs", "/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "/**/*.png", "*.html", "/**/*.html");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {

        return builder.build();
    }

}
