package com.mdsd.cloud.controller.pc.emergencystation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListPo;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListRo;
import com.mdsd.cloud.controller.pc.emergencystation.mapper.MedPhepStationMapper;
import com.mdsd.cloud.controller.pc.emergencystation.service.EmergencyStationService;
import com.mdsd.cloud.entity.MedPhepStationEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author WangYunwei [2022-05-31]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EmergencyStationServiceImpl implements EmergencyStationService {

    @NonNull
    MedPhepStationMapper medPhepStationMapper;//急救站表

    /**
     * 急救站 - 保存or更新
     */
    @Override
    public Boolean escSaveOrUpdate(final MedPhepStationEntity medPhepStationEntity) {

        Assert.notNull(medPhepStationEntity, "入参为: NULL");
        if (Objects.nonNull(medPhepStationEntity) && StringUtils.isNotBlank(medPhepStationEntity.getId())) {
            medPhepStationEntity.setUpdateDate(LocalDateTime.now());
            return medPhepStationEntity.updateById();
        } else {
            return medPhepStationEntity.insert();
        }
    }

    /**
     * 急救站 - 删除
     */
    @Override
    public Boolean escDelete(final List<String> ids) {

        Assert.noNullElements(ids, "入参为: NULL");
        return this.medPhepStationMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 急救站 - 分页列表
     */
    @Override
    public IPage<EscPageListRo> escPageList(final EscPageListPo escPageListPo) {

        //判断是否需分页
        if (null == escPageListPo || null == escPageListPo.getCurrent() || null == escPageListPo.getSize()) {
            final Long integer = this.medPhepStationMapper.selectCount(Wrappers.lambdaQuery());
            escPageListPo.setSize(Long.valueOf(integer));
            escPageListPo.setCurrent(Long.valueOf(0));
        }
        final Page<EscPageListRo> page = new Page<>(escPageListPo.getCurrent(), escPageListPo.getSize());
        return this.medPhepStationMapper.escPageList(page, escPageListPo);
    }
}
