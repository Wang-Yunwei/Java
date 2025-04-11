package com.mdsd.cloud.controller.dictionary.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.mdsd.cloud.controller.dictionary.dto.*;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.entity.MedRatingscaleEntity;
import com.mdsd.cloud.controller.dictionary.entity.MedRatingscaleItemEntity;
import com.mdsd.cloud.controller.dictionary.function.MedDictionaryFunction;
import com.mdsd.cloud.controller.dictionary.mapper.MedDictionaryMapper;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.dictionary.utils.DdcExcelListener;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @NonNull
    MedDictionaryMapper medDictionaryMapper;

    @NonNull
    MedDictionaryFunction medDictionaryFunction;

    final String APP_ID = "PIAT";

    /**
     * 数据字典 - 保存or更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ddcSaveOrUpdate(final MedDictionaryEntity medDictionaryEntity) {

        final Boolean result;
        Assert.notNull(medDictionaryEntity, "入参对象为: NULL");
        if (Objects.nonNull(medDictionaryEntity.getId())) {
            //执行更新
            result = this.medDictionaryFunction.updateById(medDictionaryEntity);
        } else {
            //执行新增
            if (null == medDictionaryEntity.getName() && null != medDictionaryEntity.getCode()) {
                final MedDictionaryEntity medDictionaryEntity1 = this.medDictionaryMapper.selectOne(Wrappers.<MedDictionaryEntity>lambdaQuery().eq(MedDictionaryEntity::getCode, medDictionaryEntity.getCode()).apply("rownum=1"));
                medDictionaryEntity.setName(medDictionaryEntity1.getName());
                medDictionaryEntity.setAppid(medDictionaryEntity1.getAppid());
            }
            result = this.medDictionaryFunction.save(medDictionaryEntity);
        }
        return result;
    }

    /**
     * 数据字典 - 删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ddcDelete(final List<String> ids) {

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
                return this.medDictionaryMapper.selectList(Wrappers.<MedDictionaryEntity>lambdaQuery().select(MedDictionaryEntity::getId, MedDictionaryEntity::getItemcode, MedDictionaryEntity::getItemname, MedDictionaryEntity::getExtend, MedDictionaryEntity::getDescribe, MedDictionaryEntity::getStatus).eq(MedDictionaryEntity::getAppid, this.APP_ID).eq(MedDictionaryEntity::getCode, ddcListOrDetailsPo.getCode()));
            }
        }
    }

    /**
     * 数据字典 - 批量添加
     */
    @Override
    public void ddcAddBatch(final MultipartFile file) {

        try {
            EasyExcelFactory.read(file.getInputStream(), DdcAddBatchDto.class, new DdcExcelListener(this.medDictionaryFunction)).sheet().doRead();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据字典 - 根据Codes获取枚举值
     */
    @Override
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
            //处理key集合
            final Set<String> collect = list.stream().map(MedDictionaryEntity::getCode).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(collect)) {
                collect.forEach(str -> {
                    final List<MedDictionaryEntity> entList = list.stream().filter(dto -> str.equals(dto.getCode())).collect(Collectors.toList());
                    result.put(str, entList);
                });
            }
        });
        return result;
    }

    /**
     * 数据字典 - 根据Codes获取枚举值_重载
     *
     * @param codes
     * @return
     */
    @Override
    public Map<String, List<MedDictionaryEntity>> getDictByCode(final String... codes) {

        return this.getDictByCode(new GetDictByCodesPao().setCode(Lists.newArrayList(codes)));
    }

    /**
     * 数据字典 - 获取字典编码
     */
    @Override
    public List<GetDictCodeRo> getDictCode(final GetDictCodePo getDictCodePo) {

        Assert.notNull(getDictCodePo, "入参对象为: NULL");
        //组装查询条件
        final QueryWrapper<MedDictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT CODE", "NAME").eq("APPID", this.APP_ID).orderByAsc("CODE");
        if (StringUtils.isNotBlank(getDictCodePo.getHospitalId())) {
            queryWrapper.like("HOSPITAL_ID", getDictCodePo.getHospitalId());
        }
        if (StringUtils.isNotBlank(getDictCodePo.getDictCode())) {
            queryWrapper.like("CODE", HandleString.repString(getDictCodePo.getDictCode()));
        }
        if (StringUtils.isNotBlank(getDictCodePo.getDictName())) {
            queryWrapper.like("NAME", HandleString.repString(getDictCodePo.getDictName()));
        }
        final List<MedDictionaryEntity> medDictionaryEntities = this.medDictionaryMapper.selectList(queryWrapper);
        //处理返回数据
        List<GetDictCodeRo> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(medDictionaryEntities)) {
            result = medDictionaryEntities.stream().map(ent -> {
                final GetDictCodeRo getDictCodeRo = new GetDictCodeRo().setDictCode(ent.getCode()).setTitle(String.format("%s(%s)", ent.getCode(), ent.getName()));
                return getDictCodeRo;
            }).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 数据字典 - 评分模板
     */
    @Override
    public Map<String, List<MedRatingscaleItemEntity>> ratingsTemplate() {

        final Map<String, List<MedRatingscaleItemEntity>> result = Maps.newHashMap();
        final List<MedRatingscaleEntity> piat = new MedRatingscaleEntity().selectList(Wrappers.<MedRatingscaleEntity>lambdaQuery().select(MedRatingscaleEntity::getId, MedRatingscaleEntity::getCode).eq(MedRatingscaleEntity::getAppid, this.APP_ID));
        Optional.ofNullable(piat).filter(list -> list.size() > 0).ifPresent(list -> {
            final List<String> collect = list.stream().map(MedRatingscaleEntity::getId).collect(Collectors.toList());
            //查询评分明细表
            final List<MedRatingscaleItemEntity> medRatingscaleItemEntities = new MedRatingscaleItemEntity().selectList(Wrappers.<MedRatingscaleItemEntity>lambdaQuery().in(MedRatingscaleItemEntity::getRatingId, collect));
            if (CollectionUtils.isNotEmpty(medRatingscaleItemEntities)) {
                list.forEach(ent -> {
                    result.put(ent.getCode(), medRatingscaleItemEntities.stream().filter(mri -> mri.getRatingId().equals(ent.getId())).collect(Collectors.toList()));
                });
            }
        });
        return result;
    }
}
