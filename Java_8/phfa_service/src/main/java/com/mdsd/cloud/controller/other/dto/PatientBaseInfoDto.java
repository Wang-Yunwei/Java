package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-01-17]
 */
@Getter
@Setter
@ApiModel(value = "患者信息")
public class PatientBaseInfoDto {

    @ApiModelProperty(value = "患者ID")
    private String patientId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "年龄单位")
    private String ageExtend;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "出生年月")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "证件类型")
    private String cardType;

    @ApiModelProperty(value = "证件号码")
    private String cardNo;

    @ApiModelProperty(value = "急诊Id")
    private String fromEmergencyId;

    @ApiModelProperty(value = "任务ID")
    private String missionId;
}
