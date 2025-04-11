package com.mdsd.cloud.controller.other;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.other.dto.*;
import com.mdsd.cloud.controller.other.service.OtherService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 其它
 *
 * @author WangYunwei [2022-01-06]
 */
@RestController
@RequestMapping("/OtherController/v1")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "其它")
public class OtherController {

    OtherService otherService;

    public OtherController(final OtherService otherService) {

        this.otherService = otherService;
    }

    /**
     * Other - 急救病历
     */
    @Deprecated
    @ApiOperation(value = "Other - 急救病历")
    @PostMapping(name = "详情", value = "/getFirstAidMedicalRecord")
    public ResponseDto<GetFirstAidMedicalRecordRso> getFirstAidMedicalRecord(@RequestBody final GetFirstAidMedicalRecordPao getFirstAidMedicalRecordPo) {

        return ResponseUtil.wrapSuccess(this.otherService.getFirstAidMedicalRecord(getFirstAidMedicalRecordPo));
    }

    /**
     * Other - 患者列表(分页)
     */
    @ApiOperation(value = "Other - 患者列表(分页)")
    @PostMapping(name = "患者列表(分页)", value = "/getPatientsPage")
    public ResponseDto<IPage<GetPatientsPageRo>> getPatientsPage(@RequestBody final GetPatientsPagePo getPatientsPagePo) {

        return ResponseUtil.wrapSuccess(this.otherService.getPatientsPage(getPatientsPagePo));
    }

    /**
     * Other - 详情
     */
    @ApiOperation(value = "Other - 详情")
    @PostMapping(name = "详情", value = "/firstAidDetails")
    public ResponseDto<FirstAidDetailsRo> firstAidDetails(@RequestBody final FirstAidDetailsPo firstAidDetailsPo) {

        return ResponseUtil.wrapSuccess(this.otherService.firstAidDetails(firstAidDetailsPo));
    }

    /**
     * 和公司急诊系统对接，将获取院前患者列表，改写成返回包含详情的接口
     */
    @ApiOperation(value = "Other - 患者列表")
    @PostMapping("/getPatients")
    public ResponseDto<List<GetFirstAidMedicalRecordRso>> getPatients(@RequestBody final GetPatientsPo getPatientsPo) {

        final List<GetFirstAidMedicalRecordRso> res = new ArrayList<>();
        // 查询列表
        final List<GetPatientsPageRo> list = this.otherService.getPatients(getPatientsPo);
        if (!list.isEmpty()) {
            for (final GetPatientsPageRo item : list) {
                // 查询病历详情
                final GetFirstAidMedicalRecordPao model = new GetFirstAidMedicalRecordPao();
                model.setMissionId(item.getMissionId());
                final GetFirstAidMedicalRecordRso obj = this.otherService.getFirstAidMedicalRecord(model);
                if (obj != null) {
                    res.add(obj);
                }
            }
        }
        return ResponseUtil.wrapSuccess(res);
    }

    @ApiOperation(value = "Other - 通过患者ID获取医院ID、院区、任务ID、病历ID")
    @GetMapping(name = "通过患者ID获取医院ID、任务ID、病历ID", path = "/oneForFour/{patientId}")
    public ResponseDto<OneForFourRo> oneForFour(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.otherService.oneForFour(patientId));
    }

    /**
     * Other - 评分详情
     */
    @ApiOperation(value = "Other - 评分详情")
    @GetMapping(name = "评分详情", value = "/getEstimateDetail/{missionId}")
    public ResponseDto<List<EstimateDto>> getEstimateDetail(@PathVariable final String missionId) {

        return ResponseUtil.wrapSuccess(this.otherService.getEstimateDetail(missionId));
    }
}
