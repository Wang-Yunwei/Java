package com.mdsd.cloud.config;

import feign.Client;
import feign.Feign;
import feign.Logger;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangYunwei [2022-08-23]
 */
@Configuration
public class FeignHttpsConfig {

    @Bean
    public Feign.Builder feignBuilder() {

        final Client trustSSLSockets = this.client();
        return Feign.builder().client(trustSSLSockets);
    }

    @Bean
    public Client client() {

        return new Client.Default(
                TrustingSSLSocketFactory.get(), new NoopHostnameVerifier());
    }

    @Bean
    Logger.Level feignLoggerLevel() {

        /*NONE-没有日志记录
          BASIC-仅记录请求方法和URL以及响应状态代码和执行时间
          HEADERS-记录基本信息以及请求和响应头
          FULL-记录请求和响应的头、正文和元数据
        */
        return Logger.Level.FULL;
    }
}
