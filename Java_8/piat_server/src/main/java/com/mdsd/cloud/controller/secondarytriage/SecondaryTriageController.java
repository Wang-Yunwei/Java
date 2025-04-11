package com.mdsd.cloud.controller.secondarytriage;

import com.mdsd.cloud.controller.secondarytriage.dto.SecondPatientDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondaryTriageDto;
import com.mdsd.cloud.controller.secondarytriage.service.SecondaryTriageService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wgy
 **/

@RestController
@RequestMapping("/SecondaryTriage")
@Api(value = "WEB - SecondaryTriageController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "二次分诊")
public class SecondaryTriageController {

    SecondaryTriageService secondaryTriageService;

    public SecondaryTriageController(final SecondaryTriageService secondaryTriageService) {

        this.secondaryTriageService = secondaryTriageService;
    }

    @ApiOperation(value = "查询二次分诊患者")
    @PostMapping(name = "二次分诊 查询", path = "/getpatientsecondcheck")
    public ResponseDto<?> getPatientSecondcheck(@RequestBody final SecondPatientDto secondpatientdto) {

        return ResponseUtil.wrapSuccess(this.secondaryTriageService.getPatientSecondcheck(secondpatientdto));
    }

    @ApiOperation(value = "查询患者二次分诊记录")
    @PostMapping(name = "二次分诊 查询", path = "/getsecondcheck")
    public ResponseDto<?> getsecondcheck(final String regid) {

        return ResponseUtil.wrapSuccess(this.secondaryTriageService.getsecondcheck(regid));
    }

    @ApiOperation(value = "新增患者二次分诊")
    @PostMapping(name = "二次分诊 新增", path = "/addsecondcheck")
    public ResponseDto<?> addsecondcheck(@RequestBody final SecondaryTriageDto secondarytriagedto) {

        return ResponseUtil.wrapSuccess(this.secondaryTriageService.addsecondcheck(secondarytriagedto));
    }

    @ApiOperation(value = "二次分诊患者记录信息回显")
    @PostMapping(name = "二次分诊 根据患者记录id查询", path = "/SecondaryTriageEcho")
    public ResponseDto<?> secondarytriageecho(final String id, final String type) {

        return ResponseUtil.wrapSuccess(this.secondaryTriageService.secondaryTriageEcho(id, type));
    }
}
