package com.mdsd.cloud.controller.body.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-11-12]
 */
@Getter
@Setter
@ApiModel(value = "体格检查 - 列表_入参")
public class GenListParamDto {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;
}
