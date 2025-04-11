package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepMedicalRecordsService;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 病历信息
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepMedicalRecords")
@Api(value = "MedPhepMedicalRecordsController", tags = "病历信息")
public class MedPhepMedicalRecordsController {

    MedPhepMedicalRecordsService medPhepMedicalRecordsService;

    public MedPhepMedicalRecordsController(final MedPhepMedicalRecordsService medPhepMedicalRecordsService) {

        this.medPhepMedicalRecordsService = medPhepMedicalRecordsService;
    }

    /**
     * 新增or修改
     */
    @ApiOperation(value = "新增or修改")
    @PostMapping(name = "新增or修改", value = "/saveOrUpdate")
    public ResponseDto<String> saveOrUpdate(@RequestBody final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity) {

        return ResponseUtil.wrapSuccess(this.medPhepMedicalRecordsService.saveOrUpdate(medPhepMedicalRecordsEntity));
    }

    /**
     * 根据患者ID新增or修改
     */
    @ApiOperation(value = "根据患者ID新增or修改")
    @PostMapping(name = "根据患者ID新增or修改", value = "/updateByPatientId")
    public ResponseDto<Integer> updateByPatientId(@RequestBody final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity) {

        return ResponseUtil.wrapSuccess(this.medPhepMedicalRecordsService.updateByPatientId(medPhepMedicalRecordsEntity));
    }

    /**
     * 根据患者ID查询
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询")
    @GetMapping(name = "根据患者ID查询", value = "/queryById/{patientId}")
    public ResponseDto<MedPhepMedicalRecordsEntity> queryById(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepMedicalRecordsService.queryById(patientId));
    }
}
