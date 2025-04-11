package com.mdsd.cloud.controller.qualityControl.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@ApiModel("分诊正确率报表月度查询返回值")
public class TriageWrongMonthRatioVo {

    @ApiModelProperty("时间")
    private String triageTime;

    @ApiModelProperty("科室错误数量")
    private int aCount;

    @ApiModelProperty("科室错误占比")
    private String aRatio;

    @ApiModelProperty("级别错误数量")
    private int bCount;

    @ApiModelProperty("级别错误占比")
    private String bRatio;

    @ApiModelProperty("错误总数")
    private int cCount;

    @ApiModelProperty("错误总数占比")
    private String cRatio;

    @ApiModelProperty("患者总数")
    private int patientCount;

}
