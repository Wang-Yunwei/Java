package com.mdsd.cloud.controller.auxiliary.dto;

import com.mdsd.cloud.entity.MedPhepPoctEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2021-11-15]
 */
@Getter
@Setter
@ApiModel(value = "辅助检查 - 详情_返参")
public class SupDetailsResultDto {

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
     * POCT对象
     */
    @ApiModelProperty(value = "POCT")
    private MedPhepPoctEntity medPhepPoctEntity;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private List<EstimateDto> estimateDto;

    /**
     * 心电图URL列表
     */
    @ApiModelProperty(value = "心电图URL列表")
    private List<Map<String, String>> electrocardiogramUrlList;

    /**
     * POCT附件URL列表
     */
    @ApiModelProperty(value = "POCT附件URL列表")
    private List<Map<String, String>> poctAttachmentUrlList;

    /**
     * 现场照片URL列表
     */
    @ApiModelProperty(value = "现场照片URL列表")
    private List<Map<String, String>> scenePhotoUrlList;

    /**
     * 现场照片URL列表
     */
    @ApiModelProperty(value = "现场照片URL列表")
    private List<Map<String, String>> sceneVideoUrlList;

}
