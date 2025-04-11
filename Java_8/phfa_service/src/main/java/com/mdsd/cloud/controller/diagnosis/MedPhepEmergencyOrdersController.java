package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepEmergencyOrdersService;
import com.mdsd.cloud.entity.MedPhepEmergencyOrdersEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用药信息
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepEmergencyOrders")
@Api(value = "MedPhepEmergencyOrdersController", tags = "用药信息")
public class MedPhepEmergencyOrdersController {

    MedPhepEmergencyOrdersService medPhepEmergencyOrdersService;

    public MedPhepEmergencyOrdersController(final MedPhepEmergencyOrdersService medPhepEmergencyOrdersService) {

        this.medPhepEmergencyOrdersService = medPhepEmergencyOrdersService;
    }

    /**
     * 新增Or修改
     */
    @ApiOperation(value = "新增Or修改")
    @PostMapping(name = "新增Or修改", value = "/saveOrUpdate")
    public ResponseDto<Boolean> saveOrUpdate(@RequestBody final MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity) {

        return ResponseUtil.wrapSuccess(this.medPhepEmergencyOrdersService.saveOrUpdate(medPhepEmergencyOrdersEntity));
    }

    /**
     * 根据患者ID查询用药信息
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询用药信息")
    @GetMapping(name = "根据患者ID查询用药信息", value = "/queryByPatientId/{patientId}")
    public ResponseDto<List<MedPhepEmergencyOrdersEntity>> queryByPatientId(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepEmergencyOrdersService.queryByPatientId(patientId));
    }

    /**
     * 根据用药Id删除信息
     *
     * @param orderId 用药ID
     */
    @ApiOperation(value = "根据用药Id删除信息")
    @PostMapping(name = "根据用药Id删除信息", value = "/deleteEmergencyOrdersById/{orderId}")
    public ResponseDto<Integer> deleteEmergencyOrdersById(@PathVariable final String orderId) {

        return ResponseUtil.wrapSuccess(this.medPhepEmergencyOrdersService.deleteEmergencyOrdersById(orderId));
    }

}
