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
@ApiModel(value = "个人登记 - 分级")
public class GradeDto {

    @ApiModelProperty(value = "系统分级")
    private String syslevel;

    @ApiModelProperty(value = "护士分诊的级别")
    private String resetLevel;
}
