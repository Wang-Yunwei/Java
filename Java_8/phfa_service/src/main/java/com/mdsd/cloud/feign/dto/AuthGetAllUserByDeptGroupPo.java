package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-08-29]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
public class AuthGetAllUserByDeptGroupPo {

    @ApiModelProperty(value = "模糊查询值")
    private String queryValue;

    @ApiModelProperty(value = "默认值: base")
    private String clientSign = "base";

    @ApiModelProperty(value = "默认值: qwsq")
    private String clientSecret = "qwsq";

    @ApiModelProperty(value = "默认值: RBAC")
    private String hospitalSign = "RBAC";
}
