package com.mdsd.cloud.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "权限Token")
    @JsonProperty(value = "jwtToken")
    private String authJwtToken;

    @ApiModelProperty(value = "服务ID")
    private String clientId = "piat";
}
