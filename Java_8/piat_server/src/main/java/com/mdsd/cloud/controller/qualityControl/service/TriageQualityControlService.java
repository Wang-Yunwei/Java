package com.mdsd.cloud.controller.qualityControl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.qualityControl.Vo.PatientProportionDetailReportVo;
import com.mdsd.cloud.controller.qualityControl.Vo.PatientProportionReportRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.TriageWrongDetailVo;
import com.mdsd.cloud.controller.qualityControl.Vo.TriageWrongRequestVo;

import java.util.List;
import java.util.Map;

/**
 * @author huxiaoneng
 * @version 1.0
 */
public interface TriageQualityControlService {

    Map<String, Object> queryPatientProportionReport(PatientProportionReportRequestVo patientProportionReportRequestVo);

    IPage<PatientProportionDetailReportVo> queryPatientProportionDetailReport(PatientProportionReportRequestVo patientProportionReportRequestVo);

    Map<String, Object> queryMonthProportionReport(PatientProportionReportRequestVo patientProportionReportRequestVo, String status);

    List<PatientProportionDetailReportVo> patientDetailExport(PatientProportionReportRequestVo patientProportionReportRequestVo);

    Map<String, Object> queryTriageWrongRatio(TriageWrongRequestVo triageWrongRequestVo);

    IPage<TriageWrongDetailVo> queryTriageWrongRatioDetail(TriageWrongRequestVo triageWrongRequestVo);

    Map<String, Object> queryTriageWrongMonthRatio(TriageWrongRequestVo triageWrongRequestVo, String status);

    List<TriageWrongDetailVo> queryTriageWrongDetailExport(TriageWrongRequestVo triageWrongRequestVo);
}
