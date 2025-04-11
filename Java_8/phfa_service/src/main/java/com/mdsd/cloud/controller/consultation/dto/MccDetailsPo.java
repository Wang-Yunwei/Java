package com.mdsd.cloud.controller.consultation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-03-23]
 */
@Getter
@Setter
@ApiModel(value = "移动会诊 - 患者详情_入参")
public class MccDetailsPo {

    @ApiModelProperty(value = "患者ID")
    private String patientId;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;
}
