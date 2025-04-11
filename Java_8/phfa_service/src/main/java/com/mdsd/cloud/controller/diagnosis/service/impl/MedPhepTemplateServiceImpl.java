package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepTemplateService;
import com.mdsd.cloud.entity.MedPhepTemplateEntity;
import com.mdsd.cloud.enumeration.ValidEnum;
import com.mdsd.cloud.mapper.MedPhepTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wcq
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepTemplateServiceImpl implements MedPhepTemplateService {

    private final MedPhepTemplateMapper medPhepTemplateMapper;

    @Override
    public void save(final MedPhepTemplateEntity medPhepTemplateEntity) {

        this.medPhepTemplateMapper.insert(medPhepTemplateEntity);
    }

    @Override
    public void update(final MedPhepTemplateEntity medPhepTemplateEntity) {

        this.medPhepTemplateMapper.updateById(medPhepTemplateEntity);
    }

    @Override
    public void deleteById(final String id) {

        this.medPhepTemplateMapper.deleteById(id);
    }

    @Override
    public List<MedPhepTemplateEntity> queryList(final String tempType) {

        final LambdaQueryWrapper<MedPhepTemplateEntity> queryWapper = Wrappers.lambdaQuery();
        queryWapper.eq(MedPhepTemplateEntity::getStatus, ValidEnum.VALID.getValue())
                .eq(MedPhepTemplateEntity::getTempType, tempType);

        return this.medPhepTemplateMapper.selectList(queryWapper);
    }
}
