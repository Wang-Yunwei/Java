package com.mdsd.cloud.controller.qualityControl.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@ApiModel("分诊正确率报表charts查询返回值")
public class TriageWrongRatioChartsVo {

    @ApiModelProperty("错误类型")
    private String errType;

    @ApiModelProperty("错误数量")
    private int count;

    @ApiModelProperty("时间")
    private String triageTime;

}
