package com.mdsd.cloud.controller.qualityControl;

import com.mdsd.cloud.controller.qualityControl.Vo.AgeDistributionRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.ReceptionStatisticsRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.TimePeriodDistributionRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.ToHospitalDistributionRequestVo;
import com.mdsd.cloud.controller.qualityControl.service.TriageStatisticsService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/triageStatistics")
@RequiredArgsConstructor
@Api(value = "TriageStatisticsController", tags = "分诊统计")
public class TriageStatisticsController {

    private final TriageStatisticsService triageStatisticsService;

    @ApiOperation(value = "查询统计count特殊患者接诊统计")
    @GetMapping("getReceptionStatistics")
    public ResponseDto<?> getReceptionStatistics(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.getReceptionStatistics(receptionStatisticsRequestVo));
    }

    @ApiOperation(value = "查询详情特殊患者接诊统计")
    @GetMapping("queryDetailList")
    public ResponseDto<?> queryDetailList(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryDetailList(receptionStatisticsRequestVo));
    }

    @ApiOperation(value = "导出详情特殊患者接诊统计")
    @PostMapping("detailExport")
    public ResponseDto<?> detailExport(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.detailExport(receptionStatisticsRequestVo));
    }

    @ApiOperation(value = "查询统计天count来诊时间段分布")
    @GetMapping("queryTimeCount")
    public ResponseDto<?> queryTimeCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryTimeCount(timePeriodDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计月count来诊时间段分布")
    @GetMapping("queryMonthCount")
    public ResponseDto<?> queryMonthCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryMonthCount(timePeriodDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计年count来诊时间段分布")
    @GetMapping("queryYearCount")
    public ResponseDto<?> queryYearCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryYearCount(timePeriodDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计count病人年龄段分布")
    @GetMapping("queryAgeCount")
    public ResponseDto<?> queryAgeCount(final AgeDistributionRequestVo ageDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryAgeCount(ageDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计明细来院方式分布")
    @GetMapping("queryToHospitalDetail")
    public ResponseDto<?> queryToHospitalDetail(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryToHospitalDetail(toHospitalDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计count来院方式分布")
    @GetMapping("queryToHospitalCount")
    public ResponseDto<?> queryToHospitalCount(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.queryToHospitalCount(toHospitalDistributionRequestVo));
    }

    @ApiOperation(value = "查询统计count来院方式分布")
    @GetMapping("exportToHospital")
    public ResponseDto<?> exportToHospital(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {

        return ResponseUtil.wrapSuccess(this.triageStatisticsService.exportToHospital(toHospitalDistributionRequestVo));
    }

}
