package com.mdsd.cloud.controller.qualityControl.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.qualityControl.Vo.*;
import com.mdsd.cloud.controller.qualityControl.mapper.TriageStatisticsMapper;
import com.mdsd.cloud.controller.qualityControl.service.TriageStatisticsService;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TriageStatisticsServiceImpl implements TriageStatisticsService {

    private final TriageStatisticsMapper triageStatisticsMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;

    /**
     * 查询特殊患者接诊统计
     */
    @Override
    public List<ReceptionStatisticsCountDetailVo> getReceptionStatistics(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        final List<ReceptionStatisticsCountDetailVo> detailVos = this.triageStatisticsMapper.getReceptionStatistics(receptionStatisticsRequestVo);
        return detailVos;
    }

    /**
     * 查询特殊患者接诊统计明细
     */
    @Override
    public IPage<ReceptionStatisticsDetailVo> queryDetailList(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        final Page<ReceptionStatisticsDetailVo> page = new Page<>(receptionStatisticsRequestVo.getCurrent(), receptionStatisticsRequestVo.getPageSize());
        //获取患者信息
        final IPage<ReceptionStatisticsDetailVo> detailVos = this.triageStatisticsMapper.getDetails(page, receptionStatisticsRequestVo);

        return detailVos;
    }

    /**
     * 特殊患者接诊导出明细
     */
    @Override
    public List<ReceptionStatisticsDetailVo> detailExport(final ReceptionStatisticsRequestVo receptionStatisticsRequestVo) {

        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0001.getValue(), DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0008.getValue(), DictionaryEnum.PIAT_0005.getValue());
        //查询数据
        List<ReceptionStatisticsDetailVo> receptionStatisticsDetailVos = this.triageStatisticsMapper.getDetails(receptionStatisticsRequestVo);
        receptionStatisticsDetailVos = receptionStatisticsDetailVos.stream().map(detailVo -> {
            final ReceptionStatisticsDetailVo vo = new ReceptionStatisticsDetailVo();
            BeanUtils.copyProperties(detailVo, vo);
            vo.setGender(this.matching(vo.getGender(), DictionaryEnum.PIAT_0001.getValue(), dictByCode));
            vo.setResetLevel(this.matching(vo.getResetLevel(), DictionaryEnum.PIAT_0009.getValue(), dictByCode));
            vo.setTurnResult(this.matching(vo.getTurnResult(), DictionaryEnum.PIAT_0008.getValue(), dictByCode));
            vo.setPatientType(this.matching(vo.getPatientType(), DictionaryEnum.PIAT_0005.getValue(), dictByCode));
            return vo;
        }).collect(Collectors.toList());
        return receptionStatisticsDetailVos;
    }

    /**
     * 来诊时间段天
     */
    @Override
    public List<TimePeriodDistributionTimeCountVo> queryTimeCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return this.triageStatisticsMapper.queryTimeCount(timePeriodDistributionRequestVo);
    }

    /**
     * 来诊时间段年
     */
    @Override
    public List<TimePeriodDistributionTimeCountVo> queryYearCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return this.triageStatisticsMapper.queryYearCount(timePeriodDistributionRequestVo);
    }

    /**
     * 来诊时间段月
     */
    @Override
    public List<TimePeriodDistributionTimeCountVo> queryMonthCount(final TimePeriodDistributionRequestVo timePeriodDistributionRequestVo) {

        return this.triageStatisticsMapper.queryMonthCount(timePeriodDistributionRequestVo);
    }

    /**
     * 病人年龄段查询
     */
    @Override
    public List<AgeDistributionCountVo> queryAgeCount(final AgeDistributionRequestVo ageDistributionRequestVo) {
        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0001.getValue());
        List<AgeDistributionCountVo> ageDistributionCountVos = this.triageStatisticsMapper.queryAgeCount(ageDistributionRequestVo);
        ageDistributionCountVos = ageDistributionCountVos.stream().map(detailVo -> {
            final AgeDistributionCountVo vo = new AgeDistributionCountVo();
            BeanUtils.copyProperties(detailVo, vo);
            vo.setGender(this.matching(vo.getGender(), DictionaryEnum.PIAT_0001.getValue(), dictByCode));
            return vo;
        }).collect(Collectors.toList());
        return ageDistributionCountVos;
    }

    /**
     * 来院方式明细查询
     */
    @Override
    public IPage<ToHospitalDistributionDetailVo> queryToHospitalDetail(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {

        final Page<ToHospitalDistributionDetailVo> page = new Page<>(toHospitalDistributionRequestVo.getCurrent(), toHospitalDistributionRequestVo.getPageSize());
        final IPage<ToHospitalDistributionDetailVo> detailVoIPage = this.triageStatisticsMapper.queryToHospitalDetail(page, toHospitalDistributionRequestVo);
        return detailVoIPage;
    }

    /**
     * 查询来院方式统计
     */
    @Override
    public List<ToHospitalDistributionCountVo> queryToHospitalCount(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {

        return this.triageStatisticsMapper.queryToHospitalCount(toHospitalDistributionRequestVo);
    }

    @Override
    public List<ToHospitalDistributionDetailVo> exportToHospital(final ToHospitalDistributionRequestVo toHospitalDistributionRequestVo) {
        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0001.getValue(), DictionaryEnum.PIAT_0008.getValue(), DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0004.getValue());

        List<ToHospitalDistributionDetailVo> detailVos = this.triageStatisticsMapper.queryToHospitalDetail(toHospitalDistributionRequestVo);
        detailVos = detailVos.stream().map(detailVo -> {
            final ToHospitalDistributionDetailVo vo = new ToHospitalDistributionDetailVo();
            BeanUtils.copyProperties(detailVo, vo);
            vo.setGender(this.matching(vo.getGender(), DictionaryEnum.PIAT_0001.getValue(), dictByCode));
            vo.setTurnResult(this.matching(vo.getTurnResult(), DictionaryEnum.PIAT_0008.getValue(), dictByCode));
            vo.setResetLevel(this.matching(vo.getResetLevel(), DictionaryEnum.PIAT_0009.getValue(), dictByCode));
            vo.setArriveType(this.matching(vo.getArriveType(), DictionaryEnum.PIAT_0004.getValue(), dictByCode));
            return vo;
        }).collect(Collectors.toList());
        return detailVos;
    }

    /**
     * 字典匹配
     *
     * @param result 需要修改的字段
     * @param param  字典表中标识
     * @return 返回string
     */
    public String matching(String result, final String param, final Map<String, List<MedDictionaryEntity>> dictByCode) {
        //性别
        final List<MedDictionaryEntity> drDtoList = dictByCode.get(param);
        if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(result)) {

            for (final MedDictionaryEntity drDto : drDtoList) {
                if (result.equals(drDto.getItemcode())) {
                    result = drDto.getItemname();
                }
            }
        }
        return result;
    }
}
