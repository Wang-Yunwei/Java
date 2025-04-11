package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiaoneng.h
 * @date 2022/12/21 9:07
 */
@Data
public class EstimateDto {

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 评分名
     */
    @ApiModelProperty(value = "评分名")
    private String name;

    /**
     * 评分时间
     */
    @ApiModelProperty(value = "评分时间")
    private LocalDateTime time;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值")
    private Float value;

    /**
     * 评分详情
     */
    @ApiModelProperty(value = "评分详情")
    private List<EstimateDetailDto> estimateDetails;
}
