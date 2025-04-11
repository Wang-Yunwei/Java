package com.mdsd.cloud.controller.dictionary;

import com.mdsd.cloud.controller.dictionary.dto.DdcListOrDetailsPo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.dto.GetDictCodePo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictCodeRo;
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
import java.util.Map;

/**
 * 数据字典
 *
 * @author WangYunwei [2022-04-07]
 */
@RestController
@RequestMapping("/DataDictionaryController/v1")
@Api(tags = "数据字典", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataDictionaryController {

    DataDictionaryService dataDictionaryService;

    public DataDictionaryController(final DataDictionaryService dataDictionaryService) {

        this.dataDictionaryService = dataDictionaryService;
    }

    /**
     * 数据字典 - 保存or更新
     */
    @ApiOperation(value = "数据字典 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/ddcSaveOrUpdate")
    public ResponseDto<Boolean> ddcSaveOrUpdate(@RequestBody final MedDictionaryEntity medDictionaryEntity) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcSaveOrUpdate(medDictionaryEntity));
    }

    /**
     * 数据字典 - 删除
     *
     * @param ids 字典ID集合
     */
    @ApiOperation(value = "数据字典 - 删除")
    @PostMapping(name = "删除", value = "/ddcDelete")
    public ResponseDto<Boolean> ddcDelete(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcDelete(ids));
    }

    /**
     * 数据字典 - 列表or详情(注: 主键ID有值,则默认查详情)
     */
    @ApiOperation(value = "数据字典 - 列表or详情(注: 主键ID有值,则默认查详情)")
    @PostMapping(name = "列表or详情", value = "/ddcListOrDetails")
    public ResponseDto<?> ddcListOrDetails(@RequestBody final DdcListOrDetailsPo ddcListOrDetailsPo) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcListOrDetails(ddcListOrDetailsPo));
    }

    /**
     * 数据字典 - 批量添加
     *
     * @param file Excel文件
     */
    @ApiOperation(value = "数据字典 - 批量添加", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(name = "批量添加", value = "/ddcAddBatch")
    public ResponseDto<Boolean> ddcAddBatch(@RequestParam(value = "file") final MultipartFile file) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.ddcAddBatch(file));
    }

    /**
     * 数据字典 - 根据Codes获取枚举值
     */
    @ApiOperation(value = "数据字典 - 根据Codes获取枚举值")
    @PostMapping(name = "根据Codes获取枚举值", value = "/getDictByCodes")
    public ResponseDto<Map<String, List<MedDictionaryEntity>>> getDictByCodes(@RequestBody final GetDictByCodesPao getDictByCodesPao) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.getDictByCode(getDictByCodesPao));
    }

    /**
     * 数据字典 - 获取字典编码
     */
    @ApiOperation(value = "数据字典 - 获取字典编码")
    @PostMapping(name = "获取字典编码", value = "/getDictCode")
    public ResponseDto<List<GetDictCodeRo>> getDictCode(@RequestBody final GetDictCodePo getDictCodePo) {

        return ResponseUtil.wrapSuccess(this.dataDictionaryService.getDictCode(getDictCodePo));
    }
}
