package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-10-11]
 */
@Getter
@Setter
@ApiModel(value = "基本信息 - 详情_患者信息_入参")
public class GetCardInfoParamDto {

    @ApiModelProperty(value = "证件类别")
    private String idcardType;
    
    @ApiModelProperty(value = "证件号码")
    private String identificationCard;
}
