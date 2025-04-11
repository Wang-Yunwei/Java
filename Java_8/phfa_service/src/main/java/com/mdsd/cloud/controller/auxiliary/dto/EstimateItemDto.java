package com.mdsd.cloud.controller.auxiliary.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-12-01]
 */
@Getter
@Setter
public class EstimateItemDto {

    /**
     * 评分项目明细表ID
     */
    @ApiModelProperty(value = "评分项目明细表ID")
    private String id;

    /**
     * 类别
     */
    private String type;

    /**
     * 值
     */
    private String value;
}
