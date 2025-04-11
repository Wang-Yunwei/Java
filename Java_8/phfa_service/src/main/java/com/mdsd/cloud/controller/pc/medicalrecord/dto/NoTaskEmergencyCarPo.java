package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-12-13]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
@ApiModel(value = "NoTaskEmergencyCarPo对象", description = "病历管理 - 获取无任务急救车_入参")
public class NoTaskEmergencyCarPo {

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "院区")
    private String hospitalArea;
}
