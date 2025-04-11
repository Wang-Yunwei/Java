package com.mdsd.cloud.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-08-12]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
public class GetClientAccTokenByJwtRo {

    @JsonProperty(value = "access_token")
    private String access_token;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    private String scope;

    @JsonProperty(value = "jwt_token")
    private String jwtToken;

    private String updatePasswordNotice;

    private String username;

    private String appSign;

    private String userIp;
}
