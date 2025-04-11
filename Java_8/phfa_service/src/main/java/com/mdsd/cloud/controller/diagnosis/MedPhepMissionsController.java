package com.mdsd.cloud.controller.diagnosis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepMissionsService;
import com.mdsd.cloud.controller.diagnosis.vo.*;
import com.mdsd.cloud.controller.diagnosis.vo.request.MissionsInfoRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsDetailRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsUpdateStatusRequest;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 任务信息
 *
 * @author wcq
 */
@RestController
@RequestMapping(value = "/phepMissions")
@Api(value = "MedPhepMissionsController", tags = "任务信息")
public class MedPhepMissionsController {

    MedPhepMissionsService medPhepMissionsService;

    public MedPhepMissionsController(final MedPhepMissionsService medPhepMissionsService) {

        this.medPhepMissionsService = medPhepMissionsService;
    }

    /**
     * 初始化信息
     */
    @ApiOperation(value = "初始化信息")
    @PostMapping(value = "init")
    public ResponseDto<PhepMissionsInitVo> init(@RequestBody final MedPhepMissionsEntity medPhepMissionsEntity) {

        return ResponseUtil.wrapSuccess(this.medPhepMissionsService.init(medPhepMissionsEntity));
    }

    @PostMapping(value = "save")
    public ResponseDto<String> save(@RequestBody final MedPhepMissionsEntity medPhepMissionsEntity) {

        if (Objects.isNull(medPhepMissionsEntity.getMissionId())) {
            this.medPhepMissionsService.save(medPhepMissionsEntity);
        } else {
            this.medPhepMissionsService.update(medPhepMissionsEntity);
        }

        return ResponseUtil.wrapSuccess(medPhepMissionsEntity.getMissionId());
    }

    @PostMapping(value = "update")
    public ResponseDto<?> update(@RequestBody final MedPhepMissionsEntity medPhepMissionsEntity) {

        this.medPhepMissionsService.update(medPhepMissionsEntity);
        return ResponseUtil.wrapSuccess();
    }

    @GetMapping(value = "delete")
    public void deleteById(@RequestParam("id") final String id) {

        this.medPhepMissionsService.deleteById(id);
    }

    @PostMapping(value = "queryByLicensePlate")
    public ResponseDto<MedPhepMissionsEntity> queryByLicensePlate(@RequestBody final PhepMissionsRequest phepMissionsRequest) {

        return ResponseUtil.wrapSuccess(this.medPhepMissionsService.queryByLicensePlate(phepMissionsRequest));
    }

    @PostMapping(value = "updateStatus")
    public ResponseDto<?> updateStatus(@RequestBody final PhepMissionsUpdateStatusRequest phepMissionsUpdateStatusRequest) {

        this.medPhepMissionsService.updateStatus(phepMissionsUpdateStatusRequest);

        return ResponseUtil.wrapSuccess();
    }

    @PostMapping(value = "detail")
    public ResponseDto<PhepMissionsDetailVo> detail(@RequestBody final PhepMissionsDetailRequest phepMissionsUpdateStatusRequest) {

        return ResponseUtil.wrapSuccess(this.medPhepMissionsService.detail(phepMissionsUpdateStatusRequest));
    }

    @PostMapping(value = "/queryMissionsList")
    public ResponseDto<List<PhepMissionsHistoryListVo>> queryMissionsList(@RequestBody final PhepMissionsParamVo phepMissionsParamVo) {

        return ResponseUtil.wrapSuccess(this.medPhepMissionsService.queryMissionsList(phepMissionsParamVo));
    }

    @PostMapping(value = "/queryByCallTime")
    public ResponseDto<IPage<MissionsInfoVo>> queryByCallTime(@RequestBody final MissionsInfoRequest missionsInfoRequest) {

        return ResponseUtil.wrapSuccess(this.medPhepMissionsService.queryByCallTime(missionsInfoRequest));
    }
}
