package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wcq
 */
@Data
public class PhepMissionsInitVo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

    /**
     * 患者信息ID
     */
    @ApiModelProperty(value = "患者信息ID")
    private String patientId;

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID")
    private String recordId;
}
