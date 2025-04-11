package com.mdsd.cloud.controller.pc.drug.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListPo;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListRo;
import com.mdsd.cloud.controller.pc.drug.dto.SpecialistCategoryPo;
import com.mdsd.cloud.entity.MedPhepDrugEntity;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-06-02]
 */
public interface DrugService {

    /**
     * 药品 - 保存or更新
     *
     * @param medPhepDrugEntity
     * @return Boolean
     */
    Boolean drugSaveOrUpdate(MedPhepDrugEntity medPhepDrugEntity);

    /**
     * 药品 - 删除
     *
     * @param ids
     * @return Boolean
     */
    Boolean drugDelete(List<String> ids);

    /**
     * 药品 - 分页列表
     *
     * @param drugPageListPo
     * @return IPage<DrugPageListRo>
     */
    IPage<DrugPageListRo> drugPageList(DrugPageListPo drugPageListPo);

    /**
     * 药品 - 专科对应类别
     *
     * @param specialistCategoryPo
     * @return Object
     */
    Object specialistCategory(SpecialistCategoryPo specialistCategoryPo);

    /**
     * 药品 - 分组列表
     *
     * @return Map<String, List < MedPhepDrugEntity>>
     */
    Map<String, List<MedPhepDrugEntity>> drugGroupByList();
}
