package com.mdsd.cloud.controller.pc.emergencycar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-05-24]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
@ApiModel(value = "EccPageListPo", description = "急救车 - 分页列表_入参")
public class EccPageListPo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    private String hospitalArea;

    /**
     * 行政区域ID
     */
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private long current;

    /**
     * 页长
     */
    @ApiModelProperty(value = "页长")
    private long size;
}
