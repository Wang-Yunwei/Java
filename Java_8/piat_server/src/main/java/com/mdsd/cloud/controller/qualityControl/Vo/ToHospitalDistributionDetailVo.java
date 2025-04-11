package com.mdsd.cloud.controller.qualityControl.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@ApiModel("来院方式分布明细")
public class ToHospitalDistributionDetailVo {

    @ApiModelProperty("卡号表主键ID")
    private String INFO_ID;

    @ApiModelProperty("分诊时间")
    private LocalDateTime triageTime;

    @ApiModelProperty("来院方式")
    private String arriveType;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("病历号")
    private String patientId;

    @ApiModelProperty("急诊编号")
    private String regId;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("年龄")
    private float regAge;

    @ApiModelProperty("年龄补充")
    private String regAgeCaption;

    @ApiModelProperty("分诊级别")
    private String resetLevel;

    @ApiModelProperty("去向")
    private String turnResult;

}
