package com.mdsd.cloud.controller.auxiliary.dto;

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
@ApiModel(value = "辅助检查 - 列表_返参")
public class SupListResultDto {

    @ApiModelProperty(value = "心电图URL列表")
    private List<Map<String, String>> electrocardiogramUrlList;

    @ApiModelProperty(value = "POCT附件URL列表")
    private List<Map<String, String>> poctAttachmentUrlList;

    @ApiModelProperty(value = "现场照片URL列表")
    private List<Map<String, String>> scenePhotoUrlList;

    @ApiModelProperty(value = "现场视频URL列表")
    private List<Map<String, String>> sceneVideoUrlList;
}
