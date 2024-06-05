package com.example.controller.sse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-15]
 */
@Getter
@Setter
@ApiModel(value = "PushDateToSSEPo", description = "SSE - 推送数据_入参")
public class PushDatePo {

    /**
     * sseID
     */
    @ApiModelProperty(value = "sseID")
    private String sseId;

    /**
     * 推送内容
     */
    @ApiModelProperty(value = "推送内容")
    private String content;
}
