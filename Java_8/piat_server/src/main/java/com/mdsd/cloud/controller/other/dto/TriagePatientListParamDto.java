package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-12-23]
 */
@Getter
@Setter
@ApiModel(value = "Other - 分诊患者列表_入参")
public class TriagePatientListParamDto {

    @ApiModelProperty(value = "分诊编号")
    private String regId;

    @ApiModelProperty(value = "1-腕带编号字段为NULL")
    private Byte isTagNo;

    @ApiModelProperty(value = "院区")
    private String hospitalArer;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "页码")
    private Long current;

    @ApiModelProperty(value = "页长")
    private Long pageSize;
}
