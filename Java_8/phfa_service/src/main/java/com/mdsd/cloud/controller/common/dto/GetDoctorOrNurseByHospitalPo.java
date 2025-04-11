package com.mdsd.cloud.controller.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-29]
 */
@Getter
@Setter
@ApiModel(value = "GetDoctorOrNurseByHospitalPo对象", description = "Auth - 根据医院ID获取医生Or护士_入参")
public class GetDoctorOrNurseByHospitalPo {

    /**
     * 角色(医生: AID_DOCTOR,护士: AID_NURSE)
     */
    @ApiModelProperty(value = "角色(医生: AID_DOCTOR,护士: AID_NURSE)")
    private String roleSign;

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hosId;

    /**
     * 权限的jwtToken
     */
    @ApiModelProperty(value = "权限的jwtToken")
    private String authJwtToken;
}
