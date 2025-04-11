package com.mdsd.cloud.controller.other;

import com.mdsd.cloud.controller.other.dto.TriagePatientListParamDto;
import com.mdsd.cloud.controller.other.service.OtherService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangYunwei [2021-12-23]
 */
@RestController
@RequestMapping("/OtherController/v1")
@Api(value = "WEB - 其他服务调用接口", tags = "Other", produces = MediaType.APPLICATION_JSON_VALUE)
public class OtherController {

    OtherService otherService;

    public OtherController(final OtherService otherService) {

        this.otherService = otherService;
    }

    @ApiOperation(value = "Other - 分诊患者列表")
    @PostMapping(name = "卒中调用 - 分诊患者列表", path = "/triagePatientPageList")
    public ResponseDto<?> triagePatientPageList(@RequestBody final TriagePatientListParamDto triagePatientListParamDto) {

        return ResponseUtil.wrapSuccess(this.otherService.triagePatientPageList(triagePatientListParamDto));
    }

    @ApiOperation(value = "Other - 分诊患者详情")
    @GetMapping(name = "分诊患者详情", path = "/triagePatientDetail/{regId}")
    public ResponseDto<?> triagePatientDetails(@PathVariable final String regId) {

        return ResponseUtil.wrapSuccess(this.otherService.triagePatientDetail(regId));
    }
}
