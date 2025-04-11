package com.mdsd.cloud.controller.pc.medicalrecord;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.*;
import com.mdsd.cloud.controller.pc.medicalrecord.service.MedicalRecordManageService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 病历管理
 *
 * @author WangYunwei [2022-05-13]
 */
@RestController
@RequestMapping("/pc/MedicalRecordManageController/v1")
@Api(value = "WEB - 病历管理", tags = "PC - MedicalRecordManage", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordManageController {

    MedicalRecordManageService medicalRecordManageService;

    public MedicalRecordManageController(final MedicalRecordManageService medicalRecordManageService) {

        this.medicalRecordManageService = medicalRecordManageService;
    }

    /**
     * 病历管理 - 保存or更新
     */
    @ApiOperation(value = "病历管理 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/mrmSaveOrUpdate")
    public ResponseDto<Boolean> mrmSaveOrUpdate(@RequestBody final MrmSaveOrUpdatePo mrmSaveOrUpdatePo) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.mrmSaveOrUpdate(mrmSaveOrUpdatePo));
    }

    /**
     * 病历管理 - 删除
     *
     * @param recordIds 病历ID集合
     */
    @ApiOperation(value = "病历管理 - 删除")
    @PostMapping(name = "删除", value = "/mrmDelete")
    public ResponseDto<Boolean> mrmDelete(@RequestBody final List<String> recordIds) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.mrmDelete(recordIds));
    }

    /**
     * 病历管理 - 分页列表
     */
    @ApiOperation(value = "病历管理 - 分页列表")
    @PostMapping(name = "分页列表", value = "/mrmPageList")
    public ResponseDto<IPage<MrmPageListRo>> mrmPageList(@RequestBody final MrmPageListPo mrmPageListPo) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.mrmPageList(mrmPageListPo));
    }

    /**
     * 病历管理 - 详情
     */
    @ApiOperation(value = "病历管理 - 详情")
    @PostMapping(name = "详情", value = "/mrmDetails")
    public ResponseDto<MrmDetailsRo> mrmDetails(@RequestBody final MrmDetailsPo mrmDetailsPo) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.mrmDetails(mrmDetailsPo));
    }

    /**
     * 病历管理 - 获取无任务急救车
     */
    @ApiOperation(value = "病历管理 - 获取无任务急救车")
    @GetMapping(name = "获取无任务急救车", value = "/getNoTaskEmergencyCar")
    public ResponseDto<List<String>> getNoTaskEmergencyCar(@RequestParam(name = "hospitalId") final String hospitalId, @RequestParam(name = "hospitalArea") final String hospitalArea) {

        final NoTaskEmergencyCarPo noTaskEmergencyCarPo = new NoTaskEmergencyCarPo().setHospitalId(hospitalId).setHospitalArea(hospitalArea);
        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.getNoTaskEmergencyCar(noTaskEmergencyCarPo));
    }

    /**
     * 病历管理 - 时间轴
     *
     * @param missionId 任务ID
     */
    @ApiOperation(value = "病历管理 - 时间轴")
    @GetMapping(name = "时间轴", value = "/timeAxis/{missionId}")
    public ResponseDto<TimeAxisRo> timeAxis(@PathVariable final String missionId) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.timeAxis(missionId));
    }

    /**
     * 病历管理 - 修改时间轴
     */
    @ApiOperation(value = "病历管理 - 修改时间轴")
    @PostMapping(name = "修改时间轴", value = "/timeAxisUpdate")
    public ResponseDto<Boolean> timeAxisUpdate(@RequestBody final TimeAxisUpdatePo timeAxisUpdatePo) {

        return ResponseUtil.wrapSuccess(this.medicalRecordManageService.timeAxisUpdate(timeAxisUpdatePo));
    }
}
