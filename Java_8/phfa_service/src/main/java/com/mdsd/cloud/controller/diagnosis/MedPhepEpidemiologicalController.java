package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepEpidemiologicalService;
import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalSaveOrUpdateVo;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 新冠肺炎流调管理
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepEpidemiological")
@Api(value = "MedPhepEpidemiologicalController", tags = "新冠肺炎流调管理")
public class MedPhepEpidemiologicalController {

    MedPhepEpidemiologicalService medPhepEpidemiologicalService;

    public MedPhepEpidemiologicalController(final MedPhepEpidemiologicalService medPhepEpidemiologicalService) {

        this.medPhepEpidemiologicalService = medPhepEpidemiologicalService;
    }

    /**
     * 新增or修改
     */
    @ApiOperation(value = "新增or修改")
    @PostMapping(name = "新增or修改", value = "/saveOrUpdate")
    public ResponseDto<Integer> saveOrUpdate(@RequestBody final EpidemiologicalSaveOrUpdateVo epidemiologicalSaveOrUpdateVo) {

        return ResponseUtil.wrapSuccess(this.medPhepEpidemiologicalService.saveOrUpdate(epidemiologicalSaveOrUpdateVo));
    }

    /**
     * 根据患者ID查询详情
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "根据患者ID查询详情")
    @GetMapping(name = "根据患者ID查询详情", value = "/queryByPatientId/{patientId}")
    public ResponseDto<EpidemiologicalDetailVo> queryByPatientId(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepEpidemiologicalService.queryByPatientId(patientId));
    }

}
