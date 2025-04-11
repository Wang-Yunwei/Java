package com.mdsd.cloud.controller.qualityControl.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author huxiaoneng
 * @version 1.0
 * 各级患者查询统计返回
 */

@Data
public class PatientMonthProportionReportVo {

    @ApiModelProperty(value = "月份")
    private String triageTime;

    @ApiModelProperty(value = "一级")
    private String aLevel;

    @ApiModelProperty(value = "一级人数")
    private int aPnumber;

    @ApiModelProperty(value = "一级占比")
    private String aProportion;

    @ApiModelProperty(value = "二级")
    private String bLevel;

    @ApiModelProperty(value = "二级患者")
    private int bPnumber;

    @ApiModelProperty(value = "二级占比")
    private String bProportion;

    @ApiModelProperty(value = "三级")
    private String cLevel;

    @ApiModelProperty(value = "三级患者")
    private int cPnumber;

    @ApiModelProperty(value = "三级占比")
    private String cProportion;

    @ApiModelProperty(value = "四级")
    private String dLevel;

    @ApiModelProperty(value = "四级患者")
    private int dPnumber;

    @ApiModelProperty(value = "四级占比")
    private String dProportion;

    @ApiModelProperty(value = "对应等级患者总数")
    private int patientCount;
}
