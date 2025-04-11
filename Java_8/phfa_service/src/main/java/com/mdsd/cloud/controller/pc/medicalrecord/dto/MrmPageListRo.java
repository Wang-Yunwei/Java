package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-13]
 */
@Getter
@Setter
@ApiModel(value = "MrmPageListRo对象", description = "病历管理 - 分页列表_返参")
public class MrmPageListRo {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID")
    private String recordId;

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
     * 年龄类别
     */
    @ApiModelProperty(value = "年龄类别")
    private String ageExtend;

    /**
     * 主诉
     */
    @ApiModelProperty(value = "主诉")
    private String chiefComplaint;

    /**
     * 接诊地址
     */
    @ApiModelProperty(value = "接诊地址 --> 现场地址(DB) ")
    private String destinationAddress;

    /**
     * 急救车
     */
    @ApiModelProperty(value = "急救车")
    private String emergencyCar;

    /**
     * 出车医生
     */
    @ApiModelProperty(value = "出车医生 --> 车载医生(DB)")
    private String doctorName;

    /**
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间")
    private LocalDateTime callTime;

    /**
     * 呼叫原因
     */
    @ApiModelProperty(value = "呼叫原因 --> 呼救原因(DB)")
    private String reasonType;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    /**
     * 病历状态
     */
    @ApiModelProperty(value = "病历状态")
    private String medicalRecordStatus;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")
    private String missionStatus;

    /**
     * 任务终止类型
     */
    @ApiModelProperty(value = "任务终止类型")
    private String suspendType;

    /**
     * 任务终止原因
     */
    @ApiModelProperty(value = "任务终止原因")
    private String suspendReason;

    @ApiModelProperty(value = "现场救治地点<MedPhepMedicalRecordsEntity::ADDRESS>")
    private String address;
}
