package com.mdsd.cloud.controller.pc.drug;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListPo;
import com.mdsd.cloud.controller.pc.drug.dto.DrugPageListRo;
import com.mdsd.cloud.controller.pc.drug.dto.SpecialistCategoryPo;
import com.mdsd.cloud.controller.pc.drug.service.DrugService;
import com.mdsd.cloud.entity.MedPhepDrugEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 药品
 *
 * @author WangYunwei [2022-06-02]
 */
@RestController
@RequestMapping(value = "/pc/DrugController/v1")
@Api(value = "WEB - 药品", tags = "PC - Drug", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugController {

    DrugService drugService;

    public DrugController(final DrugService drugService) {

        this.drugService = drugService;
    }

    /**
     * 药品 - 保存or更新
     */
    @ApiOperation(value = "药品 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/drugSaveOrUpdate")
    public ResponseDto<Boolean> drugSaveOrUpdate(@RequestBody final MedPhepDrugEntity medPhepDrugEntity) {

        return ResponseUtil.wrapSuccess(this.drugService.drugSaveOrUpdate(medPhepDrugEntity));
    }

    /**
     * 药品 - 删除
     *
     * @param ids 药品ID集合
     */
    @ApiOperation(value = "药品 - 删除")
    @PostMapping(name = "删除", value = "/drugDelete")
    public ResponseDto<Boolean> drugDelete(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.drugService.drugDelete(ids));
    }

    /**
     * 药品 - 分页列表
     */
    @ApiOperation(value = "药品 - 分页列表")
    @PostMapping(name = "分页列表", value = "/drugPageList")
    public ResponseDto<IPage<DrugPageListRo>> drugPageList(@RequestBody final DrugPageListPo drugPageListPo) {

        return ResponseUtil.wrapSuccess(this.drugService.drugPageList(drugPageListPo));
    }

    /**
     * 药品 - 专科对应类别
     */
    @ApiOperation(value = "药品 - 专科对应类别")
    @PostMapping(name = "专科对应类别", value = "/specialistCategory")
    public ResponseDto<?> specialistCategory(@RequestBody final SpecialistCategoryPo specialistCategoryPo) {

        return ResponseUtil.wrapSuccess(this.drugService.specialistCategory(specialistCategoryPo));
    }

    /**
     * 药品 - 分组列表
     */
    @ApiOperation(value = "药品 - 分组列表")
    @GetMapping(name = "分组列表", value = "/drugGroupByList")
    public ResponseDto<Map<String, List<MedPhepDrugEntity>>> drugGroupByList() {

        return ResponseUtil.wrapSuccess(this.drugService.drugGroupByList());
    }
}
