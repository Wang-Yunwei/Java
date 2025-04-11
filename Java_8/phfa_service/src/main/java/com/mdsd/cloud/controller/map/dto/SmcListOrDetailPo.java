package com.mdsd.cloud.controller.map.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-02-11]
 */
@Getter
@Setter
@ApiModel(value = "专科地图 - 列表or详情_入参")
public class SmcListOrDetailPo {

    /**
     * 主键ID(注:该字段存在查详情)
     */
    @ApiModelProperty(value = "主键ID(注:该字段存在查详情)")
    private String id;

    /**
     * 任务ID(注:该字段存在根据missionId查列表,否则查所有)
     */
    @ApiModelProperty(value = "任务ID(注:该字段存在根据missionId查列表,否则查所有)")
    private String missionId;
}
