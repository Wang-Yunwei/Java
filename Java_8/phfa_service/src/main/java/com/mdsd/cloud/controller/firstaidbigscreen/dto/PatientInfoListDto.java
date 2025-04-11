package com.mdsd.cloud.controller.firstaidbigscreen.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-04-06]
 */
@Getter
@Setter
public class PatientInfoListDto {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "所属任务ID")
    private String missionId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 年龄拓展
     */
    @ApiModelProperty(value = "年龄拓展")
    private String ageExtend;

    /**
     * 疾病类别
     */
    @ApiModelProperty(value = "疾病类别")
    private String diseaseCategory;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 交接科室
     */
    @ApiModelProperty(value = "交接科室")
    private String dept;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;
}
