package com.mdsd.cloud.controller.pc.emergencycar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-26]
 */
@Getter
@Setter
@ApiModel(value = "EccPageListRo对象", description = "急救车 - 分页列表_返参")
public class EccPageListRo {

    /**
     * 急救车ID
     */
    @ApiModelProperty(value = "急救车ID")
    private String id;

    /**
     * 车辆代码
     */
    @ApiModelProperty(value = "车辆代码")
    private String code;

    /**
     * 车牌
     */
    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    /**
     * 车载电话
     */
    @ApiModelProperty(value = "车载电话")
    private String carPhone;

    /**
     * 归属医院
     */
    @ApiModelProperty(value = "归属医院")
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private int status;
}
