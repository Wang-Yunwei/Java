package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepMeasuresService;
import com.mdsd.cloud.entity.MedPhepMeasuresEntity;
import com.mdsd.cloud.mapper.MedPhepMeasuresMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepMeasuresServiceImpl implements MedPhepMeasuresService {

    private final MedPhepMeasuresMapper medPhepMeasuresMapper;

    @Override
    public List<MedPhepMeasuresEntity> queryByPatientId(final String patientId) {

        final LambdaQueryWrapper<MedPhepMeasuresEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepMeasuresEntity::getPatientId, patientId);
        return this.medPhepMeasuresMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteMeasures(final String id) {

        return this.medPhepMeasuresMapper.deleteById(id);
    }

    @Override
    public int saveMeasures(final MedPhepMeasuresEntity medPhepMeasuresEntity) {

        final LambdaQueryWrapper<MedPhepMeasuresEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (medPhepMeasuresEntity.getMeasureKey() == null) {
            queryWrapper.eq(MedPhepMeasuresEntity::getPatientId, medPhepMeasuresEntity.getPatientId())
                    .isNull(MedPhepMeasuresEntity::getMeasureKey);
        } else {
            queryWrapper.eq(MedPhepMeasuresEntity::getPatientId, medPhepMeasuresEntity.getPatientId())
                    .eq(MedPhepMeasuresEntity::getMeasureKey, medPhepMeasuresEntity.getMeasureKey());
        }
        final MedPhepMeasuresEntity measuresEntity = this.medPhepMeasuresMapper.selectOne(queryWrapper);
        if (measuresEntity != null) {
            return this.medPhepMeasuresMapper.update(medPhepMeasuresEntity, queryWrapper);
        } else {
            return this.medPhepMeasuresMapper.insert(medPhepMeasuresEntity);
        }
    }
}
