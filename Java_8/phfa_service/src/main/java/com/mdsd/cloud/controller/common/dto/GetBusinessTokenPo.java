package com.mdsd.cloud.controller.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-22]
 */
@Getter
@Setter
@ApiModel(value = "GetTokenPo对象", description = "Auth - 获取Token_入参")
public class GetBusinessTokenPo extends GetAuthTokenPo {

    /**
     * 权限的jwtToken
     */
    @ApiModelProperty(value = "权限的jwtToken")
    private String authJwtToken;
}
