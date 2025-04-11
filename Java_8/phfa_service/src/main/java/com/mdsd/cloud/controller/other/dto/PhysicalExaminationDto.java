package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-01-06]
 */
@Getter
@Setter
@ApiModel(value = "体格检查")
public class PhysicalExaminationDto {

    @ApiModelProperty(value = "检查时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "呼吸频率(次/分)")
    private Integer rr;

    @ApiModelProperty(value = "脉搏(次/分)")
    private Integer pulse;

    @ApiModelProperty(value = "血氧")
    private String spo2;

    @ApiModelProperty(value = "收缩压")
    private Float sbp;

    @ApiModelProperty(value = "舒张压")
    private Float dbp;

    @ApiModelProperty(value = "意识")
    private String consciousness;

    @ApiModelProperty(value = "瞳孔")
    private String pupilResponse;

    @ApiModelProperty(value = "对光反应")
    private String lightReflection;

    @ApiModelProperty(value = "体温")
    private Float t;
}
