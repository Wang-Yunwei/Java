package com.mdsd.cloud.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WangYunwei [2024-07-15]
 */
@Slf4j
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    @Value("${filter.preHandle.log}")
    private boolean preHandleLog;

    public class InterceptorImpl implements HandlerInterceptor {
        @Override
        public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
            if(preHandleLog){
                log.info("前置拦截器 URL: {}", request.getRequestURL());
            }
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorImpl());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
