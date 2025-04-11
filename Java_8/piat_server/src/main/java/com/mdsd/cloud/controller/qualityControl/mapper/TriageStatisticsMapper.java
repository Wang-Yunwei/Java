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
public interface TriageStatisticsMapper {

    List<ReceptionStatisticsCountDetailVo> getReceptionStatistics(@Param("dto") ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    IPage<ReceptionStatisticsDetailVo> getDetails(Page<ReceptionStatisticsDetailVo> page, @Param("dto") ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    List<ReceptionStatisticsDetailVo> getDetails(@Param("dto") ReceptionStatisticsRequestVo receptionStatisticsRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryTimeCount(@Param("dto") TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryYearCount(@Param("dto") TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<TimePeriodDistributionTimeCountVo> queryMonthCount(@Param("dto") TimePeriodDistributionRequestVo timePeriodDistributionRequestVo);

    List<AgeDistributionCountVo> queryAgeCount(@Param("dto") AgeDistributionRequestVo ageDistributionRequestVo);

    IPage<ToHospitalDistributionDetailVo> queryToHospitalDetail(Page<ToHospitalDistributionDetailVo> page, @Param("dto") ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);

    List<ToHospitalDistributionDetailVo> queryToHospitalDetail(@Param("dto") ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);

    List<ToHospitalDistributionCountVo> queryToHospitalCount(@Param("dto") ToHospitalDistributionRequestVo toHospitalDistributionRequestVo);
}
