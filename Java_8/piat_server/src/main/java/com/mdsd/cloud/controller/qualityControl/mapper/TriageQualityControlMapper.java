package com.mdsd.cloud.controller.qualityControl.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.qualityControl.Vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Mapper
public interface TriageQualityControlMapper {

    List<PatientProportionReportVo> queryPatientProportionReport(@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    IPage<PatientProportionDetailReportVo> queryPatientProportionDetailReport(Page<PatientProportionDetailReportVo> page, @Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<PatientProportionDetailReportVo> queryPatientProportionDetailReport (@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<PatientMonthProportionReportVo> queryMonthProportionReport(@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<PatientMonthProportionReportVo> queryYearProportionReport(@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<PatientMonthProportionReportChartsVo> queryMonthProportionReportCharts(@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<PatientMonthProportionReportChartsVo> queryYearProportionReportCharts(@Param("dto") PatientProportionReportRequestVo patientProportionReportRequestVo);

    List<TriageWrongRatioVo> queryTriageWrongRatio(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    IPage<TriageWrongDetailVo> queryTriageWrongRatioDetail(Page<TriageWrongDetailVo> page,@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    int queryTriageWrongRatioCount(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    int queryPatientCount(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongMonthRatioVo> queryTriageWrongMonthRatio(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongRatioChartsVo> queryTriageWrongMonthCharts(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongRatioChartsVo> queryTriageWrongMonthChartsCount(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongMonthRatioVo> queryTriageWrongYearRatio(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongRatioChartsVo> queryTriageWrongYearCharts(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongRatioChartsVo> queryTriageWrongYearChartsCount(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

    List<TriageWrongDetailVo> queryTriageWrongRatioDetail(@Param("dto") TriageWrongRequestVo triageWrongRequestVo);

}
