package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-06-29]
 */
@Getter
@Setter
public class QcDiseaseEntitiesRo {
    
    @ApiModelProperty(value = "任务ID<MedPhepMissionsEntity::MISSION_ID>")
    private String missionId;

    @ApiModelProperty(value = "呼叫时间<MedPhepMissionsEntity::CALL_TIME>")
    private LocalDateTime callTime;

    @ApiModelProperty(value = "状态<MedPhepMissionsEntity::STATUS>")
    private String status;

    @ApiModelProperty(value = "车牌号<MedPhepMissionsEntity::LICENSE_PLATE>")
    private String licensePlate;

    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity::DISPATCH_VEHICLE_SITE>")
    private String dispatchVehicleSite;

    @ApiModelProperty(value = "患者ID<MedPhepPatientsEntity::PATIENT_ID>")
    private String patientId;

    @ApiModelProperty(value = "病历ID<MedPhepMedicalRecordsEntity::RECORD_ID>")
    private String recordId;

    @ApiModelProperty(value = "疾病类别<MedPhepMedicalRecordsEntity::DISEASE_CATEGORY>")
    private String diseaseCategory;

    @ApiModelProperty(value = "车辆ID<MedPhepVehiclesEntity::ID>")
    private String id;
}
