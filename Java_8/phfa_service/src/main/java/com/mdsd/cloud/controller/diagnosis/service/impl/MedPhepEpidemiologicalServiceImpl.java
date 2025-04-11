package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepEpidemiologicalService;
import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepEpidemiologicalEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.mapper.MedPhepEpidemiologicalMapper;
import com.mdsd.cloud.mapper.MedPhepPatientsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepEpidemiologicalServiceImpl implements MedPhepEpidemiologicalService {

    private final MedPhepEpidemiologicalMapper medPhepEpidemiologicalMapper;

    private final MedPhepPatientsMapper medPhepPatientsMapper;

    @Override
    public int saveOrUpdate(final EpidemiologicalSaveOrUpdateVo epidemiologicalSaveOrUpdateVo) {

        final MedPhepEpidemiologicalEntity medPhepEpidemiologicalEntity = epidemiologicalSaveOrUpdateVo.getMedPhepEpidemiologicalEntity();
        final MedPhepPatientsEntity medPhepPatientsEntity = epidemiologicalSaveOrUpdateVo.getMedPhepPatientsEntity();

        final LambdaQueryWrapper<MedPhepEpidemiologicalEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepEpidemiologicalEntity::getPatientId, medPhepEpidemiologicalEntity.getPatientId());

        final MedPhepEpidemiologicalEntity epidemiologicalEntity = this.medPhepEpidemiologicalMapper.selectOne(queryWrapper);
        //修改患者信息表中姓名，卡号
        final MedPhepPatientsEntity patientsEntity = this.medPhepPatientsMapper.selectById(medPhepPatientsEntity.getPatientId());
        if (patientsEntity != null) {
            patientsEntity.setCardNo(medPhepPatientsEntity.getCardNo());
            patientsEntity.setName(medPhepPatientsEntity.getName());
            //修改患者信息
            this.medPhepPatientsMapper.updateById(patientsEntity);
        }
        if (epidemiologicalEntity != null) {
            return this.medPhepEpidemiologicalMapper.update(medPhepEpidemiologicalEntity, queryWrapper);
        } else {
            return this.medPhepEpidemiologicalMapper.insert(medPhepEpidemiologicalEntity);
        }
    }

    @Override
    public EpidemiologicalDetailVo queryByPatientId(final String patientId) {

        final MedPhepEpidemiologicalEntity epidemiologicalEntity = this.medPhepEpidemiologicalMapper.selectOne(Wrappers.<MedPhepEpidemiologicalEntity>lambdaQuery()
                .eq(MedPhepEpidemiologicalEntity::getPatientId, patientId));

        final MedPhepPatientsEntity medPhepPatientsEntity = this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery()
                .eq(MedPhepPatientsEntity::getPatientId, patientId));
        final EpidemiologicalDetailVo epidemiologicalDetailVo = new EpidemiologicalDetailVo();
        epidemiologicalDetailVo.setMedPhepPatientsEntity(medPhepPatientsEntity);
        epidemiologicalDetailVo.setMedPhepEpidemiologicalEntity(epidemiologicalEntity);
        return epidemiologicalDetailVo;
    }
}
