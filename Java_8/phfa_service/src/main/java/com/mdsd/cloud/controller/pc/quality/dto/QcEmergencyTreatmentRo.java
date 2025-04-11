package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-07-01]
 */
@Getter
@Setter
public class QcEmergencyTreatmentRo {

    @ApiModelProperty(value = "措施标识<MedPhepMeasuresEntity::MEASURE_KEY>")
    private String measureKey;

    @ApiModelProperty(value = "措施名称<MedPhepMeasuresEntity::MEASURE_NAME>")
    private String measureName;
}
