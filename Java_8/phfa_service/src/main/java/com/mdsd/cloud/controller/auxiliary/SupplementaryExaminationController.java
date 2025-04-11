package com.mdsd.cloud.controller.auxiliary;

import com.mdsd.cloud.controller.auxiliary.dto.SupDetailsResultDto;
import com.mdsd.cloud.controller.auxiliary.dto.SupSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.auxiliary.service.SupplementaryExaminationService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 辅助检查
 *
 * @author WangYunwei [2021-11-11]
 */
@RestController
@RequestMapping("/SupplementaryExaminationController/v1")
@Api(value = "WEB - 辅助检查", tags = "SupplementaryExamination", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplementaryExaminationController {

    SupplementaryExaminationService supplementaryExaminationService;

    public SupplementaryExaminationController(final SupplementaryExaminationService supplementaryExaminationService) {

        this.supplementaryExaminationService = supplementaryExaminationService;
    }

    /**
     * 辅助检查 - 新增or更新
     */
    @ApiOperation(value = "辅助检查 - 新增or更新")
    @PostMapping(name = "新增or更新", value = "/supSaveOrUpdate")
    public ResponseDto<Boolean> supSaveOrUpdate(@RequestBody final SupSaveOrUpdateParamDto supSaveOrUpdateParamDto) {

        return ResponseUtil.wrapSuccess(this.supplementaryExaminationService.supSaveOrUpdate(supSaveOrUpdateParamDto));
    }

    /**
     * 辅助检查 - 详情
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "辅助检查 - 详情")
    @GetMapping(name = "详情", value = "/supDetails/{patientId}")
    public ResponseDto<SupDetailsResultDto> supDetails(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.supplementaryExaminationService.supDetails(patientId));
    }
}
