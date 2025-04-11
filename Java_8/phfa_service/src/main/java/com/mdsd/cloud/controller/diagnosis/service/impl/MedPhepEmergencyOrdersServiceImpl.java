package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mdsd.cloud.controller.diagnosis.function.MedPhepEmergencyOrdersFunction;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepEmergencyOrdersService;
import com.mdsd.cloud.entity.MedPhepEmergencyOrdersEntity;
import com.mdsd.cloud.enumeration.ValidEnum;
import com.mdsd.cloud.mapper.MedPhepEmergencyOrdersMapper;
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
public class MedPhepEmergencyOrdersServiceImpl implements MedPhepEmergencyOrdersService {

    private final MedPhepEmergencyOrdersFunction medPhepEmergencyOrdersFunction;

    private final MedPhepEmergencyOrdersMapper medPhepEmergencyOrdersMapper;

    @Override
    public boolean saveOrUpdate(final MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity) {

        medPhepEmergencyOrdersEntity.setStatus(ValidEnum.VALID.getValue());
        medPhepEmergencyOrdersEntity.setIsHand(ValidEnum.INVALID.getValue());
        final boolean flag = this.medPhepEmergencyOrdersFunction.saveOrUpdate(medPhepEmergencyOrdersEntity);
        return flag;
    }

    @Override
    public List<MedPhepEmergencyOrdersEntity> queryByPatientId(final String patientId) {

        final LambdaQueryWrapper<MedPhepEmergencyOrdersEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepEmergencyOrdersEntity::getPatientId, patientId)
                .eq(MedPhepEmergencyOrdersEntity::getStatus, ValidEnum.VALID.getValue());
        final List<MedPhepEmergencyOrdersEntity> medPhepEmergencyOrdersEntities = this.medPhepEmergencyOrdersMapper.selectList(queryWrapper);
        return medPhepEmergencyOrdersEntities;
    }

    /**
     * 根据用药Id删除信息
     */
    @Override
    public int deleteEmergencyOrdersById(final String orderId) {

        final MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity = this.medPhepEmergencyOrdersMapper.selectById(orderId);
        if (medPhepEmergencyOrdersEntity != null) {
            medPhepEmergencyOrdersEntity.setStatus(ValidEnum.INVALID.getValue());
        }
        return this.medPhepEmergencyOrdersMapper.updateById(medPhepEmergencyOrdersEntity);
    }
}
