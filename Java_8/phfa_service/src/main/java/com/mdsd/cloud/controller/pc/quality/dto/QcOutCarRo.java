package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-30]
 */
@Getter
@Setter
public class QcOutCarRo {

    @ApiModelProperty(value = "呼叫时间<MedPhepMissionsEntity::CALL_TIME>")
    private String callTime;
}
