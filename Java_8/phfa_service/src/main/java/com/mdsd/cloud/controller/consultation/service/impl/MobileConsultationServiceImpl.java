package com.mdsd.cloud.controller.consultation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.consultation.dto.MccListPo;
import com.mdsd.cloud.controller.consultation.dto.MccListRo;
import com.mdsd.cloud.controller.consultation.dto.MccSaveOrUpdateParDto;
import com.mdsd.cloud.controller.consultation.mapper.MobileConsultationMapper;
import com.mdsd.cloud.controller.consultation.service.MobileConsultationService;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.entity.MedPhepMobileConsultationEntity;
import com.mdsd.cloud.enumeration.CommonEnum;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.feign.ImServerFeign;
import com.mdsd.cloud.feign.dto.ImAddMembersPo;
import com.mdsd.cloud.feign.dto.ImCreatePo;
import com.mdsd.cloud.feign.dto.ImCreateRo;
import com.mdsd.cloud.response.ResponseMessage;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author WangYunwei [2021-12-31]
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MobileConsultationServiceImpl implements MobileConsultationService {

    @NonNull
    MobileConsultationMapper mobileConsultationMapper;

    @NonNull
    ImServerFeign imServerFeign;

    @NonNull
    DataDictionaryService dataDictionaryService;//数据字典

    /**
     * 移动会诊 - 保存or更新
     */
    @Override
    public String mccSaveOrUpdate(final MccSaveOrUpdateParDto mccSaveOrUpdateParDto) {

        String result = null;
        Assert.notNull(mccSaveOrUpdateParDto.getUserId(), "会诊人员为: NULL");
        //判断参与人员是否为NULL
        List<String> userIds = Lists.newArrayList(mccSaveOrUpdateParDto.getUserId());
        if (!StringUtils.isEmpty(mccSaveOrUpdateParDto.getImUserids())) {
            if (mccSaveOrUpdateParDto.getImUserids().length() > 1) {
                final String[] split = mccSaveOrUpdateParDto.getImUserids().split(",");
                //去除 "" 字符串并添加到List中
                userIds.addAll(Arrays.stream(split).filter(s -> !"".equals(s)).collect(Collectors.toList()));
            } else {
                userIds.add(mccSaveOrUpdateParDto.getImUserids());
            }
        }
        userIds = userIds.stream().distinct().collect(Collectors.toList());//去重
        //查询数据字典
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(new GetDictByCodesPao().setCode(Lists.newArrayList(DictionaryEnum.PHFA_0062.getValue())));
        if (CollectionUtils.isEmpty(dictByCode)) {
            throw new BusinessException("数据字典中未查询到IM请求超链接!");
        }
        //判断当前患者是否已经存在移动会诊群组
        MedPhepMobileConsultationEntity medPhepMobileConsultationEntity = this.mobileConsultationMapper.selectOne(Wrappers.<MedPhepMobileConsultationEntity>lambdaQuery().eq(MedPhepMobileConsultationEntity::getPatientId, mccSaveOrUpdateParDto.getPatientId()));
        if (Objects.nonNull(medPhepMobileConsultationEntity)) {
            //去除已经存在于库中的人员
            List<String> updateUserIds = null;
            if (null != medPhepMobileConsultationEntity.getImUserids()) {
                final List<String> strings = JSON.parseArray(medPhepMobileConsultationEntity.getImUserids(), String.class);
                userIds.addAll(strings);
                updateUserIds = userIds.stream().filter(el1 -> strings.stream().noneMatch(el2 -> Objects.equals(el1, el2))).collect(Collectors.toList());
            }
            //添加会审人员
            if (updateUserIds.size() > 0) {
                final ImAddMembersPo imAddMembersPo = new ImAddMembersPo().setChannelId(medPhepMobileConsultationEntity.getGroupId()).setUserIds(updateUserIds);
                log.info("IM::addMembers - [InParameter] => {}", JSON.toJSONString(imAddMembersPo));
                try {
                    final ResponseMessage<?> stringResponseMessage = this.imServerFeign.imAddMembers(imAddMembersPo);
                    if (!"0".equals(stringResponseMessage.getCode())) {
                        throw new BusinessException(stringResponseMessage.getMessage());
                    }
                } catch (final Exception e) {
                    throw new BusinessException(String.format("IM::addMembers 接口异常,%s", e.getMessage()));
                }
                //更新移动会诊表
                BeanUtils.copyProperties(mccSaveOrUpdateParDto, medPhepMobileConsultationEntity);
                medPhepMobileConsultationEntity.setImUserids(JSON.toJSONString(userIds));
                medPhepMobileConsultationEntity.updateById();
            }
            //拼接返回数据
            if (Strings.isNotBlank(medPhepMobileConsultationEntity.getGroupId())) {
                result = String.format(dictByCode.get(DictionaryEnum.PHFA_0062.getValue()).get(0).getItemname(), medPhepMobileConsultationEntity.getGroupId(), mccSaveOrUpdateParDto.getJwtToken());
            }
        } else {
            //判断参与移动会诊人员是否为NULL
            if (CollectionUtils.isEmpty(userIds)) {
                throw new BusinessException("参与移动会诊人员不能小于一位!");
            }
            //首先创建ChannelId
            final ImCreatePo imCreatePo = new ImCreatePo()
                    .setCreateUserId(mccSaveOrUpdateParDto.getUserId())
                    .setAdminUserId(mccSaveOrUpdateParDto.getUserId())
                    .setUserIds(userIds)
                    .setChannelName(org.apache.commons.lang3.StringUtils.isNotBlank(mccSaveOrUpdateParDto.getPatientName()) ? String.format("%s的移动会诊", mccSaveOrUpdateParDto.getPatientName()) : "未知患者的移动会诊");
            log.info("IM::create - [InParameter] => {}", JSON.toJSONString(imCreatePo));
            final ResponseMessage<ImCreateRo> imCreateRoResponseMessage;
            try {
                imCreateRoResponseMessage = this.imServerFeign.imCreate(imCreatePo);
                if (!"0".equals(imCreateRoResponseMessage.getCode())) {
                    throw new BusinessException(imCreateRoResponseMessage.getMessage());
                }
            } catch (final Exception e) {
                throw new BusinessException(String.format("IM::create 接口异常,%s", e.getMessage()));
            }
            if (Objects.nonNull(imCreateRoResponseMessage.getResult())) {
                //保存信息到数据库
                medPhepMobileConsultationEntity = new MedPhepMobileConsultationEntity();
                BeanUtils.copyProperties(mccSaveOrUpdateParDto, medPhepMobileConsultationEntity);
                medPhepMobileConsultationEntity.setImUserids(JSON.toJSONString(imCreateRoResponseMessage.getResult().getUserIds()));
                medPhepMobileConsultationEntity.setGroupId(imCreateRoResponseMessage.getResult().getChannelId());
                medPhepMobileConsultationEntity.setCreateTime(LocalDateTime.now());
                medPhepMobileConsultationEntity.insert();
                //拼接返回数据
                result = String.format(dictByCode.get(DictionaryEnum.PHFA_0062.getValue()).get(0).getItemname(), imCreateRoResponseMessage.getResult().getChannelId(), mccSaveOrUpdateParDto.getJwtToken());
            }
        }
        return result;
    }

    /**
     * 移动会诊 - 列表
     */
    @Override
    public List<MccListRo> mccList(final MccListPo mccListPo) {

        final List<MccListRo> result = Lists.newArrayList();
        //获取患者列表中五大专科患者或移动会诊列表中的患者
        final List<MccListRo> mccListRos = this.mobileConsultationMapper.mccList(mccListPo);
        if (!CollectionUtils.isEmpty(mccListRos)) {
            //查询年龄类别、疾病类别数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(new GetDictByCodesPao().setCode(Arrays.asList(DictionaryEnum.PHFA_0002.getValue(), DictionaryEnum.PHFA_0011.getValue(), DictionaryEnum.PHFA_0062.getValue())));
            //处理患者数据中的疾病类别
            Optional.ofNullable(dictByCode).filter(map -> map.size() > 0).ifPresent(map -> {
                mccListRos.forEach(dto -> {
                    //处理年龄类别
                    if (!StringUtils.isEmpty(dto.getAge())) {
                        dto.setAge(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getAgeExtend()) ? String.format("%s%s", dto.getAge(), map.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()) : dto.getAge());
                    }
                    //疾病类别
                    MedDictionaryEntity medDictionaryEntity = null;
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(dto.getDiseaseCategory())) {
                        medDictionaryEntity = map.get(DictionaryEnum.PHFA_0011.getValue()).stream().filter(dic -> dic.getItemcode().equals(dto.getDiseaseCategory())).findFirst().get();
                    }
                    //是否已经创建IM群组
                    if (Strings.isNotBlank(dto.getGroupId())) {
                        dto.setImUrl(String.format(dictByCode.get(DictionaryEnum.PHFA_0062.getValue()).get(0).getItemname(), dto.getGroupId(), mccListPo.getBusinessToken()));
                    }
                    //添加数据到返回列表中
                    if (Objects.nonNull(medDictionaryEntity) && !StringUtils.isEmpty(medDictionaryEntity.getExtend())) {
                        //筛选患者列表中五大专科患者
                        dto.setDiseaseCategoryStr(CommonEnum.getValueByKey(medDictionaryEntity.getExtend()));
                        result.add(dto);
                    } else {
                        dto.setDiseaseCategoryStr("非五大专科");
                    }
                });
            });
            //查询移动会诊表中的患者数据
            final List<MedPhepMobileConsultationEntity> mobileConsultationEntities = this.mobileConsultationMapper.selectList(Wrappers.<MedPhepMobileConsultationEntity>lambdaQuery().eq(MedPhepMobileConsultationEntity::getLicensePlate, mccListPo.getLicensePlate()));
            final List<String> patientIds = !CollectionUtils.isEmpty(mobileConsultationEntities) ? mobileConsultationEntities.stream().map(MedPhepMobileConsultationEntity::getPatientId).collect(Collectors.toList()) : null;
            if (!CollectionUtils.isEmpty(patientIds)) {
                //筛选出移动会诊表中存在的患者数据
                final List<MccListRo> collect = mccListRos.stream().filter(dto -> patientIds.stream()
                        .anyMatch(patientId -> Objects.equals(dto.getPatientId(), patientId))).collect(Collectors.toList());
                result.addAll(collect);
            }
            //出去重复患者信息
            final List<MccListRo> collect = result.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<MccListRo>(comparing(MccListRo::getPatientId))), ArrayList::new));
            result.clear();
            result.addAll(collect);
        }
        return result.stream().sorted(Comparator.nullsLast(Comparator.comparing(MccListRo::getCreateTime).reversed())).collect(Collectors.toList());
    }
}
