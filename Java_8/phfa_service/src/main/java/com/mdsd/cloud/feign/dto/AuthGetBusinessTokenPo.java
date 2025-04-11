package com.mdsd.cloud.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-08-30]
 */
@Getter
@Setter
@Accessors(chain = true)
public class AuthGetBusinessTokenPo {

    @JsonProperty(value = "jwtToken")
    private String authJwtToken;
    
    private String clientId = "phfa";
}
