package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-07-11]
 */
@Getter
@Setter
@ApiModel(value = "FirstAidDetailsPo对象", description = "Other - 急救详情_入参")
public class FirstAidDetailsPo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;
}
