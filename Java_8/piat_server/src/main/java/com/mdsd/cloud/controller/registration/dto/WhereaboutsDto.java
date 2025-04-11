package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 患者去向")
public class WhereaboutsDto {

    @ApiModelProperty(value = "就诊科室")
    private String departmentId;

    @ApiModelProperty(value = "就诊医生")
    private String checkDoctorId;

    @ApiModelProperty(value = "去向")
    private String turnResult;
}
