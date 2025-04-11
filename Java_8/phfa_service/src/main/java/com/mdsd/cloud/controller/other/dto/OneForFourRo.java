package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-11-23]
 */
@Getter
@Setter
public class OneForFourRo {

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "病历ID")
    private String recordId;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "院区")
    private String hospitalArea;
}
