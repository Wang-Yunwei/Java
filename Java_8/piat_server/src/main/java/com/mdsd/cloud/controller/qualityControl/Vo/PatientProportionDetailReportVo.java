package com.mdsd.cloud.controller.qualityControl.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @version 1.0
 * 各级患者详情返回
 */
@Data
public class PatientProportionDetailReportVo {

    private String infoId;

    @ApiModelProperty(value = "分诊时间")
    private LocalDateTime triageTime;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "病历号")
    private String patientId;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private String regAge;

    @ApiModelProperty(value = "年龄补充")
    private String regAgeCaption;

    @ApiModelProperty(value = "诊断")
    private String diagnoseDescribe;

    @ApiModelProperty(value = "分诊级别")
    private String resetLevel;

    @ApiModelProperty(value = "就诊科室")
    private String departmentId;

}
