package com.mdsd.cloud.controller.pc.drug.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListPo;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListRo;
import com.mdsd.cloud.entity.MedPhepDrugEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-06-02]
 */
@Mapper
public interface MedPhepDrugMapper extends BaseMapper<MedPhepDrugEntity> {

    /**
     * 药品 - 分页列表
     *
     * @param page           分页
     * @param drugPageListPo 入参
     * @return IPage<DrugPageListRo>
     */
    IPage<DrugPageListRo> drugPageList(@Param("page") Page<DrugPageListRo> page, @Param("dto") DrugPageListPo drugPageListPo);
}
