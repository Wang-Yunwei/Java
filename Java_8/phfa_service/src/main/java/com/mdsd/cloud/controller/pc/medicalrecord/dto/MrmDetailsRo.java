package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import com.mdsd.cloud.controller.auxiliary.dto.EstimateDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-05-19]
 */
@Getter
@Setter
@ApiModel(value = "MrmDetailsRo对象", description = "病历管理 - 详情_返参")
public class MrmDetailsRo extends MrmSaveOrUpdatePo {

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private List<EstimateDto> estimateDtoList;

    /**
     * 心电图URL列表
     */
    @ApiModelProperty(value = "心电图URL列表")
    private List<Map<String, String>> ecgUrlList;

    /**
     * POCT附件URL列表
     */
    @ApiModelProperty(value = "POCT附件URL列表")
    private List<Map<String, String>> poctUrlList;

    /**
     * 现场照片URL列表
     */
    @ApiModelProperty(value = "现场照片URL列表")
    private List<Map<String, String>> scenePhotoUrlList;

    /**
     * 现场视频URL列表
     */
    @ApiModelProperty(value = "现场视频URL列表")
    private List<Map<String, String>> sceneVideoUrlList;

}
