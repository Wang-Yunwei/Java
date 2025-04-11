package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepPatientsService;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.mapper.MedPhepPatientsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author wcq
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepPatientsServiceImpl implements MedPhepPatientsService {

    private final MedPhepPatientsMapper medPhepPatientsMapper;

    @Override
    public void save(final MedPhepPatientsEntity medPhepPatientsEntity) {

        medPhepPatientsEntity.setCreateTime(LocalDateTime.now());
        this.medPhepPatientsMapper.insert(medPhepPatientsEntity);
    }

    @Override
    public void update(final MedPhepPatientsEntity medPhepPatientsEntity) {

        medPhepPatientsEntity.setUpdateTime(LocalDateTime.now());
        this.medPhepPatientsMapper.updateById(medPhepPatientsEntity);
    }

    @Override
    public void deleteById(final String id) {

        this.medPhepPatientsMapper.deleteById(id);
    }

    @Override
    public MedPhepPatientsEntity queryById(final String missionId) {

        final LambdaQueryWrapper<MedPhepPatientsEntity> queryWapper = Wrappers.lambdaQuery();
        queryWapper.eq(MedPhepPatientsEntity::getMissionId, missionId);

        return this.medPhepPatientsMapper.selectOne(queryWapper);
    }

    /**
     * 根据患者id查询患者信息
     */
    @Override
    public MedPhepPatientsEntity queryByPatientId(final String patientId) {

        return this.medPhepPatientsMapper.selectById(patientId);
    }
}
