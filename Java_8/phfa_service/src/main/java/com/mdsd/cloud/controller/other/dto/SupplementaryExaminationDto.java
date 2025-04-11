package com.mdsd.cloud.controller.other.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mdsd.cloud.entity.MedPhepPoctEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangYunwei [2022-01-06]
 */
@Getter
@Setter
@ApiModel(value = "辅助检查")
public class SupplementaryExaminationDto {

    @ApiModelProperty(value = "心电图")
    private List<String> ecg;

    @ApiModelProperty(value = "血糖")
    private Float bloodglucose;

    @ApiModelProperty(value = "POCT")
    private MedPhepPoctEntity medPhepPoctEntity;

    @ApiModelProperty(value = "TI评分")
    private String tiScore;

//    @ApiModelProperty(value = "评分时间")
//    private LocalDateTime tiTime;
//
//    @ApiModelProperty(value = "TI详情")
//    private List<TIDetailDto> tiDetails;

    @ApiModelProperty(value = "GCS评分")
    private String gcsScore;

    @ApiModelProperty(value = "胸痛评估")
    private String chestPainAssessment;

    @ApiModelProperty(value = "Killip分级")
    private String killip;

    @ApiModelProperty(value = "Fast评估")
    private String fastAssessment;

    @ApiModelProperty(value = "其它检查")
    private String otherCheck;

    @ApiModelProperty(value = "现场照片")
    private List<String> sitePhotos;

    @ApiModelProperty(value = "现场视频")
    private List<String> liveVideo;
}
