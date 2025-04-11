package com.mdsd.cloud.feign.dto;

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

    private String hospitalId;

    private String clientSecret = "xiangmu3";

    private String clientSign = "phfa";
}
