package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 流行病学史")
public class InfectHistoryDto {

    @ApiModelProperty(value = "死亡或者动物接触史")
    private String diseaseTouch;

    @ApiModelProperty(value = "疫区旅游史")
    private String epidemicArea;

    @ApiModelProperty(value = "流行病病史")
    private String infectMsg;

    @ApiModelProperty(value = "入院前三天有发热学史")
    private String isHvaeFeverHistory;

    @ApiModelProperty(value = "24小时体温超过38度")
    private String isFever;

    @ApiModelProperty(value = "发热时间")
    private LocalDateTime feverTime;
}
