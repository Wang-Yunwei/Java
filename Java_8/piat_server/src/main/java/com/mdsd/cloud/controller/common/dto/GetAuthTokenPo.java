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

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "默认值: password")
    private String grantType = "password";

    @ApiModelProperty(value = "默认值: piat")
    private String clientId = "piat";

    @ApiModelProperty(value = "默认值: keoqj")
    private String clientSecret = "keoqj";
}
