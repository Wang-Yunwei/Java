package com.mdsd.cloud.controller.pc.drug.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListPo;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListRo;
import com.mdsd.cloud.controller.pc.drug.dto.SpecialistCategoryPo;
import com.mdsd.cloud.controller.pc.drug.mapper.MedPhepDrugMapper;
import com.mdsd.cloud.controller.pc.drug.service.DrugService;
import com.mdsd.cloud.entity.MedPhepDrugEntity;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-06-02]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DrugServiceImpl implements DrugService {

    @NonNull
    MedPhepDrugMapper medPhepDrugMapper;

    /**
     * 药品 - 保存or更新
     */
    @Override
    public Boolean drugSaveOrUpdate(final MedPhepDrugEntity medPhepDrugEntity) {

        Assert.notNull(medPhepDrugEntity, "入参为: NULL");
        if (StringUtils.isNotBlank(medPhepDrugEntity.getId())) {
            medPhepDrugEntity.setUpdateDate(LocalDateTime.now());
            return this.medPhepDrugMapper.updateById(medPhepDrugEntity) > 0;
        } else {
            return this.medPhepDrugMapper.insert(medPhepDrugEntity) > 0;
        }
    }

    /**
     * 药品 - 删除
     */
    @Override
    public Boolean drugDelete(final List<String> ids) {

        Assert.noNullElements(ids, "入参为: NULL");
        return this.medPhepDrugMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 药品 - 分页列表
     */
    @Override
    public IPage<DrugPageListRo> drugPageList(final DrugPageListPo drugPageListPo) {

        //判断是否需分页
        if (null == drugPageListPo || null == drugPageListPo.getCurrent() || null == drugPageListPo.getSize()) {
            final Long integer = this.medPhepDrugMapper.selectCount(Wrappers.lambdaQuery());
            drugPageListPo.setSize(Long.valueOf(integer));
            drugPageListPo.setCurrent(Long.valueOf(0));
        }
        final Page<DrugPageListRo> page = new Page<>(drugPageListPo.getCurrent(), drugPageListPo.getSize());
        //处理需要模糊查询字段
        drugPageListPo.setCode(HandleString.repString(drugPageListPo.getCode()));
        drugPageListPo.setName(HandleString.repString(drugPageListPo.getName()));
        return this.medPhepDrugMapper.drugPageList(page, drugPageListPo);
    }

    /**
     * 药品 - 专科对应类别
     */
    @Override
    public Object specialistCategory(final SpecialistCategoryPo specialistCategoryPo) {

        if (Objects.nonNull(specialistCategoryPo) && null != specialistCategoryPo.getOperation()) {
            final MedPhepDrugEntity medPhepDrugEntity = this.medPhepDrugMapper.selectById(specialistCategoryPo.getId());
            switch (specialistCategoryPo.getOperation()) {
                case 0://保存or更新
                    if (Objects.nonNull(medPhepDrugEntity) && StringUtils.isNotBlank(medPhepDrugEntity.getSpecialistCategory())) {
                        //存在对应类别
                        final List<Map> maps = JSON.parseArray(medPhepDrugEntity.getSpecialistCategory(), Map.class);
                        int i = 0;
                        for (final Map map : maps) {
                            if (map.get("name").equals(specialistCategoryPo.getName())) {
                                i++;
                                map.put("value", specialistCategoryPo.getValue());
                            }
                        }
                        if (i == 0) {
                            maps.add(
                                    new HashMap<String, String>() {{
                                        this.put("name", specialistCategoryPo.getName());
                                        this.put("value", specialistCategoryPo.getValue());
                                    }});

                        }
                        return this.medPhepDrugMapper.updateById(new MedPhepDrugEntity().setId(specialistCategoryPo.getId()).setSpecialistCategory(JSON.toJSONString(maps))) > 0;
                    } else {
                        //不存在对应类别
                        final List<Map<String, String>> list = Lists.newArrayList(new HashMap<String, String>() {{
                            this.put("name", specialistCategoryPo.getName());
                            this.put("value", specialistCategoryPo.getValue());
                        }});
                        return this.medPhepDrugMapper.updateById(new MedPhepDrugEntity().setId(specialistCategoryPo.getId()).setSpecialistCategory(JSON.toJSONString(list))) > 0;
                    }
                case 1://删除
                    if (Objects.nonNull(medPhepDrugEntity) && StringUtils.isNotBlank(medPhepDrugEntity.getSpecialistCategory())) {
                        //存在对应类别
                        final List<Map> maps = JSON.parseArray(medPhepDrugEntity.getSpecialistCategory(), Map.class);
                        final List<Map> list = maps.stream().filter(map -> !map.get("name").equals(specialistCategoryPo.getName())).collect(Collectors.toList());
                        return this.medPhepDrugMapper.updateById(new MedPhepDrugEntity().setId(specialistCategoryPo.getId()).setSpecialistCategory(JSON.toJSONString(list))) > 0;
                    }
                    break;
                case 2://列表
                    final List<Map<String, String>> result = Lists.newArrayList();
                    if (Objects.nonNull(medPhepDrugEntity) && StringUtils.isNotBlank(medPhepDrugEntity.getSpecialistCategory())) {
                        return JSON.parseArray(medPhepDrugEntity.getSpecialistCategory(), Map.class);
                    }
                    return result;
            }
        }
        return null;
    }

    /**
     * 药品 - 分组列表
     */
    @Override
    public Map<String, List<MedPhepDrugEntity>> drugGroupByList() {

        final Map<String, List<MedPhepDrugEntity>> result = Maps.newHashMap();
        //查询所有用药信息
        final List<MedPhepDrugEntity> medPhepDrugEntities = this.medPhepDrugMapper.selectList(Wrappers.<MedPhepDrugEntity>lambdaQuery().eq(MedPhepDrugEntity::getDeleteFlag, 0));
        Optional.ofNullable(medPhepDrugEntities).filter(list -> list.size() > 0).ifPresent(list -> {
            final Map<String, List<MedPhepDrugEntity>> map = list.stream().collect(Collectors.groupingBy(MedPhepDrugEntity::getCategory));
            result.putAll(map);
        });
        return result;
    }
}
