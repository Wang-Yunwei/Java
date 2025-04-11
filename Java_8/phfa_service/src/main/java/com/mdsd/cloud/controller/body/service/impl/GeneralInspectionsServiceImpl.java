package com.mdsd.cloud.controller.body.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.body.dto.GenListParamDto;
import com.mdsd.cloud.controller.body.service.GeneralInspectionsService;
import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import com.mdsd.cloud.mapper.MedPhepGeneralInspectionsMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author WangYunwei [2021-11-12]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GeneralInspectionsServiceImpl implements GeneralInspectionsService {

    @NonNull
    MedPhepGeneralInspectionsMapper medPhepGeneralInspectionsMapper;

    /**
     * 体格检查 - 新增or更新
     */
    @Override
    public boolean genSaveOrUpdate(final MedPhepGeneralInspectionsEntity medPhepGeneralInspectionsEntity) {

        boolean result = false;
        final MedPhepGeneralInspectionsEntity entity = this.medPhepGeneralInspectionsMapper.selectById(medPhepGeneralInspectionsEntity.getId());
        if (!ObjectUtils.isEmpty(entity)) {
            //数据存在,执行更新
            result = this.medPhepGeneralInspectionsMapper.updateById(medPhepGeneralInspectionsEntity) > 0;
        } else {
            //数据不存在,执行新增
            result = this.medPhepGeneralInspectionsMapper.insert(medPhepGeneralInspectionsEntity) > 0;
        }
        return result;
    }

    /**
     * 体格检查 - 删除
     */
    @Override
    public boolean genRemove(final List<String> ids) {

        int result = 0;
        if (!CollectionUtils.isEmpty(ids)) {
            result = this.medPhepGeneralInspectionsMapper.deleteBatchIds(ids);
        }
        return result > 0;
    }

    /**
     * 体格检查 - 详情
     */
    @Override
    public MedPhepGeneralInspectionsEntity genDetails(final String id) {

        return this.medPhepGeneralInspectionsMapper.selectById(id);
    }

    /**
     * 体格检查 - 列表
     */
    @Override
    public List<MedPhepGeneralInspectionsEntity> genList(final GenListParamDto genListParamDto) {

        return this.medPhepGeneralInspectionsMapper.selectList(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery().eq(MedPhepGeneralInspectionsEntity::getPatientId, genListParamDto.getPatientId()).isNull(MedPhepGeneralInspectionsEntity::getIsHand).orderByAsc(MedPhepGeneralInspectionsEntity::getCreateTime));
    }
}
