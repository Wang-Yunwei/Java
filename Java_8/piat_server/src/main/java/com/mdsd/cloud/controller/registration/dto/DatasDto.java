package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 采集信息")
public class DatasDto {

    @ApiModelProperty(value = "生命体征")
    private VitalSignsDto vitalSigns;

    @ApiModelProperty(value = "到院信息")
    private ArriveInfoDto arriveInfo;

    @ApiModelProperty(value = "流行病学史")
    private InfectHistoryDto infectHistory;
}
