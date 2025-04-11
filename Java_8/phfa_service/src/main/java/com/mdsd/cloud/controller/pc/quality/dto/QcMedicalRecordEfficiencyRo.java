package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-07-01]
 */
@Getter
@Setter
public class QcMedicalRecordEfficiencyRo {

    @ApiModelProperty(value = "任务ID<MedPhepMissionsEntity::MISSION_ID>")
    private String missionId;

    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity::DISPATCH_VEHICLE_SITE>")
    private String dispatchVehicleSite;

    @ApiModelProperty(value = "创建时间<MedPhepMedicalRecordsEntity::CREATE_TIME>")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间<MedPhepMedicalRecordsEntity::UPDATE_TIME>")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "状态<MedPhepMedicalRecordsEntity::STATUS>")
    private String medicalRecordStatus;
}
