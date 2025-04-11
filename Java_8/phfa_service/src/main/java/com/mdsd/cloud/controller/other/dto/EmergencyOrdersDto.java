package com.mdsd.cloud.controller.other.dto;

import com.mdsd.cloud.entity.MedPhepDrugEntity;
import com.mdsd.cloud.entity.MedPhepEmergencyOrdersEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-03-23]
 */
@Getter
@Setter
@ApiModel(value = "用药信息")
public class EmergencyOrdersDto {

    @ApiModelProperty(value = "用药信息")
    private MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity;

    @ApiModelProperty(value = "药品信息")
    private MedPhepDrugEntity medPhepDrugEntity;

    @ApiModelProperty(value = "专科对应类别")
    private List<Map> drugCategoryList;
}
