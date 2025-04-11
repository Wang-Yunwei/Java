package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepMeasuresService;
import com.mdsd.cloud.entity.MedPhepMeasuresEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 急救措施
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepMeasures")
@Api(value = "MedPhepMeasuresController", tags = "急救措施")
public class MedPhepMeasuresController {

    MedPhepMeasuresService medPhepMeasuresService;

    public MedPhepMeasuresController(final MedPhepMeasuresService medPhepMeasuresService) {

        this.medPhepMeasuresService = medPhepMeasuresService;
    }

    /**
     * 根据患者ID查询急救措施
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询急救措施")
    @GetMapping(name = "根据患者ID查询急救措施", value = "/queryByPatientId/{patientId}")
    public ResponseDto<List<MedPhepMeasuresEntity>> queryByPatientId(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepMeasuresService.queryByPatientId(patientId));
    }

    /**
     * 根据患者ID查询急救措施
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询急救措施")
    @GetMapping(name = "根据患者ID查询急救措施", value = "/deleteMeasures/{patientId}")
    public ResponseDto<Integer> deleteMeasures(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepMeasuresService.deleteMeasures(patientId));
    }

    /**
     * 新增急救措施
     */
    @ApiOperation(value = "新增急救措施")
    @PostMapping(name = "新增急救措施", value = "/saveMeasures")
    public ResponseDto<Integer> saveMeasures(@RequestBody final MedPhepMeasuresEntity medPhepMeasuresEntity) {

        return ResponseUtil.wrapSuccess(this.medPhepMeasuresService.saveMeasures(medPhepMeasuresEntity));
    }
}
