package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-12-23]
 */
@Getter
@Setter
@ApiModel(value = "Other - 分诊患者列表_返参")
public class TriagePatientListResultDto {

    private String infoId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(1:男,2:女,3:未知)")
    private String gender;

    @ApiModelProperty(value = "急诊编号")
    private String regId;

    @ApiModelProperty(value = "就诊时年龄")
    private BigDecimal regAge;

    @ApiModelProperty(value = "患者主诉")
    private String subjective;

    @ApiModelProperty(value = "分诊时间")
    private LocalDateTime triageTime;

    @ApiModelProperty(value = "来院方式")
    private String arriveType;

    @ApiModelProperty(value = "到院时间")
    private LocalDateTime arriveTime;

    @ApiModelProperty(value = "证件类型")
    private String idcardType;

    @ApiModelProperty(value = "证件号码")
    private String identificationCard;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "就诊时间")
    private LocalDateTime regTime;

    @ApiModelProperty(value = "体温")
    private Float temperature;

    @ApiModelProperty(value = "脉搏(次/分钟)")
    private BigDecimal plus;

    @ApiModelProperty(value = "呼吸(次/分钟)")
    private BigDecimal breath;

    @ApiModelProperty(value = "收缩压(mmhg)")
    private Float sbp;

    @ApiModelProperty(value = "舒张压(mmhg)")
    private Float dbp;

    @ApiModelProperty(value = "SPO₂ (%)")
    private Float spo;

    @ApiModelProperty(value = "意识")
    private String mind;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime acqTime;

    @ApiModelProperty(value = "医院")
    private String hospitalId;

    @ApiModelProperty(value = "急救ID")
    private String phepPid;

    @ApiModelProperty(value = "院区")
    private String hospitalArea;
}
