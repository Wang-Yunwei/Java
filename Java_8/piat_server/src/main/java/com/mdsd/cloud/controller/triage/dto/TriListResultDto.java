package com.mdsd.cloud.controller.triage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author WangYunwei [2021-10-26]
 */
@Getter
@Setter
@ApiModel(value = "体征辅助 - 详情_返参")
public class TriListResultDto {

    @ApiModelProperty(value = "体征辅助分诊表_主键ID")
    private String id;

    @ApiModelProperty(value = "体征类型")
    private String vitalSignType;

    @ApiModelProperty(value = "体征简码")
    private String vitalSignCode;

    @ApiModelProperty(value = "患者类型(0:成人,1:儿童)")
    private String patientType;

    @ApiModelProperty(value = "年龄分组(注: 只对儿童生效)")
    private String yearGroup;

    @ApiModelProperty(value = "起始月数")
    private BigDecimal beginMonth;

    @ApiModelProperty(value = "结束月数")
    private BigDecimal endMonth;

    @ApiModelProperty(value = "起始值")
    private Double levelBegin;

    @ApiModelProperty(value = "结束值")
    private Double levelEnd;

    @ApiModelProperty(value = "体征分诊级别")
    private String levelCode;

    @ApiModelProperty(value = "状态(0:是,1:否)")
    private String status;
}
