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
public class QcMeanResponseTimeRo {

    @ApiModelProperty(value = "呼叫时间<MedPhepMissionsEntity::CALL_TIME>")
    private LocalDateTime callTime;

    @ApiModelProperty(value = "呼叫时间")
    private String callTimeStr;

    @ApiModelProperty(value = "出车时间<MedPhepMissionsEntity::DISPATCH_VEHICLE_TIME>")
    private LocalDateTime dispatchVehicleTime;

    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity::DISPATCH_VEHICLE_SITE>")
    private String dispatchVehicleSite;

}
