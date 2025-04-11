package com.mdsd.cloud.controller.triage.dto;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-10-26]
 */
@Getter
@Setter
@ApiModel(value = "体征辅助 - 列表_入参")
public class TriListParamDto extends BaseDto {

    @ApiModelProperty(value = "体征类型")
    private String vitalSignType;

    @ApiModelProperty(value = "患者类型(0:成人,1:儿童)")
    private String patientType;

    @ApiModelProperty(value = "状态(0:是,1:否)")
    private String status;

/*    @ApiModelProperty(value = "页码")
    private long pageNo;

    @ApiModelProperty(value = "页长")
    private long pageSize;*/
}
