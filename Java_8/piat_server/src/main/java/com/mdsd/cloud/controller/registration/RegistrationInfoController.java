package com.mdsd.cloud.controller.registration;

import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.registration.dto.*;
import com.mdsd.cloud.controller.registration.service.RegistrationInfoService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import com.mdsd.cloud.utils.PrimaryKeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author WangYunwei [2021-09-13]
 */
@RestController
@RequestMapping("/RegistrationInfoController/v1")
@Api(value = "WEB - RegistrationInfoController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "个人登记")
public class RegistrationInfoController {

    RegistrationInfoService registrationInfoService;

    PrimaryKeyUtil primaryKeyUtil;

    public RegistrationInfoController(final RegistrationInfoService registrationInfoService, final PrimaryKeyUtil primaryKeyUtil) {

        this.registrationInfoService = registrationInfoService;
        this.primaryKeyUtil = primaryKeyUtil;
    }

    @ApiOperation(value = "个人登记 - 获取流水号")
    @GetMapping(name = "获取流水号", path = "/getFlowingWaterId")
    public ResponseDto<?> getFlowingWaterId() {

        return ResponseUtil.wrapSuccess(this.primaryKeyUtil.getFlowingWaterId(CommonConstants.MED_CLINIC_CHECK_MASTER));
    }

    @ApiOperation(value = "个人登记 - 新增or更新")
    @PostMapping(name = "新增or更新", path = "/regSaveOrUpdate")
    public ResponseDto<?> regSaveOrUpdate(@RequestBody final RegSaveOrUpdateParamDto regSaveOrUpdateParamDto) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.regSaveOrUpdate(regSaveOrUpdateParamDto));
    }

    @ApiOperation(value = "个人登记 - 删除")
    @PostMapping(name = "删除", path = "/regRemove")
    public ResponseDto<?> regRemove(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.regRemove(ids));
    }

    @ApiOperation(value = "个人登记 - 详情")
    @GetMapping(name = "详情", path = "/regDetails/{regId}")
    public ResponseDto<?> regDetails(@PathVariable final String regId) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.regDetails(regId));
    }

    @ApiOperation(value = "个人登记 - 列表")
    @PostMapping(name = "列表", path = "/regList")
    public ResponseDto<?> regList(@RequestBody final RegListsParamDto regListsParamDto) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.regList(regListsParamDto));
    }

    @ApiOperation(value = "个人登记 - 列表_导出")
    @PostMapping(name = "导出", path = "/regExport")
    public void regExport(@RequestBody final RegListsParamDto regListsParamDto, final HttpServletResponse response) {

        this.registrationInfoService.regExport(regListsParamDto, response);
    }

    @ApiOperation(value = "个人登记 - 删除_患者评分历史记录")
    @PostMapping(name = "患者评分历史记录", path = "/scoRemove")
    public ResponseDto<?> scoRemove(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.scoRemove(ids));
    }

    @ApiOperation(value = "个人登记 - 列表_患者评分历史记录")
    @GetMapping(name = "患者评分历史记录", path = "/getScoreList/{regId}/{vulnusType}")

    public ResponseDto<?> getScoreList(@PathVariable final String regId, @PathVariable final String vulnusType) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.getScoreList(regId, vulnusType));
    }

    @ApiOperation(value = "个人登记 - 详情_患者评分历史记录")
    @GetMapping(name = "患者评分历史记录", path = "/getScoreDetails/{vdId}")
    public ResponseDto<?> getScoreDetails(@PathVariable final String vdId) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.getScoreDetails(vdId));
    }

    @ApiOperation(value = "个人登记 - 新增or更新_修改分诊分级原因")
    @PostMapping(name = "患者评分历史记录", path = "/modSaveOrUpdate")
    public ResponseDto<?> modSaveOrUpdate(@RequestBody final ModSaveOrUpdateParamDto modSaveOrUpdateParamDto) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.modSaveOrUpdate(modSaveOrUpdateParamDto));
    }

    @ApiOperation(value = "个人登记 - 急诊明细_PDF")
    @PostMapping(name = "急诊明细", path = "/TriageDetailed")
    public void TriageDetailed(@RequestBody final TriageDetailedParamDto triageDetailedParamDto, final HttpServletResponse response) {

        triageDetailedParamDto.setResponse(response);
        this.registrationInfoService.TriageDetailed(triageDetailedParamDto);
    }

    @ApiOperation(value = "个人登记 - 分诊凭条_PDF")
    @PostMapping(name = "分诊凭条", path = "/triageSlip")
    public void triageSlip(@RequestBody final TriageSlipParamDto triageSlipParamDto,
                           final HttpServletResponse response) {

        triageSlipParamDto.setResponse(response);
        this.registrationInfoService.triageSlip(triageSlipParamDto);
    }

    @ApiOperation(value = "个人登记 - 腕带_列表or绑定or解除")
    @PostMapping(name = "腕带", path = "/getSpireLamellaList")
    public ResponseDto<?> spireLamella(@RequestBody final SpireLamellaParamDto spireLamellaParamDto) {

        return ResponseUtil.wrapSuccess(this.registrationInfoService.spireLamella(spireLamellaParamDto));
    }

    @ApiOperation(value = "个人登记 - 腕带_PDF")
    @PostMapping(name = "腕带", path = "/printWristbands")
    public void printWristbands(@RequestBody final PrintWristbandsParamDto printWristbandsParamDto, final HttpServletResponse response) {

        printWristbandsParamDto.setResponse(response);
        this.registrationInfoService.printWristbands(printWristbandsParamDto);
    }
}
