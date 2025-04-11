package com.mdsd.cloud.controller.dictionary.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.controller.dictionary.dto.*;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.function.MedDictionaryFunction;
import com.mdsd.cloud.controller.dictionary.mapper.MedDictionaryMapper;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.dictionary.utils.DdcExcelListener;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-04-07]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @NonNull
    MedDictionaryMapper medDictionaryMapper;

    @NonNull
    MedDictionaryFunction medDictionaryFunction;

    final String APP_ID = "PHFA";

    /**
     * 数据字典 - 保存or更新
     */
    @Override
    public Boolean ddcSaveOrUpdate(final MedDictionaryEntity medDictionaryEntity) {

        Assert.notNull(medDictionaryEntity, "入参对象为: NULL");
        if (Objects.nonNull(medDictionaryEntity.getId())) {
            //执行更新
            return this.medDictionaryFunction.updateById(medDictionaryEntity);
        } else {
            //执行新增
            return this.medDictionaryFunction.save(medDictionaryEntity);
        }
    }

    /**
     * 数据字典 - 删除
     */
    @Override
    public Boolean ddcDelete(final List<String> ids) {

        Assert.noNullElements(ids, "入参对象为: NULL");
        return this.medDictionaryFunction.removeByIds(ids);
    }

    /**
     * 数据字典 - 列表or详情(注: 主键ID有值,则默认查详情)
     */
    @Override
    public Object ddcListOrDetails(final DdcListOrDetailsPo ddcListOrDetailsPo) {

        //断言
        Assert.notNull(ddcListOrDetailsPo, "入参对象为: NULL");
        if (null != ddcListOrDetailsPo.getDictId()) {
            //详情
            return this.medDictionaryFunction.getById(ddcListOrDetailsPo.getDictId());
        } else {
            if (null != ddcListOrDetailsPo.getCurrent() && null != ddcListOrDetailsPo.getSize()) {
                //处理模糊查询字段
                ddcListOrDetailsPo.setCode(HandleString.repString(ddcListOrDetailsPo.getCode()));
                ddcListOrDetailsPo.setName(HandleString.repString(ddcListOrDetailsPo.getName()));
                //分页列表
                final Page<MedDictionaryEntity> page = new Page<>(ddcListOrDetailsPo.getCurrent(), ddcListOrDetailsPo.getSize());
                return this.medDictionaryMapper.ddcPageList(page, ddcListOrDetailsPo);
            } else {
                return this.medDictionaryMapper.selectList(Wrappers.<MedDictionaryEntity>lambdaQuery().select(MedDictionaryEntity::getId, MedDictionaryEntity::getItemcode, MedDictionaryEntity::getItemname, MedDictionaryEntity::getExtend, MedDictionaryEntity::getDescribe, MedDictionaryEntity::getStatus).eq(MedDictionaryEntity::getAppid, this.APP_ID).eq(MedDictionaryEntity::getCode, ddcListOrDetailsPo.getCode()).orderByAsc(MedDictionaryEntity::getItemcode));
            }
        }
    }

    /**
     * 数据字典 - 批量添加
     */
    @Override
    public Boolean ddcAddBatch(final MultipartFile file) {

        try {
            EasyExcelFactory.read(file.getInputStream(), DdcAddBatchDto.class, new DdcExcelListener(this.medDictionaryFunction)).sheet().doRead();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

    /**
     * 数据字典 - 根据Codes获取枚举值
     */
    @Override
    @Cacheable(value = "dictionary",key="#root.method.name",cacheManager = "dicCacheManager")
    public Map<String, List<MedDictionaryEntity>> getDictByCode(final GetDictByCodesPao getDictByCodesPao) {

        //构建查询条件
        final Map<String, List<MedDictionaryEntity>> result = Maps.newHashMap();
        final QueryWrapper<MedDictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("HOSPITAL_ID", "CODE", "NAME", "ITEMCODE", "ITEMNAME", "DESCRIBE", "EXTEND");
        queryWrapper.eq("APPID", this.APP_ID).eq("STATUS", 1);
        if (null != getDictByCodesPao && StringUtils.isNotBlank(getDictByCodesPao.getHospitalId())) {
            queryWrapper.eq("HOSPITAL_ID", getDictByCodesPao.getHospitalId());
        }
        if (null != getDictByCodesPao && CollectionUtils.isNotEmpty(getDictByCodesPao.getCode())) {
            queryWrapper.in("CODE", getDictByCodesPao.getCode());
        }
        //获得原始数据
        final List<MedDictionaryEntity> medDictionaryEntities = this.medDictionaryMapper.selectList(queryWrapper);
        //开始数据组装
        Optional.ofNullable(medDictionaryEntities).filter(list -> list.size() > 0).ifPresent(list -> {
            if (null != getDictByCodesPao && CollectionUtils.isNotEmpty(getDictByCodesPao.getCode()) && getDictByCodesPao.getCode().size() == 1 && getDictByCodesPao.getCode().contains(DictionaryEnum.PHFA_0057.getValue())) {
                //特殊处理初步诊断
                final Set<String> collect = list.stream().map(MedDictionaryEntity::getDescribe).collect(Collectors.toSet());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.forEach(str -> {
                        final List<MedDictionaryEntity> entList = list.stream().filter(dto -> str.equals(dto.getDescribe())).collect(Collectors.toList());
                        result.put(str, entList);
                    });
                }
            } else {
                //处理key集合
                final Set<String> collect = list.stream().map(MedDictionaryEntity::getCode).collect(Collectors.toSet());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.forEach(str -> {
                        final List<MedDictionaryEntity> entList = list.stream().filter(dto -> str.equals(dto.getCode())).collect(Collectors.toList());
                        result.put(str, entList);
                    });
                }
            }
        });
        return result;
    }

    /**
     * 数据字典 - 根据Codes获取枚举值_重载
     */
    @Override
    public Map<String, List<MedDictionaryEntity>> getDictByCode(final DictionaryEnum... enums) {

        final List<String> list = Lists.newArrayList();
        final List<DictionaryEnum> dictionaryEnums = Arrays.asList(enums);
        dictionaryEnums.forEach(enu -> {
            list.add(enu.getValue());
        });
        return this.getDictByCode(new GetDictByCodesPao().setCode(list));
    }

    /**
     * 数据字典 - 获取字典编码
     */
    @Override
    public List<GetDictCodeRo> getDictCode(final GetDictCodePo getDictCodePo) {

        //组装查询条件
        LambdaQueryWrapper<MedDictionaryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(MedDictionaryEntity::getCode,MedDictionaryEntity::getName).eq(MedDictionaryEntity::getAppid,this.APP_ID).groupBy(MedDictionaryEntity::getCode,MedDictionaryEntity::getName).orderByAsc(MedDictionaryEntity::getCode);
        if (StringUtils.isNotBlank(getDictCodePo.getHospitalId())) {
            lambdaQueryWrapper.like(MedDictionaryEntity::getHospitalId, getDictCodePo.getHospitalId());
        }
        if (StringUtils.isNotBlank(getDictCodePo.getDictCode())) {
            lambdaQueryWrapper.like(MedDictionaryEntity::getCode, HandleString.repString(getDictCodePo.getDictCode()));
        }
        if (StringUtils.isNotBlank(getDictCodePo.getDictName())) {
            lambdaQueryWrapper.like(MedDictionaryEntity::getName, HandleString.repString(getDictCodePo.getDictName()));
        }
        final List<MedDictionaryEntity> medDictionaryEntities = this.medDictionaryMapper.selectList(lambdaQueryWrapper);
        //处理返回数据
        List<GetDictCodeRo> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(medDictionaryEntities)) {
            result = medDictionaryEntities.stream().map(ent ->
                new GetDictCodeRo().setDictCode(ent.getCode()).setTitle(String.format("%s(%s)", ent.getName(), ent.getCode()))
            ).collect(Collectors.toList());
        }
        return result;
    }
}
