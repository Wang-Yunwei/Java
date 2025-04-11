package com.mdsd.cloud.controller.other.dto;

import com.mdsd.cloud.entity.MedPhepMeasuresEntity;
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
@ApiModel(value = "OTHER - 其它专科查询急救病历接口_返参")
public class GetFirstAidMedicalRecordRso {

    /**
     * 患者基本信息
     */
    @ApiModelProperty(value = "患者基本信息")
    private PatientBaseInfoDto patientBaseInfoDto;

    /**
     * 任务信息
     */
    @ApiModelProperty(value = "任务信息")
    private TaskInfoDto taskInfoDto;

    /**
     * 病历信息
     */
    @ApiModelProperty(value = "病历信息")
    private MedicalRecordInfoDto medicalRecordInfoDto;

    /**
     * 体格检查
     */
    @ApiModelProperty(value = "体格检查")
    private List<PhysicalExaminationDto> physicalExaminationDtoList;

    /**
     * 辅助检查
     */
    @ApiModelProperty(value = "辅助检查")
    private SupplementaryExaminationDto supplementaryExaminationDto;

    /**
     * 诊断处理
     */
    @ApiModelProperty(value = "诊断处理")
    private DiagnosticProcessingDto diagnosticProcessingDto;

    /**
     * 知情同意书
     */
    @ApiModelProperty(value = "知情同意书")
    private String consents;

    /**
     * 新冠流调表
     */
    @ApiModelProperty(value = "新冠流调表")
    private String epidemiological;

    /**
     * 急救措施列表
     */
    @ApiModelProperty(value = "急救措施列表")
    private List<MedPhepMeasuresEntity> medPhepMeasuresEntityList;
}
