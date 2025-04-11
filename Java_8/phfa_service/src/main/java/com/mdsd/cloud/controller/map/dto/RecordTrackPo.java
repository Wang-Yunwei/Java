package com.mdsd.cloud.controller.map.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-05-06]
 */
@Getter
@Setter
@ApiModel(value = "RecordTrackPo对象", description = "专科地图 - 记录轨迹_入参")
public class RecordTrackPo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

    /**
     * 当前点
     */
    @ApiModelProperty(value = "当前点")
    private CurrentPointsDto currentPoints;
}
