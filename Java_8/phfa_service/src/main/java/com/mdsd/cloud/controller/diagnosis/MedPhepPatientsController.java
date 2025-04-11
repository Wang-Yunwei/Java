package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepPatientsService;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 患者信息
 *
 * @author wcq
 */
@RestController
@RequestMapping(value = "phepPatients")
@Api(value = "MedPhepPatientsController", tags = "患者信息")
public class MedPhepPatientsController {

    MedPhepPatientsService medPhepPatientsService;

    public MedPhepPatientsController(final MedPhepPatientsService medPhepPatientsService) {

        this.medPhepPatientsService = medPhepPatientsService;
    }

    @PostMapping(value = "save")
    public ResponseDto<String> save(@RequestBody final MedPhepPatientsEntity medPhepPatientsEntity) {

        if (Objects.isNull(medPhepPatientsEntity.getPatientId())) {
            this.medPhepPatientsService.save(medPhepPatientsEntity);
        } else {
            this.medPhepPatientsService.update(medPhepPatientsEntity);
        }

        return ResponseUtil.wrapSuccess(medPhepPatientsEntity.getPatientId());
    }

    @PostMapping(value = "update")
    public ResponseDto<?> update(@RequestBody final MedPhepPatientsEntity medPhepPatientsEntity) {

        this.medPhepPatientsService.update(medPhepPatientsEntity);
        return ResponseUtil.wrapSuccess();
    }

    @GetMapping(value = "delete")
    public void deleteById(@RequestParam("id") final String id) {

        this.medPhepPatientsService.deleteById(id);
    }

    @GetMapping(value = "/queryByPatientId/{patientId}")
    public ResponseDto<MedPhepPatientsEntity> queryByPatientId(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepPatientsService.queryByPatientId(patientId));
    }
}
