package com.mdsd.cloud.controller.pc.emergencycar.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListPo;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListRo;
import com.mdsd.cloud.controller.pc.emergencycar.mapper.EmergencyCarMapper;
import com.mdsd.cloud.controller.pc.emergencycar.service.EmergencyCarService;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import com.mdsd.cloud.entity.MedPhepVehiclesEntity;
import com.mdsd.cloud.mapper.MedPhepMissionsMapper;
import com.mdsd.cloud.mapper.MedPhepVehiclesMapper;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-05-24]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EmergencyCarServiceImpl implements EmergencyCarService {

    @NonNull
    EmergencyCarMapper emergencyCarMapper;

    @NonNull
    MedPhepVehiclesMapper medPhepVehiclesMapper;//车辆信息表

    @NonNull
    MedPhepMissionsMapper medPhepMissionsMapper;//任务信息表

    /**
     * 急救车 - 保存or更新
     */
    @Override
    public Boolean eccSaveOrUpdate(final MedPhepVehiclesEntity medPhepVehiclesEntity) {

        Assert.isTrue(Objects.nonNull(medPhepVehiclesEntity) && StringUtils.isNotBlank(medPhepVehiclesEntity.getLicensePlate()), "车牌号不能为: NULL");
        final boolean result;
        final Long integer = this.medPhepVehiclesMapper.selectCount(Wrappers.<MedPhepVehiclesEntity>lambdaQuery().select(MedPhepVehiclesEntity::getLicensePlate).eq(MedPhepVehiclesEntity::getLicensePlate, medPhepVehiclesEntity.getLicensePlate()));
        if (null != integer && integer > 0 && null == medPhepVehiclesEntity.getId()) {
            throw new BusinessException("车牌号已存在,请勿重复添加!");
        }
        final LocalDateTime now = LocalDateTime.now();
        if (StringUtils.isNotBlank(medPhepVehiclesEntity.getId())) {
            medPhepVehiclesEntity.setUpdateTime(now);
            result = this.medPhepVehiclesMapper.updateById(medPhepVehiclesEntity) > 0;
        } else {
            medPhepVehiclesEntity.setCreateTime(now);
            medPhepVehiclesEntity.setUpdateTime(now);
            result = this.medPhepVehiclesMapper.insert(medPhepVehiclesEntity) > 0;
        }
        return result;
    }

    /**
     * 急救车 - 删除
     */
    @Override
    public Boolean eccDelete(final List<String> ids) {

        return this.medPhepVehiclesMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 急救车 - 分页列表
     */
    @Override
    public IPage<EccPageListRo> eccPageList(final EccPageListPo eccPageListPo) {

        if (0 == eccPageListPo.getSize()) {
            eccPageListPo.setSize(this.emergencyCarMapper.getTotal());
        }
        final Page<EccPageListRo> page = new Page<>(eccPageListPo.getCurrent(), eccPageListPo.getSize());
        final IPage<EccPageListRo> result = this.emergencyCarMapper.eccPageList(page, eccPageListPo);
        //处理状态
        Optional.ofNullable(result.getRecords()).filter(list -> list.size() > 0).ifPresent(list -> {
            //筛选未完成任务的车辆
            final List<MedPhepMissionsEntity> medPhepMissionsEntities = this.medPhepMissionsMapper.selectList(Wrappers.<MedPhepMissionsEntity>lambdaQuery().select(MedPhepMissionsEntity::getLicensePlate).ne(MedPhepMissionsEntity::getStatus, "06").groupBy(MedPhepMissionsEntity::getLicensePlate));
            if (!CollectionUtils.isEmpty(medPhepMissionsEntities)) {
                final List<String> collect = medPhepMissionsEntities.stream().map(MedPhepMissionsEntity::getLicensePlate).collect(Collectors.toList());
                list.forEach(dto -> {
                    if (collect.contains(dto.getLicensePlate())) {
                        dto.setStatus(1);
                    }
                });
            }
        });
        return result;
    }
}
