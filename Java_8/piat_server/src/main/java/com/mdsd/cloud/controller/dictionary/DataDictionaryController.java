package com.mdsd.cloud.controller.dictionary;

import com.mdsd.cloud.controller.dictionary.dto.DdcListOrDetailsPo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.dto.GetDictCodePo;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2022-04-07]
 */
@RestController
@RequestMapping("/DataDictionaryController/v1")
@Api(value = "WEB - 数据字典", tags = "DataDictionary", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataDictionaryController {

    DataDictionaryService dataDictionaryService;

    public DataDictionaryController(final DataDictionaryService dataDictionaryService) {

        this.dataDictionaryService = dataDictionaryService;
    }

    @ApiOperation(value = "数据字典 - 保存or更新")
    @PostMapping(name = "保存or更新", path = "/ddcSaveOrUpdate")
    public ResponseDto<?> ddcSaveOrUpdate(@RequestBody final MedDictionaryEntity medDictionaryEntity) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcSaveOrUpdate(medDictionaryEntity));
    }

    @ApiOperation(value = "数据字典 - 删除")
    @PostMapping(name = "删除", path = "/ddcDelete")
    public ResponseDto<?> ddcDelete(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcDelete(ids));
    }

    @ApiOperation(value = "数据字典 - 列表or详情(注: 主键ID有值,则默认查详情)")
    @PostMapping(name = "列表or详情", path = "/ddcListOrDetails")
    public ResponseDto<?> ddcListOrDetails(@RequestBody final DdcListOrDetailsPo ddcListOrDetailsPo) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcListOrDetails(ddcListOrDetailsPo));
    }

    @ApiOperation(value = "数据字典 - 批量添加", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(name = "批量添加", path = "/ddcAddBatch")
    public ResponseDto<?> ddcAddBatch(@RequestParam(value = "file") final MultipartFile file) {

        this.dataDictionaryService.ddcAddBatch(file);
        return ResponseUtil.wrapSuccess();
    }

    @ApiOperation(value = "数据字典 - 根据Codes获取枚举值")
    @PostMapping(name = "根据Codes获取枚举值", path = "/getDictByCodes")
    public ResponseDto<?> getDictByCodes(@RequestBody final GetDictByCodesPao getDictByCodesPao) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.getDictByCode(getDictByCodesPao));
    }

    @ApiOperation(value = "数据字典 - 获取字典编码")
    @PostMapping(name = "获取字典编码", path = "/getDictCode")
    public ResponseDto<?> getDictCode(@RequestBody final GetDictCodePo getDictCodePo) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.getDictCode(getDictCodePo));
    }

    @ApiOperation(value = "数据字典 - 评分模板")
    @GetMapping(name = "评分模板", path = "/ratingsTemplate")
    public ResponseDto<?> ratingsTemplate() {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ratingsTemplate());
    }
}
