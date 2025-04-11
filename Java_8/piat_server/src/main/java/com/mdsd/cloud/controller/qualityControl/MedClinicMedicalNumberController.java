package com.mdsd.cloud.controller.qualityControl;

import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationNumberRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationRequestVo;
import com.mdsd.cloud.controller.qualityControl.entity.MedClinicMedicalNumberEntity;
import com.mdsd.cloud.controller.qualityControl.service.MedClinicMedicalNumberService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huxiaoneng
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/medClinicMedicalNumber")
@Api(value = "MedClinicMedicalNumberController", tags = "医护人数配置")
public class MedClinicMedicalNumberController {

    private final MedClinicMedicalNumberService medClinicMedicalNumberService;

    @ApiModelProperty("查询当前院区医护患比")
    @GetMapping("queryByArea")
    public ResponseDto<?> queryByArea(final DoctorPatientRationRequestVo doctorPatientRationRequestVo) {

        return ResponseUtil.wrapSuccess(this.medClinicMedicalNumberService.queryByArea(doctorPatientRationRequestVo));
    }

    @ApiModelProperty("查询医护人数list")
    @PostMapping("queryList")
    public ResponseDto<?> queryList(@RequestBody final DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo) {

        return ResponseUtil.wrapSuccess(this.medClinicMedicalNumberService.queryList(doctorPatientRationNumberRequestVo));
    }

    @ApiModelProperty("根据院区和年份修改")
    @PostMapping("saveOrUpdateByKey")
    public ResponseDto<?> saveOrUpdateByKey(@RequestBody final MedClinicMedicalNumberEntity medClinicMedicalNumberEntity) {

        return ResponseUtil.wrapSuccess(this.medClinicMedicalNumberService.saveOrUpdateByKey(medClinicMedicalNumberEntity));
    }

    @ApiOperation(value = "导出详情")
    @PostMapping("detailExport")
    public void detailExport(final HttpServletResponse response, @RequestBody final DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo) {

        this.medClinicMedicalNumberService.detailExport(doctorPatientRationNumberRequestVo, response);
    }

}
