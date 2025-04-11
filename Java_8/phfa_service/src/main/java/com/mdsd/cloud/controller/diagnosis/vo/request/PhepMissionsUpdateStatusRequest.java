package com.mdsd.cloud.controller.diagnosis.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wcq
 * @date 2021/11/15 17:26
 */
@Data
public class PhepMissionsUpdateStatusRequest {

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "患者信息ID")
    private String patientId;

    @ApiModelProperty(value = "状态")
    private String status;
}
