package com.mdsd.cloud.controller.pc.quality.service;

import com.mdsd.cloud.controller.pc.quality.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-06-29]
 */
public interface QualityService {

    /**
     * 质控管理 - 患者趋势
     *
     * @return List<Map < String, Long>>
     */
    List<Map<String, Long>> qcPatient();

    /**
     * 质控管理 - 出车统计
     *
     * @param qcOutCarPo 入参
     * @return List<Map < String, Long>>
     */
    List<Map<String, Long>> qcOutCar(QcOutCarPo qcOutCarPo);

    /**
     * 质控管理 - 平均反应时间
     *
     * @param qcMeanResponseTimePo 入参
     * @return List<Map < String, Map < String, Float>>>
     */
    List<Map<String, Map<String, Float>>> qcMeanResponseTime(QcMeanResponseTimePo qcMeanResponseTimePo);

    /**
     * 质控管理 - 各时段出车统计
     *
     * @param qcTimeIntervalOutCarPo 入参
     * @return List<Map < String, Long>>
     */
    List<Map<String, Long>> qcTimeIntervalOutCar(QcTimeIntervalOutCarPo qcTimeIntervalOutCarPo);

    /**
     * 质控管理 - 平均出车时长
     *
     * @param qcMeanOutCarTimePo 入参
     * @return List<Map < String, Map < String, Float>>>
     */
    List<Map<String, Map<String, Float>>> qcMeanOutCarTime(QcMeanOutCarTimePo qcMeanOutCarTimePo);

    /**
     * 质控管理 - 病种分类统计
     *
     * @param qcDiseaseEntitiesPo 入参
     * @return List<Map < String, Integer>>
     */
    List<Map<String, Long>> qcDiseaseEntities(QcDiseaseEntitiesPo qcDiseaseEntitiesPo);

    /**
     * 质控管理 - 区域分析
     *
     * @param qcZoneAnalysisPo 入参
     * @return List<Map < String, Long>>
     */
    List<Map<String, Long>> qcZoneAnalysis(QcZoneAnalysisPo qcZoneAnalysisPo);

    /**
     * 质控管理 - 病历完成率
     *
     * @param qcMedicalRecordEfficiencyPo 入参
     * @return List<Map < String, Map < String, Long>>>
     */
    List<Map<String, Map<String, Long>>> qcMedicalRecordEfficiency(QcMedicalRecordEfficiencyPo qcMedicalRecordEfficiencyPo);

    /**
     * 质控管理 - 急救措施统计
     *
     * @param qcEmergencyTreatmentPo 入参
     * @return List<Map < String, Long>>
     */
    List<Map<String, Long>> qcEmergencyTreatment(QcEmergencyTreatmentPo qcEmergencyTreatmentPo);
}
