package com.mdsd.cloud.controller.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author WangYunwei [2022-08-15]
 */
@Getter
@Setter
@ApiModel(value = "PushDateToSSEPo", description = "SSE - 推送数据_入参")
public class PushDateToSSEPo {

    /**
     * sseID
     */
    @NotBlank(message = "sseId不能为:NULL")
    @ApiModelProperty(value = "sseID")
    private String sseId;

    /**
     * 推送内容
     */
    @ApiModelProperty(value = "推送内容")
    private String content;
}
