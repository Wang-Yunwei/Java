package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-30]
 */
@Getter
@Setter
public class QcPatientRo {

    @ApiModelProperty(value = "患者创建时间")
    private String createTime;
}
