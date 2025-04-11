package com.mdsd.cloud.controller.other.dto;

import com.mdsd.cloud.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-07-11]
 */
@Getter
@Setter
public class FirstAidDetailsRo {

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
     * 心电图
     */
    @ApiModelProperty(value = "心电图")
    private List<String> ecgList;

    /**
     * 体格检查
     */
    @ApiModelProperty(value = "体格检查")
    private List<MedPhepGeneralInspectionsEntity> generalList;

    /**
     * 急救措施
     */
    @ApiModelProperty(value = "急救措施")
    private List<MedPhepMeasuresEntity> measuresList;

    /**
     * 用药
     */
    @ApiModelProperty(value = "用药")
    private List<UsingDrugsDto> usingDrugsList;

    /**
     * 交接单
     */
    @ApiModelProperty(value = "交接单")
    private MedPhepShiftRecordEntity medPhepShiftRecordEntity;

    /**
     * POCT
     */
    @ApiModelProperty(value = "POCT")
    private MedPhepPoctEntity medPhepPoctEntity;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private List<MedPhepEstimateEntity> estimateList;
}
