package com.mdsd.cloud.controller.qualityControl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.qualityControl.Vo.*;

import java.util.List;

/**
 * @author huxiaoneng
 * @version 1.0
 */
public interface TriageStatisticsService {

    List<ReceptionStatisticsCountDetailVo> getReceptionStatistics(ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    IPage<ReceptionStatisticsDetailVo> queryDetailList(ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    List<ReceptionStatisticsDetailVo> detailExport(ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryTimeCount(TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryYearCount(TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryMonthCount(TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<AgeDistributionCountVo> queryAgeCount(AgeDistributionRequestVo ageDistributionRequestVo);

    IPage<ToHospitalDistributionDetailVo> queryToHospitalDetail(ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);

    List<ToHospitalDistributionCountVo> queryToHospitalCount(ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);

    List<ToHospitalDistributionDetailVo> exportToHospital(ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);
}
