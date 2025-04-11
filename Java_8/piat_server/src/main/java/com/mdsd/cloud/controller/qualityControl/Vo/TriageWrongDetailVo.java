package com.mdsd.cloud.controller.qualityControl.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@ApiModel(value = "质控统计-分诊正确率详情")
public class TriageWrongDetailVo {

    private int rowNum;

    @ApiModelProperty("急诊编号")
    private String regId;

    @ApiModelProperty("错误类型 1:科室错误，2:级别错误")
    private String errType;

    @ApiModelProperty("分诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime triageTime;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("病历号")
    private String patientId;

    @ApiModelProperty("修改前值")
    private String oldValue;

    @ApiModelProperty("修改后值")
    private String newValue;

    @ApiModelProperty("修改人员")
    private String createId;

}
