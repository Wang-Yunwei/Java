package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-08-24]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
public class AuthGetDepartmentsPo {

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    private String clientSecret = "keoqj";

    private String clientSign = "piat";
}
