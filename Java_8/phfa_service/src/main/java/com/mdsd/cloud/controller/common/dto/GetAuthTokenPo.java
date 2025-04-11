package com.mdsd.cloud.controller.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-23]
 */
@Getter
@Setter
@ApiModel(value = "GetAuthTokenPo对象", description = "Auth - 获取权限Token_入参")
public class GetAuthTokenPo {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username = "admin";

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password = "admin";

    /**
     * (可不传,默认值: password)
     */
    @ApiModelProperty(value = "默认值: password")
    private String grantType = "password";

    /**
     * (可不传,默认值: cloud-rbac)
     */
    @ApiModelProperty(value = "默认值: cloud-rbac")
    private String clientId = "phfa";

    /**
     * (可不传,默认值: acvb)
     */
    @ApiModelProperty(value = "默认值: acvb")
    private String clientSecret = "acvb";

    /**
     * 权限的jwtToken
     */
    @ApiModelProperty(value = "权限的jwtToken")
    private String authJwtToken;
}
