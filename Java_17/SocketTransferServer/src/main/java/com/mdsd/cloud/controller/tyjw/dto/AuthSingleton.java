package com.mdsd.cloud.controller.tyjw.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author WangYunwei [2024-07-17]
 */
@Getter
@Setter
public class AuthSingleton {

    private Integer companyId;

    private String accessToken;

    private String currentHost;

    private Set<String> webSocketKeys;

    private static AuthSingleton authSingleton = new AuthSingleton();

    private AuthSingleton(){}

    public static AuthSingleton getInstance(){

        return authSingleton;
    }
}
