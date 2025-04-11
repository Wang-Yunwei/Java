package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-01-06]
 */
@Getter
@Setter
@ApiModel(value = "Other - 其它专科查询急救病历接口_入参")
public class GetFirstAidMedicalRecordPao {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;
}
