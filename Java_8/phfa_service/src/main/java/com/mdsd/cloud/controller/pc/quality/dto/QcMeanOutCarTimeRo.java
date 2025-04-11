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
public class QcMeanOutCarTimeRo {

    @ApiModelProperty(value = "出车时间<MedPhepMissionsEntity::DISPATCH_VEHICLE_TIME>")
    private LocalDateTime dispatchVehicleTime;

    @ApiModelProperty(value = "出车时间")
    private String outCatTimeStr;

    @ApiModelProperty(value = "到达医院时间<MedPhepMissionsEntity::ARRIVED_HOSPITAL_TIME>")
    private LocalDateTime arrivedHospitalTime;

    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity::DISPATCH_VEHICLE_SITE>")
    private String dispatchVehicleSite;
}
