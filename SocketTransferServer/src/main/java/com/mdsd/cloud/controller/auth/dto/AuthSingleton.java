package com.mdsd.cloud.controller.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-17]
 */
@Getter
@Setter
public class AuthSingleton {

    private Integer companyId;

    private String accessToken;

    private static AuthSingleton authSingleton = new AuthSingleton();

    private AuthSingleton(){}

    public static AuthSingleton getInstance(){

        return authSingleton;
    }
}
