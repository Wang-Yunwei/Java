package com.mdsd.cloud.controller.pc.quality;

import com.mdsd.cloud.controller.pc.quality.dto.*;
import com.mdsd.cloud.controller.pc.quality.service.QualityService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 质控管理
 *
 * @author WangYunwei [2022-06-29]
 */
@RestController
@RequestMapping("/pc/QualityController/v1")
@Api(value = "WEB - 质控管理", tags = "PC - QualityControl", produces = MediaType.APPLICATION_JSON_VALUE)
public class QualityController {

    QualityService qualityService;

    public QualityController(final QualityService qualityService) {

        this.qualityService = qualityService;
    }

    /**
     * 质控管理 - 患者趋势
     */
    @ApiOperation(value = "质控管理 - 患者趋势")
    @GetMapping(name = "患者趋势", value = "/qcPatient")
    public ResponseDto<List<Map<String, Long>>> qcPatient() {

        return ResponseUtil.wrapSuccess(this.qualityService.qcPatient());
    }

    /**
     * 质控管理 - 出车统计
     */
    @ApiOperation(value = "质控管理 - 出车统计")
    @PostMapping(name = "出车统计", value = "/qcOutCar")
    public ResponseDto<List<Map<String, Long>>> qcOutCar(@RequestBody final QcOutCarPo qcOutCarPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcOutCar(qcOutCarPo));
    }

    /**
     * 质控管理 - 平均反应时间
     */
    @ApiOperation(value = "质控管理 - 平均反应时间")
    @PostMapping(name = "平均反应时间", value = "/qcMeanResponseTime")
    public ResponseDto<List<Map<String, Map<String, Float>>>> qcMeanResponseTime(@RequestBody final QcMeanResponseTimePo qcMeanResponseTimePo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcMeanResponseTime(qcMeanResponseTimePo));
    }

    /**
     * 质控管理 - 各时段出车统计
     */
    @ApiOperation(value = "质控管理 - 各时段出车统计")
    @PostMapping(name = "各时段出车统计", value = "/qcTimeIntervalOutCar")
    public ResponseDto<List<Map<String, Long>>> qcTimeIntervalOutCar(@RequestBody final QcTimeIntervalOutCarPo qcTimeIntervalOutCarPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcTimeIntervalOutCar(qcTimeIntervalOutCarPo));
    }

    /**
     * 质控管理 - 平均出车时长
     */
    @ApiOperation(value = "质控管理 - 平均出车时长")
    @PostMapping(name = "平均出车时长", value = "/qcMeanOutCarTime")
    public ResponseDto<List<Map<String, Map<String, Float>>>> qcMeanOutCarTime(@RequestBody final QcMeanOutCarTimePo qcMeanOutCarTimePo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcMeanOutCarTime(qcMeanOutCarTimePo));
    }

    /**
     * 质控管理 - 病种分类统计
     */
    @ApiOperation(value = "质控管理 - 病种分类统计")
    @PostMapping(name = "病种分类统计", value = "/qcDiseaseEntities")
    public ResponseDto<List<Map<String, Long>>> qcDiseaseEntities(@RequestBody final QcDiseaseEntitiesPo qcDiseaseEntitiesPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcDiseaseEntities(qcDiseaseEntitiesPo));
    }

    /**
     * 质控管理 - 区域分析
     */
    @ApiOperation(value = "质控管理 - 区域分析")
    @PostMapping(name = "区域分析", value = "/qcZoneAnalysis")
    public ResponseDto<List<Map<String, Long>>> qcZoneAnalysis(@RequestBody final QcZoneAnalysisPo qcZoneAnalysisPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcZoneAnalysis(qcZoneAnalysisPo));
    }

    /**
     * 质控管理 - 病历完成率
     */
    @ApiOperation(value = "质控管理 - 病历完成率")
    @PostMapping(name = "病历完成率", value = "/qcMedicalRecordEfficiency")
    public ResponseDto<List<Map<String, Map<String, Long>>>> qcMedicalRecordEfficiency(@RequestBody final QcMedicalRecordEfficiencyPo qcMedicalRecordEfficiencyPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcMedicalRecordEfficiency(qcMedicalRecordEfficiencyPo));
    }

    /**
     * 质控管理 - 急救措施统计
     */
    @ApiOperation(value = "质控管理 - 急救措施统计")
    @PostMapping(name = "急救措施统计", value = "/qcEmergencyTreatment")
    public ResponseDto<List<Map<String, Long>>> qcEmergencyTreatment(@RequestBody final QcEmergencyTreatmentPo qcEmergencyTreatmentPo) {

        return ResponseUtil.wrapSuccess(this.qualityService.qcEmergencyTreatment(qcEmergencyTreatmentPo));
    }
}
