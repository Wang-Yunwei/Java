package com.mdsd.cloud.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-14]
 */
@Getter
@Setter
public class TyRequestInterceptor implements RequestInterceptor {

    private String companyId;

    private String accessToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header("x-cid",companyId);
        requestTemplate.header("x-token",accessToken);
    }
}
