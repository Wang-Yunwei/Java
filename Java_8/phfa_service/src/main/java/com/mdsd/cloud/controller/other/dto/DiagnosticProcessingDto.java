package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-01-06]
 */
@Getter
@Setter
@ApiModel(value = "诊断处理")
public class DiagnosticProcessingDto {

    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    @ApiModelProperty(value = "病情判断")
    private String conditionGrade;

    @ApiModelProperty(value = "急救措施")
    private String treatment;

    @ApiModelProperty(value = "用药信息")
    private List<EmergencyOrdersDto> emergencyOrders;

    @ApiModelProperty(value = "病情转归")

    private String rescueChangeResult;

    @ApiModelProperty(value = "出诊结果")
    private String treatmentResult;

    @ApiModelProperty(value = "送往医院")
    private String toHospital;
}
