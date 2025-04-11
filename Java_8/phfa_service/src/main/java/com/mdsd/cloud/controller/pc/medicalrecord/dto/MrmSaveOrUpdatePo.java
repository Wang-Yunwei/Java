package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import com.mdsd.cloud.controller.auxiliary.dto.EstimateDto;
import com.mdsd.cloud.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-05-16]
 */
@Getter
@Setter
@ApiModel(value = "MrmSaveOrUpdatePo对象", description = "病历管理 - 保存or更新_入参")
public class MrmSaveOrUpdatePo {

    /**
     * 患者信息
     */
    @ApiModelProperty(value = "患者信息")
    private MedPhepPatientsEntity medPhepPatientsEntity;

    /**
     * 任务信息
     */
    @ApiModelProperty(value = "任务信息")
    private MedPhepMissionsEntity medPhepMissionsEntity;

    /**
     * 病历信息
     */
    @ApiModelProperty(value = "病历信息")
    private MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity;

    /**
     * 体格检查列表
     */
    @ApiModelProperty(value = "体格检查列表")
    List<MedPhepGeneralInspectionsEntity> medPhepGeneralInspectionsEntityList;

    /**
     * POCT
     */
    @ApiModelProperty(value = "POCT")
    private MedPhepPoctEntity medPhepPoctEntity;

    /**
     * 知情同意书
     */
    @ApiModelProperty(value = "知情同意书")
    private MedPhepConsentsEntity medPhepConsentsEntity;

    /**
     * 新冠流调
     */
    @ApiModelProperty(value = "新冠流调")
    private MedPhepEpidemiologicalEntity medPhepEpidemiologicalEntity;

    /**
     * 交接单
     */
    @ApiModelProperty(value = "交接单")
    private MedPhepShiftRecordEntity medPhepShiftRecordEntity;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private EstimateDto estimateDto;
}
