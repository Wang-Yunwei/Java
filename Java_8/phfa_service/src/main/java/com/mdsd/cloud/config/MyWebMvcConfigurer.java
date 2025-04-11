package com.mdsd.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author WangYunwei [2022-04-14]
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Value("${linux.attachment.path}")
    private String filePath;

    public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/getAttachment/**").addResourceLocations(String.format("file:%s", "Linux".equals(System.getProperties().getProperty("os.name")) ? this.filePath : "D:\\"));
    }

    @Bean
    public CorsFilter corsFilter() {

        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(this.tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        new ArrayList<String>() {{
                            this.add("/webjars/**");
                            this.add("/v3/api-docs");
                            this.add("/swagger-ui/**");
                            this.add("/swagger-resources/**");
                            this.add("/**/*.png");
                            this.add("/**/*.jpg");
                            this.add("/**/*.jpeg");
                            this.add("/**/*.mp4");
                            this.add("/**/*.avi");
                        }}
                );
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory factory) {

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // key采用String的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // value序列化方式采用jackson
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
