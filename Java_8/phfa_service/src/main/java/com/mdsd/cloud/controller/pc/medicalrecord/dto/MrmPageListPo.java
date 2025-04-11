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
@ApiModel(value = "MrmPageListPo对象", description = "病历管理 - 分页列表_入参")
public class MrmPageListPo {

    /**
     * 呼叫时间 - 开始
     */
    @ApiModelProperty(value = "呼叫时间 - 开始<MedPhepMissionsEntity>")
    private LocalDateTime callTimeStart;

    /**
     * 呼叫时间 - 结束
     */
    @ApiModelProperty(value = "呼叫时间 - 结束<MedPhepMissionsEntity>")
    private LocalDateTime callTimeEnd;

    /**
     * 患者姓名
     */
    @ApiModelProperty(value = "患者姓名<MedPhepPatientsEntity>")
    private String patientName;

    /**
     * 患者性别
     */
    @ApiModelProperty(value = "患者性别<MedPhepPatientsEntity>")
    private String patientSex;

    /**
     * 急救车
     */
    @ApiModelProperty(value = "急救车<MedPhepMissionsEntity>")
    private String emergencyCar;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity>")
    private String dispatchVehicleSite;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态<MedPhepMissionsEntity>")
    private String missionStatus;

    /**
     * 出车医院
     */
    @ApiModelProperty(value = "出车医院(注: hospitalId)<MedPhepVehiclesEntity>")
    private String drawOutHospital;

    /**
     * 送达医院
     */
    @ApiModelProperty(value = "送达医院(注: hospitalId)<MedPhepMedicalRecordsEntity>")
    private String escortToHospital;

    /**
     * 疾病类别
     */
    @ApiModelProperty(value = "疾病类别<MedPhepMedicalRecordsEntity>")
    private String diseaseCategory;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断<MedPhepMedicalRecordsEntity>")
    private String diagnosis;

    /**
     * 病情判断
     */
    @ApiModelProperty(value = "病情判断<MedPhepMedicalRecordsEntity>")
    private String conditionGrade;

    /**
     * 出诊结果
     */
    @ApiModelProperty(value = "出诊结果<MedPhepMedicalRecordsEntity>")
    private String treatmentResult;

    /**
     * 病历状态
     */
    @ApiModelProperty(value = "病历状态<MedPhepMedicalRecordsEntity>")
    private String medicalRecordStatus;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private long current;

    /**
     * 页长
     */
    @ApiModelProperty(value = "页长")
    private long size;
}
