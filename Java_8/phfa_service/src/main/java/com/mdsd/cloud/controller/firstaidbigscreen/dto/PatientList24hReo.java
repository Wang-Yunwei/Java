package com.mdsd.cloud.controller.firstaidbigscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-04-06]
 */
@Getter
@Setter
@ApiModel(value = "急救公告大屏 - 24h内急救患者列表_返参")
public class PatientList24hReo {

    /**
     * 患者信息列表
     */
    @ApiModelProperty(value = "患者信息列表")
    private List<PatientInfoListDto> patientInfoListDtoList;

    /**
     * 胸痛
     */
    @ApiModelProperty(value = "胸痛")
    private int pectoralgia = 0;

    /**
     * 卒中
     */
    @ApiModelProperty(value = "卒中")
    private int stroke = 0;

    /**
     * 创伤
     */
    @ApiModelProperty(value = "创伤")
    private int trauma = 0;

    /**
     * 其它危急重症
     */
    @ApiModelProperty(value = "其它危急重症")
    private int otherDisease = 0;

    /**
     * 目前经救护车入院总数
     */
    @ApiModelProperty(value = "目前经救护车入院总数")
    private int nowAmbulanceToHospitalTotal = 0;
}
