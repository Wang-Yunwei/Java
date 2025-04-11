package com.mdsd.cloud.controller.body;

import com.mdsd.cloud.controller.body.dto.GenListParamDto;
import com.mdsd.cloud.controller.body.service.GeneralInspectionsService;
import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体格检查
 *
 * @author WangYunwei [2021-11-11]
 */
@RestController
@RequestMapping("/GeneralInspectionsController/v1")
@Api(tags = "体格检查", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeneralInspectionsController {

    GeneralInspectionsService generalInspectionsService;

    public GeneralInspectionsController(final GeneralInspectionsService generalInspectionsService) {

        this.generalInspectionsService = generalInspectionsService;
    }

    /**
     * 体格检查 - 新增or更新
     */
    @ApiOperation(value = "体格检查 - 新增or更新")
    @PostMapping(name = "新增or更新", value = "/genSaveOrUpdate")
    public ResponseDto<Boolean> genSaveOrUpdate(@RequestBody final MedPhepGeneralInspectionsEntity medPhepGeneralInspectionsEntity) {

        return ResponseUtil.wrapSuccess(this.generalInspectionsService.genSaveOrUpdate(medPhepGeneralInspectionsEntity));
    }

    /**
     * 体格检查 - 删除
     *
     * @param ids 体格检查ID集合
     */
    @ApiOperation(value = "体格检查 - 删除")
    @PostMapping(name = "删除", value = "/genRemove")
    public ResponseDto<Boolean> genRemove(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.generalInspectionsService.genRemove(ids));
    }

    /**
     * 体格检查 - 详情
     *
     * @param id 体格检查ID
     */
    @ApiOperation(value = "体格检查 - 详情")
    @GetMapping(name = "详情", value = "/genDetails/{id}")
    public ResponseDto<MedPhepGeneralInspectionsEntity> genDetails(@PathVariable final String id) {

        return ResponseUtil.wrapSuccess(this.generalInspectionsService.genDetails(id));
    }

    /**
     * 体格检查 - 列表
     */
    @ApiOperation(value = "体格检查 - 列表")
    @PostMapping(name = "列表", value = "/genList")
    public ResponseDto<List<MedPhepGeneralInspectionsEntity>> genList(@RequestBody final GenListParamDto genListParamDto) {

        return ResponseUtil.wrapSuccess(this.generalInspectionsService.genList(genListParamDto));
    }

}
