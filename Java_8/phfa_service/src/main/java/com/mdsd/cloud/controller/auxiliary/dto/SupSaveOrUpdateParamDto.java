package com.mdsd.cloud.controller.auxiliary.dto;

import com.mdsd.cloud.entity.MedPhepPoctEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-11-15]
 */
@Getter
@Setter
@ApiModel(value = "辅助检查 - 新增or更新_入参")
public class SupSaveOrUpdateParamDto {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 血糖
     */
    @ApiModelProperty(value = "血糖")
    private Float bloodglucose;

    /**
     * 其他检查
     */
    @ApiModelProperty(value = "其他检查")
    private String otherCheck;

    /**
     * 评分入参
     */
    @ApiModelProperty(value = "评分入参")
    private EstimateDto estimateDto;

    /**
     * POCT对象
     */
    @ApiModelProperty(value = "POCT")
    private MedPhepPoctEntity medPhepPoctEntity;
}
