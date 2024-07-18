package com.mdsd.cloud.controller.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author WangYunwei [2024-07-17]
 */
@Getter
@Setter
public class AuthSingleton {

    private Integer companyId;

    private String accessToken;

    private Set<String> boxSnList;

    private static AuthSingleton authSingleton = new AuthSingleton();

    private AuthSingleton(){}

    public static AuthSingleton getInstance(){

        return authSingleton;
    }
}
