package com.mdsd.cloud.controller.pc.quality.mapper;

import com.mdsd.cloud.controller.pc.quality.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangYunwei [2022-06-29]
 */
@Mapper
public interface QualityMapper {

    /**
     * 质控管理 - 患者趋势
     *
     * @return List<QcPatientRo>
     */
    List<QcPatientRo> qcPatient();

    /**
     * 质控管理 - 出车统计
     *
     * @param qcOutCarPo
     * @return List<QcOutCarRo>
     */
    List<QcOutCarRo> qcOutCar(QcOutCarPo qcOutCarPo);

    /**
     * 质控管理 - 平均反应时间
     *
     * @param qcMeanResponseTimePo
     * @return List<QcMeanResponseTimeRo>
     */
    List<QcMeanResponseTimeRo> qcMeanResponseTime(QcMeanResponseTimePo qcMeanResponseTimePo);

    /**
     * 质控管理 - 各时段出车统计
     *
     * @param qcTimeIntervalOutCarPo
     * @return List<QcTimeIntervalOutCarRo>
     */
    List<QcTimeIntervalOutCarRo> qcTimeIntervalOutCar(QcTimeIntervalOutCarPo qcTimeIntervalOutCarPo);

    /**
     * 质控管理 - 平均出车时长
     *
     * @param qcMeanOutCarTimePo
     * @return List<QcMeanOutCarTimeRo>
     */
    List<QcMeanOutCarTimeRo> qcMeanOutCarTime(QcMeanOutCarTimePo qcMeanOutCarTimePo);

    /**
     * 质控管理 - 病种分类统计
     *
     * @param qcDiseaseEntitiesPo 入参
     * @return List<QcDiseaseEntitiesRo>
     */
    List<QcDiseaseEntitiesRo> qcDiseaseEntities(QcDiseaseEntitiesPo qcDiseaseEntitiesPo);

    /**
     * 质控管理 - 区域分析
     *
     * @param qcZoneAnalysisPo
     * @return List<QcZoneAnalysisRo>
     */
    List<QcZoneAnalysisRo> qcZoneAnalysis(QcZoneAnalysisPo qcZoneAnalysisPo);

    /**
     * 质控管理 - 病历完成率
     *
     * @param qcMedicalRecordEfficiencyPo
     * @return List<QcMedicalRecordEfficiencyRo>
     */
    List<QcMedicalRecordEfficiencyRo> qcMedicalRecordEfficiency(final QcMedicalRecordEfficiencyPo qcMedicalRecordEfficiencyPo);

    /**
     * 质控管理 - 急救措施统计
     *
     * @param qcEmergencyTreatmentPo
     * @return List<QcEmergencyTreatmentRo>
     */
    List<QcEmergencyTreatmentRo> qcEmergencyTreatment(final QcEmergencyTreatmentPo qcEmergencyTreatmentPo);
}
