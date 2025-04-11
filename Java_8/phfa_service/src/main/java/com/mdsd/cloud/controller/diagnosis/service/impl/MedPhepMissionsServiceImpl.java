package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepMedicalRecordsService;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepMissionsService;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepPatientsService;
import com.mdsd.cloud.controller.diagnosis.vo.*;
import com.mdsd.cloud.controller.diagnosis.vo.request.MissionsInfoRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsDetailRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsUpdateStatusRequest;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.map.dto.SmcSaveOrUpdatePo;
import com.mdsd.cloud.controller.map.service.SpecialtyMapService;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.mapper.MedPhepMissionsMapper;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wcq
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepMissionsServiceImpl implements MedPhepMissionsService {

    private final MedPhepMissionsMapper medPhepMissionsMapper;

    private final MedPhepPatientsService medPhepPatientsService;

    private final MedPhepMedicalRecordsService medPhepMedicalRecordsService;

    private final SpecialtyMapService specialtyMapService;

    private final DataDictionaryService dataDictionaryService;//数据字典

    @Override
    public void save(final MedPhepMissionsEntity medPhepMissionsEntity) {

        medPhepMissionsEntity.setCallTime(LocalDateTime.now());
        this.medPhepMissionsMapper.insert(medPhepMissionsEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhepMissionsInitVo init(final MedPhepMissionsEntity medPhepMissionsEntity) {

        final PhepMissionsInitVo result = new PhepMissionsInitVo();
        //判断当前车辆下面是否有未完成的任务,有则直接Return
        final Long integer = this.medPhepMissionsMapper.selectCount(Wrappers.<MedPhepMissionsEntity>lambdaQuery().eq(MedPhepMissionsEntity::getLicensePlate, medPhepMissionsEntity.getLicensePlate()).notIn(MedPhepMissionsEntity::getStatus, Arrays.asList("06", "07")));
        if (null != integer && integer > 0) {
            throw new BusinessException(String.format("车牌号: %s 存在未完成任务!", medPhepMissionsEntity.getLicensePlate()));
        }
        this.save(medPhepMissionsEntity);

        final MedPhepPatientsEntity medPhepPatientsEntity = new MedPhepPatientsEntity();
        medPhepPatientsEntity.setMissionId(medPhepMissionsEntity.getMissionId());
        this.medPhepPatientsService.save(medPhepPatientsEntity);

        final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = new MedPhepMedicalRecordsEntity();
        medPhepMedicalRecordsEntity.setPatientId(medPhepPatientsEntity.getPatientId());
        medPhepMedicalRecordsEntity.setCreateTime(LocalDateTime.now());
        this.medPhepMedicalRecordsService.saveOrUpdate(medPhepMedicalRecordsEntity);

        result.setMissionId(medPhepMissionsEntity.getMissionId());
        result.setPatientId(medPhepPatientsEntity.getPatientId());
        result.setRecordId(medPhepMedicalRecordsEntity.getRecordId());

        return result;
    }

    @Override
    public void update(final MedPhepMissionsEntity medPhepMissionsEntity) {

        medPhepMissionsEntity.setUpdateTime(LocalDateTime.now());
        this.medPhepMissionsMapper.updateById(medPhepMissionsEntity);
    }

    @Override
    public void deleteById(final String id) {

        this.medPhepMissionsMapper.deleteById(id);
    }

    @Override
    public MedPhepMissionsEntity queryByLicensePlate(final PhepMissionsRequest phepMissionsRequest) {

        final LambdaQueryWrapper<MedPhepMissionsEntity> queryWapper = Wrappers.lambdaQuery();
        queryWapper.eq(MedPhepMissionsEntity::getLicensePlate, phepMissionsRequest.getLicensePlate())
                .notIn(MedPhepMissionsEntity::getStatus, Arrays.asList("06", "07"));

        return this.medPhepMissionsMapper.selectOne(queryWapper);
    }

    @Override
    public MedPhepMissionsEntity queryById(final String missionId) {

        final LambdaQueryWrapper<MedPhepMissionsEntity> queryWapper = Wrappers.lambdaQuery();
        queryWapper.eq(MedPhepMissionsEntity::getMissionId, missionId);

        return this.medPhepMissionsMapper.selectOne(queryWapper);
    }

    @Override
    public void updateStatus(final PhepMissionsUpdateStatusRequest phepMissionsUpdateStatusRequest) {

        final MedPhepMissionsEntity phepMissionsEntity = this.medPhepMissionsMapper.selectById(phepMissionsUpdateStatusRequest.getMissionId());
        if (Objects.isNull(phepMissionsEntity)) {
            throw new BusinessException("当前任务不存在");
        }

        switch (phepMissionsUpdateStatusRequest.getStatus()) {
            case "02"://赶往现场
                phepMissionsEntity.setDispatchVehicleTime(LocalDateTime.now());
                break;
            case "03"://到达现场
                phepMissionsEntity.setArrivedSceneTime(LocalDateTime.now());
                break;
            case "04"://送往医院
                phepMissionsEntity.setLeftSceneTime(LocalDateTime.now());
                break;
            case "05"://到达医院
                phepMissionsEntity.setArrivedHospitalTime(LocalDateTime.now());
                break;
        }

        phepMissionsEntity.setStatus(phepMissionsUpdateStatusRequest.getStatus());
        this.medPhepMissionsMapper.updateById(phepMissionsEntity);
        //如果完成状态=06则保存行动轨迹
        if (StringUtils.isNotBlank(phepMissionsUpdateStatusRequest.getMissionId()) && "06".equals(phepMissionsUpdateStatusRequest.getStatus())) {
            this.specialtyMapService.smcSaveOrUpdate(new SmcSaveOrUpdatePo().setMissionId(phepMissionsUpdateStatusRequest.getMissionId()));
        }
    }

    @Override
    public PhepMissionsDetailVo detail(final PhepMissionsDetailRequest phepMissionsDetailRequest) {

        final MedPhepMissionsEntity missionsEntity = this.queryById(phepMissionsDetailRequest.getMissionId());
        if (Objects.isNull(missionsEntity)) {
            throw new BusinessException("当前任务不存在");
        }

        final PhepMissionsDetailVo result = new PhepMissionsDetailVo();
        result.setMissionsEntity(missionsEntity);

        final MedPhepPatientsEntity patientsEntity = this.medPhepPatientsService.queryById(phepMissionsDetailRequest.getMissionId());
        if (Objects.nonNull(patientsEntity)) {
            result.setPatientsEntity(patientsEntity);

            final MedPhepMedicalRecordsEntity medicalRecordsEntity = this.medPhepMedicalRecordsService.queryById(patientsEntity.getPatientId());
            if (Objects.nonNull(medicalRecordsEntity)) {
                result.setMedicalRecordsEntity(medicalRecordsEntity);
            }
        }

        return result;
    }

    @Override
    public List<PhepMissionsHistoryListVo> queryMissionsList(final PhepMissionsParamVo phepMissionsParamVo) {

        final List<PhepMissionsHistoryListVo> historyListVoList = this.medPhepMissionsMapper.queryMissionsList(phepMissionsParamVo);
        Optional.ofNullable(historyListVoList).filter(list -> list.size() > 0).ifPresent(list -> {
            //PHFA_0002:年龄类别
            final GetDictByCodesPao getDictByCodesPao = new GetDictByCodesPao().setCode(Lists.newArrayList(DictionaryEnum.PHFA_0002.getValue()));
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(getDictByCodesPao);
            if (!org.springframework.util.CollectionUtils.isEmpty(dictByCode)) {
                list.forEach(dto -> {
                    if (StringUtils.isNotBlank(dto.getAge())) {
                        dto.setAge(StringUtils.isNotBlank(dto.getAgeExtend()) ? String.format("%s%s", dto.getAge(), dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()) : dto.getAge());
                    }
                });
            }
        });
        return historyListVoList;
    }

    @Override
    public IPage<MissionsInfoVo> queryByCallTime(final MissionsInfoRequest missionsInfoRequest) {

        final IPage<MissionsInfoVo> missionsInfoVoPage = this.medPhepMissionsMapper.queryByCallTime(new Page<>(missionsInfoRequest.getPageNo(), missionsInfoRequest.getPageSize()), missionsInfoRequest);
        if (CollectionUtils.isNotEmpty(missionsInfoVoPage.getRecords())) {
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0003);
            Optional.ofNullable(dictByCode).filter(map -> map.size() > 0).ifPresent(map -> {
                final List<MedDictionaryEntity> PHFA_0003 = map.get(DictionaryEnum.PHFA_0003.getValue());
                for (final MissionsInfoVo item : missionsInfoVoPage.getRecords()) {
                    item.setCardType(PHFA_0003.stream().filter(ent -> null != item.getCardType() && ent.getItemcode().equals(item.getCardType())).findFirst().get().getItemname());
                }
            });
        }
        return missionsInfoVoPage;
    }
}
