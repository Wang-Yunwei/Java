package com.mdsd.cloud.controller.triage;

import com.mdsd.cloud.controller.triage.dto.TriListParamDto;
import com.mdsd.cloud.controller.triage.dto.TriSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.triage.service.TriageHelpService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangYunwei [2021-10-25]
 */
@RestController
@RequestMapping("/TriageHelpController/v1")
@Api(value = "WEB - TriageHelpController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "体征辅助")
public class TriageHelpController {

    TriageHelpService triageHelpService;

    public TriageHelpController(final TriageHelpService triageHelpService) {

        this.triageHelpService = triageHelpService;
    }

    @ApiOperation(value = "体征辅助 - 新增or更新")
    @PostMapping(name = "新增or更新", path = "/triSaveOrUpdate")
    public ResponseDto<?> triSaveOrUpdate(@RequestBody final TriSaveOrUpdateParamDto triSaveOrUpdateParamDto) {

        return ResponseUtil.wrapSuccess(this.triageHelpService.triSaveOrUpdate(triSaveOrUpdateParamDto));
    }

    @ApiOperation(value = "体征辅助 - 删除")
    @PostMapping(name = "删除", path = "/triRemove")
    public ResponseDto<?> triRemove(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.triageHelpService.triRemove(ids));
    }

    @ApiOperation(value = "体征辅助 - 详情")
    @GetMapping(name = "详情", path = "/triDetails/{id}")
    public ResponseDto<?> triDetails(@PathVariable final String id) {

        return ResponseUtil.wrapSuccess(this.triageHelpService.triDetails(id));
    }

    @ApiOperation(value = "体征辅助 - 列表")
    @PostMapping(name = "列表", path = "/triLists")
    public ResponseDto<?> triList(@RequestBody final TriListParamDto TriListParamDto) {

        return ResponseUtil.wrapSuccess(this.triageHelpService.triList(TriListParamDto));
    }

    @ApiOperation(value = "体征辅助 - 体征辅助分诊")
    @GetMapping(name = "体征辅助分诊", path = "/getTriageHelp")
    public ResponseDto<?> getTriageHelp() {

        return ResponseUtil.wrapSuccess(this.triageHelpService.getTriageHelp());
    }

}
