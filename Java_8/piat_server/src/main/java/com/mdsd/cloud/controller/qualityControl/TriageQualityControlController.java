package com.mdsd.cloud.controller.qualityControl;

import com.mdsd.cloud.controller.qualityControl.Vo.PatientProportionReportRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.TriageWrongRequestVo;
import com.mdsd.cloud.controller.qualityControl.service.TriageQualityControlService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/triageQualityControl")
@RequiredArgsConstructor
@Api(value = "TriageQualityControlController", tags = "分诊质控")
public class TriageQualityControlController {

    private final TriageQualityControlService triageQualityControlService;

    @ApiOperation("查询急诊各级患者比例")
    @GetMapping("queryPatientProportionReport")
    public ResponseDto<?> queryPatientProportionReport(final PatientProportionReportRequestVo patientProportionReportRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryPatientProportionReport(patientProportionReportRequestVo));
    }

    @ApiOperation("查询急诊各级患者比例详情")
    @GetMapping("queryPatientProportionDetailReport")
    public ResponseDto<?> queryPatientProportionDetailReport(final PatientProportionReportRequestVo patientProportionReportRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryPatientProportionDetailReport(patientProportionReportRequestVo));
    }

    @ApiOperation("查询急诊各级患者比例详情月度")
    @GetMapping("queryMonthProportionReport/{status}")
    public ResponseDto<?> queryMonthProportionReport(final PatientProportionReportRequestVo patientProportionReportRequestVo, @PathVariable final String status) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryMonthProportionReport(patientProportionReportRequestVo, status));
    }

    @ApiOperation(value = "导出详情")
    @PostMapping("patientDetailExport")
    public ResponseDto<?> patientDetailExport(@RequestBody final PatientProportionReportRequestVo patientProportionReportRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.patientDetailExport(patientProportionReportRequestVo));
    }

    @ApiOperation(value = "分诊正确率查询")
    @GetMapping("queryTriageWrongRatio")
    public ResponseDto<?> queryTriageWrongRatio(final TriageWrongRequestVo triageWrongRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryTriageWrongRatio(triageWrongRequestVo));
    }

    @ApiOperation(value = "分诊正确率详情查询")
    @GetMapping("queryTriageWrongRatioDetail")
    public ResponseDto<?> queryTriageWrongRatioDetail(final TriageWrongRequestVo triageWrongRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryTriageWrongRatioDetail(triageWrongRequestVo));
    }

    @ApiOperation(value = "分诊正确率月度查询")
    @GetMapping("queryTriageWrongMonthRatio/{status}")
    public ResponseDto<?> queryTriageWrongMonthRatio(final TriageWrongRequestVo triageWrongRequestVo, @PathVariable final String status) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryTriageWrongMonthRatio(triageWrongRequestVo, status));
    }

    @ApiOperation(value = "分诊正确率详情导出查询")
    @GetMapping("queryTriageWrongDetailExport")
    public ResponseDto<?> queryTriageWrongDetailExport(final TriageWrongRequestVo triageWrongRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageQualityControlService.queryTriageWrongDetailExport(triageWrongRequestVo));
    }

}
