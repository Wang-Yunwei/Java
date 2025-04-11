package com.mdsd.cloud.controller.firstaidbigscreen.Service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.firstaidbigscreen.Service.BigScreenService;
import com.mdsd.cloud.controller.firstaidbigscreen.dto.*;
import com.mdsd.cloud.controller.firstaidbigscreen.mapper.BigScreenMapper;
import com.mdsd.cloud.controller.map.dto.RecordTrackPo;
import com.mdsd.cloud.entity.MedPhepMeasuresEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.feign.AuthServerFeign;
import com.mdsd.cloud.feign.dto.GetHospitalInfoPo;
import com.mdsd.cloud.mapper.MedPhepMeasuresMapper;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-04-06]
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BigScreenServiceImpl implements BigScreenService {

    @NonNull
    BigScreenMapper bigScreenMapper;

    @NonNull
    MedPhepMeasuresMapper medPhepMeasuresMapper; // 急救措施

    @NonNull
    DataDictionaryService dataDictionaryService;//数据字典

    @NonNull
    RedisTemplate<String, String> redisTemplate;

    @NonNull
    AuthServerFeign authServerFeign;

    /**
     * 急救公告大屏 - 24h内急救患者列表
     */
    @Override
    public PatientList24hReo patientList24h(final PatientList24hPo patientList24hPo) {

        final PatientList24hReo result = new PatientList24hReo();
        //查询24小时内的患者数据
        patientList24hPo.setEndTime(LocalDateTime.now());
        patientList24hPo.setStartTime(patientList24hPo.getEndTime().minusHours(24L));
        final List<PatientInfoListDto> patientInfoListDtoList = this.bigScreenMapper.patientList24h(patientList24hPo);
        Optional.ofNullable(patientInfoListDtoList).filter(list -> list.size() > 0).ifPresent(list -> {
            //获取字典数据
            final GetDictByCodesPao getDictByCodesPao = new GetDictByCodesPao();
            getDictByCodesPao.setCode(Lists.newArrayList(DictionaryEnum.PHFA_0001.getValue(), DictionaryEnum.PHFA_0002.getValue(), DictionaryEnum.PHFA_0011.getValue(), DictionaryEnum.PHFA_0058.getValue()));
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(getDictByCodesPao);
            list.forEach(dto -> {
                //PHFA_0001:性别
                dto.setGender(StringUtils.isNotBlank(dto.getGender()) ? dictByCode.get(DictionaryEnum.PHFA_0001.getValue()).stream().filter(mdeEnt -> dto.getGender().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
                //PHFA_0002:年龄段
                dto.setAge(StringUtils.isNotBlank(dto.getAgeExtend()) ? String.format("%s %s", dto.getAge(), dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(mdeEnt -> dto.getAgeExtend().equals(mdeEnt.getItemcode())).findFirst().get().getItemname()) : dto.getAge());
                //PHFA_0011:疾病类别
                if (StringUtils.isNotBlank(dto.getDiseaseCategory())) {
                    dictByCode.get(DictionaryEnum.PHFA_0011.getValue()).stream().filter(mdeEnt -> dto.getDiseaseCategory().equals(mdeEnt.getItemcode())).findFirst().ifPresent(ent -> {
                        dto.setDiseaseCategory(ent.getItemname());
                        //统计专科
                        if (StringUtils.isNotBlank(ent.getExtend())) {
                            switch (ent.getExtend().toUpperCase()) {
                                case "CPC"://胸痛
                                    result.setPectoralgia(result.getPectoralgia() + 1);
                                    break;
                                case "STROKE"://卒中
                                    result.setStroke(result.getStroke() + 1);
                                    break;
                                case "TRAUMA"://创伤
                                    result.setTrauma(result.getTrauma() + 1);
                                    break;
                                case "NEONATUS"://新生儿
                                case "MATERNAL"://创伤
                                    //其它危急重症
                                    result.setOtherDisease(result.getTrauma() + 1);
                                    break;
                            }
                        }
                    });
                }
                //PHFA_0058:交接科室
                dto.setDept(StringUtils.isNotBlank(dto.getDept()) ? dictByCode.get(DictionaryEnum.PHFA_0058.getValue()).stream().filter(mdeEnt -> dto.getDept().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
            });
            //目前经救护车入院总数
            result.setNowAmbulanceToHospitalTotal(list.size());
            result.setPatientInfoListDtoList(list);
        });
        return result;
    }

    /**
     * 急救公告大屏 - 时间轴
     */
    @Override
    public List<TimeAxisReo> timeAxis(final String hospitalArea) {

        return this.bigScreenMapper.timeAxis(hospitalArea);
    }

    /**
     * 急救公告大屏 - 移动轨迹
     */
    @Override
    public List<MobilityTraceReo> mobilityTrace(final MobilityTracePo param) {

        //判断是否查询Redis中MissionId
        List<String> collect = null;
        if (Boolean.FALSE) {
            //查询已经告知的数据
            final String redisKey = String.format(CommonConstants.PREFIX, param.getModuleName(), param.getKey());//拼接Redis的Key
            final Boolean aBoolean = this.redisTemplate.hasKey(redisKey);//判断key是否存在
            final Long size = this.redisTemplate.opsForList().size(redisKey);//获取集合的长度
            if (aBoolean && size > 0) {
                final List<String> range = this.redisTemplate.opsForList().range(redisKey, 0, size);
                //去重
                collect = range.stream().distinct().collect(Collectors.toList());
            } else {
                return null;
            }
        }
        //查询数据库
        final List<MobilityTraceReo> result = this.bigScreenMapper.mobilityTrace(collect, param.getHospitalId());
        //处理字典数据
        Optional.ofNullable(result).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCodeMap = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0001, DictionaryEnum.PHFA_0002, DictionaryEnum.PHFA_0011, DictionaryEnum.PHFA_0031, DictionaryEnum.PHFA_0034);
            list.forEach(dto -> {
                //PHFA_0001:性别
                dto.setGender(StringUtils.isNotBlank(dto.getGender()) ? dictByCodeMap.get(DictionaryEnum.PHFA_0001.getValue()).stream().filter(mdeEnt -> dto.getGender().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
                //处理年龄类别
                if (StringUtils.isNotBlank(dto.getAge())) {
                    dto.setAge(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getAgeExtend()) ? String.format("%s%s", dto.getAge(), dictByCodeMap.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()) : dto.getAge());
                }
                //PHFA_0011:疾病类别
                dto.setDiseaseCategory(StringUtils.isNotBlank(dto.getDiseaseCategory()) ? dictByCodeMap.get(DictionaryEnum.PHFA_0011.getValue()).stream().filter(mdeEnt -> dto.getDiseaseCategory().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
                //PHFA_0031:急救措施
//                dto.setTreatment(StringUtils.isNotBlank(dto.getTreatment()) ? dictByCodeMap.get(DictionaryEnum.PHFA_0031.getValue()).stream().filter(mdeEnt -> dto.getTreatment().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
                List<MedPhepMeasuresEntity> medPhepMeasuresEntities = medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery().eq(MedPhepMeasuresEntity::getPatientId, dto.getPatientId()));
                dto.setMedPhepMeasuresEntities(medPhepMeasuresEntities);

                //PHFA_0034:任务状态
                dto.setMissionStatus(StringUtils.isNotBlank(dto.getMissionStatus()) ? dictByCodeMap.get(DictionaryEnum.PHFA_0034.getValue()).stream().filter(mdeEnt -> dto.getMissionStatus().equals(mdeEnt.getItemcode())).findFirst().get().getItemname() : null);
                /*移动轨迹*/
                final String redisKey1 = String.format(CommonConstants.PREFIX, param.getModuleName(), dto.getMissionId());//拼接RedisKey
                final Boolean aBoolean1 = this.redisTemplate.hasKey(redisKey1);//判断key是否存在
                final Long size1 = this.redisTemplate.opsForList().size(redisKey1);
                if (Boolean.TRUE.equals(aBoolean1) && size1 > 0) {
                    final List<String> range1 = this.redisTemplate.opsForList().range(redisKey1, 0, size1);
                    if (!CollectionUtils.isEmpty(range1)) {
                        RecordTrackPo recordTrackPo;
                        try {
                            for (final String rtp : range1) {
                                //经过点
                                recordTrackPo = JSON.parseObject(rtp, RecordTrackPo.class);
                                final HashMap<String, BigDecimal> throughPointMap = Maps.newHashMap();
                                throughPointMap.put("lng", recordTrackPo.getCurrentPoints().getLng());
                                throughPointMap.put("lat", recordTrackPo.getCurrentPoints().getLat());
                                dto.getThroughPoint().add(throughPointMap);
                            }
                        } catch (final Exception e) {
                            throw new BusinessException(String.format("Json转Object失败!%s", e.getMessage()));
                        }
                        //起点
                        dto.setStartPoint(dto.getThroughPoint().get(0));
                        //终点
                        if (null != dto.getToHospitalId()) {
                            final String[] s = dto.getToHospitalId().split("_");
                            //送往醫院ID不爲NULL,查詢醫院位置
                            log.info("Auth::getHospitalInfo - [InParameter]=> {}", s[0]);
                            final Map<String, Object> res = this.authServerFeign.getHospitalInfo(new GetHospitalInfoPo().setHospitalSign(s[0]));
                            final Map<String, Object> map = JSON.parseArray(JSON.toJSONString(res.get("result")), Map.class).get(0);
                            dto.getEndPoint().put("lng", (BigDecimal) map.get("longitude"));
                            dto.getEndPoint().put("lat", (BigDecimal) map.get("latitude"));
                        }
                    }
                }
            });
        });
        return result;
    }
}
