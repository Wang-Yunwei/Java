package com.mdsd.cloud.controller.pc.quality.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.pc.quality.dto.*;
import com.mdsd.cloud.controller.pc.quality.mapper.QualityMapper;
import com.mdsd.cloud.controller.pc.quality.service.QualityService;
import com.mdsd.cloud.entity.MedPhepStationEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.utils.MyLocalDateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-06-29]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class QualityServiceImpl implements QualityService {

    @NonNull
    QualityMapper qualityMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;//数据字典

    /**
     * 质控管理 - 患者趋势
     */
    @Override
    public List<Map<String, Long>> qcPatient() {

        final List<Map<String, Long>> result = Lists.newArrayList();
        //查询数据库
        final List<QcPatientRo> qcPatientRos = this.qualityMapper.qcPatient();
        Optional.ofNullable(qcPatientRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //根据年获取日期
            final List<String> dateByYear = MyLocalDateUtil.getDateByYear(LocalDate.now().getYear());
            if (!CollectionUtils.isEmpty(dateByYear)) {
                dateByYear.forEach(str -> {
                    final Map<String, Long> map = Maps.newHashMap();
                    map.put(str, qcPatientRos.stream().filter(dto -> str.equals(dto.getCreateTime())).count());
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 出车统计
     */
    @Override
    public List<Map<String, Long>> qcOutCar(final QcOutCarPo qcOutCarPo) {

        final List<Map<String, Long>> result = Lists.newArrayList();
        //查询数据库数据
        final List<QcOutCarRo> qcOutCarRos = this.qualityMapper.qcOutCar(qcOutCarPo);
        Optional.ofNullable(qcOutCarRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //获取两个月份之前的字符串日期
            final List<String> monthOfDate = MyLocalDateUtil.getMonthOfDate(qcOutCarPo.getStartTime().toLocalDate(), qcOutCarPo.getEndTime().toLocalDate());
            if (!CollectionUtils.isEmpty(monthOfDate)) {
                monthOfDate.forEach(str -> {
                    final Map<String, Long> map = Maps.newHashMap();
                    map.put(str, list.stream().filter(dto -> str.equals(dto.getCallTime())).count());
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 平均反应时间
     */
    @Override
    public List<Map<String, Map<String, Float>>> qcMeanResponseTime(final QcMeanResponseTimePo qcMeanResponseTimePo) {

        final List<Map<String, Map<String, Float>>> result = Lists.newArrayList();
        //查询数据库
        final List<QcMeanResponseTimeRo> qcMeanResponseTimeRos = this.qualityMapper.qcMeanResponseTime(qcMeanResponseTimePo);
        Optional.ofNullable(qcMeanResponseTimeRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询站点信息
            final QueryWrapper<MedPhepStationEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("ID", "NAME").eq("DELETE_FLAG", 0);
            if (null != qcMeanResponseTimePo.getHospitalId()) {
                queryWrapper.eq("HOSPITAL_ID", qcMeanResponseTimePo.getHospitalId());
            } else if (null != qcMeanResponseTimePo.getStationId()) {
                queryWrapper.eq("ID", qcMeanResponseTimePo.getStationId());
            }
            final List<MedPhepStationEntity> medPhepStationEntities = new MedPhepStationEntity().selectList(queryWrapper);
            //获取两个月份之前的字符串日期
            final List<String> monthOfDate = MyLocalDateUtil.getMonthOfDate(qcMeanResponseTimePo.getStartTime().toLocalDate(), qcMeanResponseTimePo.getEndTime().toLocalDate());
            if (!CollectionUtils.isEmpty(monthOfDate) && !CollectionUtils.isEmpty(medPhepStationEntities)) {
                monthOfDate.forEach(str -> {
                    final Map<String, Map<String, Float>> map = Maps.newHashMap();
                    final Map<String, Float> stationMap = Maps.newHashMap();
                    if (!CollectionUtils.isEmpty(medPhepStationEntities)) {
                        for (final MedPhepStationEntity ent : medPhepStationEntities) {
                            //过滤出当前月份的站点数据
                            final List<QcMeanResponseTimeRo> collect = list.stream().filter(ros -> null != ros.getCallTimeStr() && str.equals(String.format("%s-01", ros.getCallTimeStr())) && ent.getId().equals(ros.getDispatchVehicleSite())).collect(Collectors.toList());
                            final List<Integer> timeDifference = Lists.newArrayList();
                            if (!CollectionUtils.isEmpty(collect)) {
                                collect.forEach(dto -> {
                                    final int stringIntegerMap = MyLocalDateUtil.timeDifference(dto.getCallTime(), dto.getDispatchVehicleTime());
                                    timeDifference.add(stringIntegerMap);
                                });
                            }
                            if (!CollectionUtils.isEmpty(timeDifference)) {
                                final Float value = Float.valueOf(timeDifference.stream().reduce(Integer::sum).orElse(0) / timeDifference.size());
                                stationMap.put(ent.getName(), value / 60);
                            } else {
                                stationMap.put(ent.getName(), 0F);
                            }
                        }
                    }
                    map.put(str, stationMap);
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 各时段出车统计
     */
    @Override
    public List<Map<String, Long>> qcTimeIntervalOutCar(final QcTimeIntervalOutCarPo qcTimeIntervalOutCarPo) {

        Assert.notNull(qcTimeIntervalOutCarPo.getDimensionality(), "维度参数为NULL");
        final List<Map<String, Long>> result = Lists.newArrayList();
        //处理出车时间
        final Map<String, LocalDateTime> todayStartAndEnd = MyLocalDateUtil.getTodayStartAndEnd(qcTimeIntervalOutCarPo.getOutCarTime());
        qcTimeIntervalOutCarPo.setStartTime(todayStartAndEnd.get("todayStart"));
        qcTimeIntervalOutCarPo.setEndTime(todayStartAndEnd.get("todayEnd"));
        //查询数据库数据
        final List<QcTimeIntervalOutCarRo> qcTimeIntervalOutCarRos = this.qualityMapper.qcTimeIntervalOutCar(qcTimeIntervalOutCarPo);
        Optional.ofNullable(qcTimeIntervalOutCarRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //判断统计维度/小时
            for (int i = 0; i < 24; i += qcTimeIntervalOutCarPo.getDimensionality()) {
                final Integer startHour = i;
                final Integer endHour = i + qcTimeIntervalOutCarPo.getDimensionality();
                final Map<String, Long> map = Maps.newHashMap();
                map.put(String.format("%sH-%sH", startHour, endHour), list.stream().filter(dto -> null != dto.getOutCatTimeStr() && startHour <= dto.getOutCatTimeStr() && dto.getOutCatTimeStr() < endHour).count());
                result.add(map);
            }
        });
        return result;
    }

    /**
     * 质控管理 - 平均出车时长
     */
    @Override
    public List<Map<String, Map<String, Float>>> qcMeanOutCarTime(final QcMeanOutCarTimePo qcMeanOutCarTimePo) {

        final List<Map<String, Map<String, Float>>> result = Lists.newArrayList();
        //查询数据库
        final List<QcMeanOutCarTimeRo> qcMeanOutCarTimeRos = this.qualityMapper.qcMeanOutCarTime(qcMeanOutCarTimePo);
        Optional.ofNullable(qcMeanOutCarTimeRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询出车分站
            final QueryWrapper<MedPhepStationEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("ID", "NAME").eq("DELETE_FLAG", 0);
            if (null != qcMeanOutCarTimePo.getHospitalId()) {
                queryWrapper.eq("HOSPITAL_ID", qcMeanOutCarTimePo.getHospitalId());
            } else if (null != qcMeanOutCarTimePo.getStationId()) {
                queryWrapper.eq("ID", qcMeanOutCarTimePo.getStationId());
            }
            final List<MedPhepStationEntity> medPhepStationEntities = new MedPhepStationEntity().selectList(queryWrapper);
            //获取两个月份之前的字符串日期
            final List<String> monthOfDate = MyLocalDateUtil.getMonthOfDate(qcMeanOutCarTimePo.getStartTime().toLocalDate(), qcMeanOutCarTimePo.getEndTime().toLocalDate());
            if (!CollectionUtils.isEmpty(monthOfDate)) {
                monthOfDate.forEach(str -> {
                    final Map<String, Map<String, Float>> map = Maps.newHashMap();
                    final Map<String, Float> stationMap = Maps.newHashMap();
                    //查询站点信息
                    if (!CollectionUtils.isEmpty(medPhepStationEntities)) {
                        for (final MedPhepStationEntity ent : medPhepStationEntities) {
                            //过滤出当前月份的站点数据
                            final List<QcMeanOutCarTimeRo> collect = list.stream().filter(ros -> null != ros.getOutCatTimeStr() && str.equals(String.format("%s-01", ros.getOutCatTimeStr())) && ent.getId().equals(ros.getDispatchVehicleSite())).collect(Collectors.toList());
                            final List<Integer> timeDifference = Lists.newArrayList();
                            if (!CollectionUtils.isEmpty(collect)) {
                                collect.forEach(dto -> {
                                    final int stringIntegerMap = MyLocalDateUtil.timeDifference(dto.getDispatchVehicleTime(), dto.getArrivedHospitalTime());
                                    timeDifference.add(stringIntegerMap);
                                });
                            }
                            if (!CollectionUtils.isEmpty(timeDifference)) {
                                final Float value = Float.valueOf(timeDifference.stream().reduce(Integer::sum).orElse(0) / timeDifference.size());
                                stationMap.put(ent.getName(), value / 60);
                            } else {
                                stationMap.put(ent.getName(), 0F);
                            }
                        }
                    }
                    map.put(str, stationMap);
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 病种分类统计
     */
    @Override
    public List<Map<String, Long>> qcDiseaseEntities(final QcDiseaseEntitiesPo qcDiseaseEntitiesPo) {

        final List<Map<String, Long>> result = Lists.newArrayList();
        //查询数据库
        final List<QcDiseaseEntitiesRo> qcDiseaseEntitiesRos = this.qualityMapper.qcDiseaseEntities(qcDiseaseEntitiesPo);
        Optional.ofNullable(qcDiseaseEntitiesRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //查数据字典获取疾病类别
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0011);
            if (!CollectionUtils.isEmpty(dictByCode)) {
                final List<MedDictionaryEntity> PHFA_0011 = dictByCode.get(DictionaryEnum.PHFA_0011.getValue());
                PHFA_0011.forEach(ent -> {
                    final Map<String, Long> map = Maps.newHashMap();
                    map.put(ent.getItemname(), qcDiseaseEntitiesRos.stream().filter(ros -> ent.getItemcode().equals(ros.getDiseaseCategory())).count());
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 区域分析
     */
    @Override
    public List<Map<String, Long>> qcZoneAnalysis(final QcZoneAnalysisPo qcZoneAnalysisPo) {

        return null;
    }

    /**
     * 质控管理 - 病历完成率
     */
    @Override
    public List<Map<String, Map<String, Long>>> qcMedicalRecordEfficiency(final QcMedicalRecordEfficiencyPo qcMedicalRecordEfficiencyPo) {

        final List<Map<String, Map<String, Long>>> result = Lists.newArrayList();
        final List<QcMedicalRecordEfficiencyRo> qcMedicalRecordEfficiencyRos = this.qualityMapper.qcMedicalRecordEfficiency(qcMedicalRecordEfficiencyPo);
        Optional.ofNullable(qcMedicalRecordEfficiencyRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询站点信息
            final QueryWrapper<MedPhepStationEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("ID", "NAME").eq("DELETE_FLAG", 0);
            if (null != qcMedicalRecordEfficiencyPo.getHospitalId()) {
                queryWrapper.eq("HOSPITAL_ID", qcMedicalRecordEfficiencyPo.getHospitalId());
            } else if (null != qcMedicalRecordEfficiencyPo.getStationId()) {
                queryWrapper.eq("ID", qcMedicalRecordEfficiencyPo.getStationId());
            }
            final List<MedPhepStationEntity> medPhepStationEntities = new MedPhepStationEntity().selectList(queryWrapper);
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0065);
            if (!CollectionUtils.isEmpty(dictByCode) && !CollectionUtils.isEmpty(medPhepStationEntities)) {
                //获取超时时间
                final int seconds = Integer.parseInt(dictByCode.get(DictionaryEnum.PHFA_0065.getValue()).get(0).getItemcode()) * 60 * 60;
                medPhepStationEntities.forEach(ent -> {
                    final Map<String, Map<String, Long>> map = Maps.newHashMap();
                    final Map<String, Long> qcMap = Maps.newHashMap();
                    //筛出当前站点的数据
                    final List<QcMedicalRecordEfficiencyRo> collect = list.stream().filter(ros -> null != ros.getDispatchVehicleSite() && ent.getId().equals(ros.getDispatchVehicleSite())).collect(Collectors.toList());
                    //判断 病历填写任务数、病历未填写任务数、超时完成数
                    if (!CollectionUtils.isEmpty(collect)) {
                        //判断是否超时
                        qcMap.put("病历填写任务数", collect.stream().filter(ros -> ros.getMedicalRecordStatus().equals("02")).count());
                        qcMap.put("病历未填写任务数", collect.stream().filter(ros -> ros.getMedicalRecordStatus().equals("01")).count());
                        qcMap.put("超时完成数", collect.stream().filter(ros -> ros.getMedicalRecordStatus().equals("02") && null != ros.getCreateTime() && null != ros.getUpdateTime() && MyLocalDateUtil.timeDifference(ros.getCreateTime(), ros.getUpdateTime()) > seconds).count());
                    } else {
                        qcMap.put("病历填写任务数", 0L);
                        qcMap.put("病历未填写任务数", 0L);
                        qcMap.put("超时完成数", 0L);
                    }
                    map.put(ent.getName(), qcMap);
                    result.add(map);
                });
            }
        });
        return result;
    }

    /**
     * 质控管理 - 急救措施统计
     */
    @Override
    public List<Map<String, Long>> qcEmergencyTreatment(final QcEmergencyTreatmentPo qcEmergencyTreatmentPo) {

        final List<Map<String, Long>> result = Lists.newArrayList();
        //查询数据库
        final List<QcEmergencyTreatmentRo> qcEmergencyTreatmentRos = this.qualityMapper.qcEmergencyTreatment(qcEmergencyTreatmentPo);
        Optional.ofNullable(qcEmergencyTreatmentRos).filter(list -> list.size() > 0).ifPresent(list -> {
            //数据字典查询急救措施
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0031);
            if (!CollectionUtils.isEmpty(dictByCode) && dictByCode.get(DictionaryEnum.PHFA_0031.getValue()).size() > 0) {
                dictByCode.get(DictionaryEnum.PHFA_0031.getValue()).forEach(ent -> {
                    final Map<String, Long> map = Maps.newHashMap();
                    map.put(ent.getItemname(), list.stream().filter(ros -> null != ros.getMeasureKey() && ent.getItemcode().equals(ros.getMeasureKey())).count());
                    result.add(map);
                });
                //其他急救措施
                final Map<String, Long> map = Maps.newHashMap();
                map.put("其他", list.stream().filter(ros -> null == ros.getMeasureKey()).count());
                result.add(map);
            }
        });
        return result;
    }
}
