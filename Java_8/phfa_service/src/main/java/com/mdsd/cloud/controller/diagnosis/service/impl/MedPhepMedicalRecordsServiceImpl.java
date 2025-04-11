package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepMedicalRecordsService;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.mapper.MedPhepMedicalRecordsMapper;
import com.mdsd.cloud.utils.PrimaryKeyUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepMedicalRecordsServiceImpl implements MedPhepMedicalRecordsService {

    private final MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;

    private final PrimaryKeyUtil primaryKeyUtil;

    private final DataDictionaryService dataDictionaryService;//数据字典

    @Override
    public MedPhepMedicalRecordsEntity queryById(final String patientId) {

        final LambdaQueryWrapper<MedPhepMedicalRecordsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepMedicalRecordsEntity::getPatientId, patientId);
        final MedPhepMedicalRecordsEntity result = this.medPhepMedicalRecordsMapper.selectOne(queryWrapper);
        Optional.ofNullable(result).filter(ent -> StringUtils.isNotBlank(ent.getDiagnosis())).ifPresent(ent -> {
            //初步诊断
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(new GetDictByCodesPao().setCode(Lists.newArrayList(DictionaryEnum.PHFA_0057.getValue(), DictionaryEnum.PHFA_0001.getValue())));
            if (!CollectionUtils.isEmpty(dictByCode)) {
                dictByCode.get(DictionaryEnum.PHFA_0057.getValue()).stream().filter(dic -> ent.getDiagnosis().equals(dic.getItemcode())).findFirst().ifPresent(dic -> {
                    ent.setDiagnosis(dic.getItemname());
                });
            }
        });
        return result;
    }

    /**
     * 新增or修改
     */
    @Override
    public String saveOrUpdate(final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity) {

        if (null != medPhepMedicalRecordsEntity.getRecordId()) {
            this.medPhepMedicalRecordsMapper.updateById(medPhepMedicalRecordsEntity);
        } else {
            medPhepMedicalRecordsEntity.setRecordId(this.primaryKeyUtil.getFlowingWaterId(CommonConstants.MED_PHEP_MEDICAL_RECORDS));
            medPhepMedicalRecordsEntity.setStatus("01");//01:填写中
            this.medPhepMedicalRecordsMapper.insert(medPhepMedicalRecordsEntity);
        }
        return medPhepMedicalRecordsEntity.getRecordId();
    }

    /**
     * 根据患者ID新增or修改
     */
    @Override
    public int updateByPatientId(final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity) {

        medPhepMedicalRecordsEntity.setUpdateTime(LocalDateTime.now());
        final LambdaQueryWrapper<MedPhepMedicalRecordsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepMedicalRecordsEntity::getPatientId, medPhepMedicalRecordsEntity.getPatientId());
        return this.medPhepMedicalRecordsMapper.update(medPhepMedicalRecordsEntity, queryWrapper);

    }
}
