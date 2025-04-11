package com.mdsd.cloud.controller.dictionary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.dictionary.dto.DdcListOrDetailsPo;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-04-07]
 */
@Mapper
public interface MedDictionaryMapper extends BaseMapper<MedDictionaryEntity> {

    /**
     * 数据字典 - 分页列表
     *
     * @param page
     * @param ddcPageListOrDetailPao
     * @return IPage<MedDictionaryEntity>
     */
    IPage<MedDictionaryEntity> ddcPageList(@Param("page") Page<MedDictionaryEntity> page, @Param("dto") DdcListOrDetailsPo ddcPageListOrDetailPao);
}
