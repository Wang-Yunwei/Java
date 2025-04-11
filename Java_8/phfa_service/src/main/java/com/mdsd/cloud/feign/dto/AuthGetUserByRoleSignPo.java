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
@Accessors(chain = true)
public class AuthGetUserByRoleSignPo {

    @ApiModelProperty(value = "角色(医生: AID_DOCTOR,护士: AID_NURSE)")
    private String roleSign;

    @ApiModelProperty(value = "医院ID")
    private String hosId;

}
