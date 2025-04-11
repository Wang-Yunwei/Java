package com.mdsd.cloud.controller.map.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-02-11]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
@ApiModel(value = "专科地图 - 保存or更新_入参")
public class SmcSaveOrUpdatePo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;
}
