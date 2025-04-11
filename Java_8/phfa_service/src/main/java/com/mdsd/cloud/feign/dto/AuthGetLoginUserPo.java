package com.mdsd.cloud.feign.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-22]
 */
@Getter
@Setter
public class AuthGetLoginUserPo {

    private String clientSign = "phfa";

    private String clientSecret = "xiangmu3";
}
