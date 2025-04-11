package com.mdsd.cloud.controller.notice;

import com.mdsd.cloud.controller.notice.dto.ClinicCheckMasterRequest;
import com.mdsd.cloud.controller.notice.dto.ClinicNoticeRequest;
import com.mdsd.cloud.controller.notice.entity.ClinicNoticeEntity;
import com.mdsd.cloud.controller.notice.service.ClinicCheckMasterService;
import com.mdsd.cloud.controller.notice.service.ClinicNoticeService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author wgy
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/ClinicNotice")
@Api(value = "WEB - ClinicNoticeController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "通知")
public class ClinicNoticeController {

    private final ClinicNoticeService clinicNoticeService;

    private final ClinicCheckMasterService clinicCheckMasterService;

    @PostMapping(value = "save")
    public void save(@RequestBody final ClinicNoticeEntity clinicNoticeEntity) {

        this.clinicNoticeService.save(clinicNoticeEntity);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody final ClinicNoticeEntity clinicNoticeEntity) {

        this.clinicNoticeService.update(clinicNoticeEntity);
    }

    @GetMapping(value = "delete")
    public void deleteById(@RequestParam("id") final String id) {

        this.clinicNoticeService.deleteById(id);
    }

    @ApiOperation(value = "查询通知列表")
    @PostMapping(value = "queryList")
    public ResponseDto<?> queryList(@RequestBody final ClinicNoticeRequest clinicNoticeRequest) {

        return ResponseUtil.wrapSuccess(this.clinicNoticeService.queryList(clinicNoticeRequest));
    }

    @ApiOperation(value = "判断是否已分诊")
    @PostMapping(value = "checkMaster")
    public ResponseDto<?> checkMaster(@RequestBody final ClinicCheckMasterRequest clinicCheckMasterRequest) {

        return ResponseUtil.wrapSuccess(this.clinicCheckMasterService.checkMaster(clinicCheckMasterRequest.getPhepPid()));
    }
}
