package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-05-19]
 */
@Getter
@Setter
@ApiModel(value = "MrmDetailsPo对象", description = "病历管理 - 详情入参")
public class MrmDetailsPo {

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID")
    private String recordId;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;
}
