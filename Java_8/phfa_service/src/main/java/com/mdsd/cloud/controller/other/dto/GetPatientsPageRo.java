package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-03-09]
 */
@Getter
@Setter
@ApiModel(value = "Other - 患者列表_返参")
public class GetPatientsPageRo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    private String hospitalArer;

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
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间")
    private LocalDateTime callTime;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;
}
