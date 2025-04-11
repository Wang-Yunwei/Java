package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-20]
 */
@Getter
@Setter
@ApiModel(value = "TimeAxisRo对象", description = "病历管理 - 时间轴_返参")
public class TimeAxisRo {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID <MED_PHEP_PATIENTS::PATIENT_ID>")
    private String patientId;

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID <MedPhepMedicalRecordsEntity::RECORD_ID>")
    private String recordId;

    /**
     * 发病时间
     */
    @ApiModelProperty(value = "发病时间 <MedPhepMedicalRecordsEntity::MORBTIMEAREA>")
    private String morbtimearea;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID <MedPhepMissionsEntity::MISSION_ID>")
    private String missionId;

    /**
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间 <MedPhepMissionsEntity::CALL_TIME>")
    private LocalDateTime callTime;

    /**
     * 接令时间
     */
    @ApiModelProperty(value = "接令时间 -> 派车时间 <MedPhepMissionsEntity::PLAN_VEHICLE_TIME>")
    private LocalDateTime planVehicleTime;

    /**
     * 出车时间
     */
    @ApiModelProperty(value = "出车时间 <MedPhepMissionsEntity::DISPATCH_VEHICLE_TIME>")
    private LocalDateTime dispatchVehicleTime;

    /**
     * 到达现场时间
     */
    @ApiModelProperty(value = "到达现场时间 <MedPhepMissionsEntity::ARRIVED_SCENE_TIME>")
    private LocalDateTime arrivedSceneTime;

    /**
     * 接触患者时间
     */
    @ApiModelProperty(value = "接触患者时间 <MedPhepMissionsEntity::TOUCH_PATIENT_TIME>")
    private LocalDateTime touchPatientTime;

    /**
     * 离开现场时间
     */
    @ApiModelProperty(value = "离开现场时间 -> 送往医院时间 <MedPhepMissionsEntity::LEFT_SCENE_TIME>")
    private LocalDateTime leftSceneTime;

    /**
     * 到达医院时间
     */
    @ApiModelProperty(value = "到达医院时间 <MedPhepMissionsEntity::ARRIVED_HOSPITAL_TIME>")
    private LocalDateTime arrivedHospitalTime;

    /**
     * 附件ID
     */
    @ApiModelProperty(value = "附件ID <MedPhepAttachmentEntity::ID>")
    private String attId;

    /**
     * 首份心电图时间
     */
    @ApiModelProperty(value = "首份心电图时间 -> 创建时间 <MedPhepAttachmentEntity::CREATED_DATE>")
    private LocalDateTime firstEcgTime;

    /**
     * 用药ID
     */
    @ApiModelProperty(value = "用药ID <MedPhepEmergencyOrdersEntity::ORDER_ID>")
    private String orderId;

    /**
     * 首次用药时间
     */
    @ApiModelProperty(value = "首次用药时间 -> 创建时间 <MedPhepEmergencyOrdersEntity::CREATETIME>")
    private LocalDateTime firstMedicationTime;

    /**
     * 交接ID
     */
    @ApiModelProperty(value = "交接ID <MedPhepShiftRecordEntity::SHIFT_RECORD_ID>")
    private String shiftRecordId;

    /**
     * 交接完成时间
     */
    @ApiModelProperty(value = "交接完成时间 -> 提交时间 <MedPhepShiftRecordEntity>")
    private LocalDateTime submitTime;

    /**
     * 预警ID
     */
    @ApiModelProperty(value = "预警ID <MedPhepNoticesEntity::ID>")
    private String noticesId;

    /**
     * 预警时间
     */
    @ApiModelProperty(value = "预警时间 -> 通知时间 <MedPhepNoticesEntity::NOTICE_TIME>")
    private LocalDateTime earlyWarningTime;
}
