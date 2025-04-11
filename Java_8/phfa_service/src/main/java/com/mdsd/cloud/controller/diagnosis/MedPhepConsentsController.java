package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepConsentsService;
import com.mdsd.cloud.controller.diagnosis.vo.ConsentsDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ConsentsSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepConsentsEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 患者知情同意书
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepConsents")
@Api(value = "MedPhepConsentsController", tags = "患者知情同意书")
public class MedPhepConsentsController {

    MedPhepConsentsService medPhepConsentsService;

    public MedPhepConsentsController(final MedPhepConsentsService medPhepConsentsService) {

        this.medPhepConsentsService = medPhepConsentsService;
    }

    /**
     * 新增Or修改
     */
    @ApiOperation(value = "新增Or修改")
    @PostMapping(name = "新增Or修改", value = "/saveOrUpdate")
    public ResponseDto<Integer> saveOrUpdate(@RequestBody final ConsentsSaveOrUpdateVo consentsSaveOrUpdateVo) {

        return ResponseUtil.wrapSuccess(this.medPhepConsentsService.saveOrUpdate(consentsSaveOrUpdateVo));
    }

    /**
     * 根据患者ID查询
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询")
    @GetMapping(name = "根据患者ID查询", value = "/queryById/{patientId}")
    public ResponseDto<MedPhepConsentsEntity> queryById(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepConsentsService.queryById(patientId));
    }

    /**
     * 根据患者ID查询详情
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询详情")
    @GetMapping(name = "根据患者ID查询详情", value = "/queryDetailByPatientId/{patientId}")
    public ResponseDto<ConsentsDetailVo> queryDetailByPatientId(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepConsentsService.queryDetailByPatientId(patientId));
    }

}
