package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 患者评分")
public class ScoreDto {

    @ApiModelProperty(value = "评分结果")
    private Float degreeResult;

    @ApiModelProperty(value = "评分医师")
    private String doctorId;

    @ApiModelProperty(value = "评分日期")

    private LocalDateTime operatorTime;

    @ApiModelProperty(value = "评分类别")
    private String vulnusType;

    @ApiModelProperty(value = "评分细项列表")
    private List<Object> details;
}
