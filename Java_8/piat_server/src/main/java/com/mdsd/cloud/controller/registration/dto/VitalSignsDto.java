package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 生命体征")
public class VitalSignsDto {

    @ApiModelProperty(value = "是否有生命迹象(0:否,1：是)")
    private String haslife;

    @ApiModelProperty(value = "拒绝TPRBP(0:否,1：是)")
    private String notprbp;

    @ApiModelProperty(value = "体温")
    private Float temperature;

    @ApiModelProperty(value = "脉搏(次/分钟)")
    private BigDecimal plus;

    @ApiModelProperty(value = "呼吸(次/分钟)")
    private BigDecimal breath;

    @ApiModelProperty(value = "舒张压(mmhg)")
    private Float dbp;

    @ApiModelProperty(value = "收缩压(mmhg)")
    private Float sbp;

    @ApiModelProperty(value = "SPO₂ (%)")
    private Float spo;

    @ApiModelProperty(value = "意识")
    private String mind;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime acqTime;
}
