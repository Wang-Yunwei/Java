package com.mdsd.cloud.controller.firstaidbigscreen;

import com.mdsd.cloud.controller.firstaidbigscreen.Service.BigScreenService;
import com.mdsd.cloud.controller.firstaidbigscreen.dto.*;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 急救公告大屏
 *
 * @author WangYunwei [2022-04-06]
 */
@RestController
@RequestMapping("/BigScreenController/v1")
@Api( tags = "急救公告大屏", produces = MediaType.APPLICATION_JSON_VALUE)
public class BigScreenController {

    BigScreenService bigScreenService;

    public BigScreenController(final BigScreenService bigScreenService) {

        this.bigScreenService = bigScreenService;
    }

    /**
     * 急救公告大屏 - 24h内急救患者列表
     */
    @ApiOperation(value = "急救公告大屏 - 24h内急救患者列表")
    @PostMapping(name = "24h内急救患者列表", value = "/patientList24h")
    public ResponseDto<PatientList24hReo> patientList24h(@RequestBody final PatientList24hPo patientList24hPo) {

        return ResponseUtil.wrapSuccess(this.bigScreenService.patientList24h(patientList24hPo));
    }

    /**
     * 急救公告大屏 - 时间轴
     */
    @ApiOperation(value = "急救公告大屏 - 时间轴")
    @GetMapping(name = "时间轴", value = "/timeAxis/{hospitalArea}")
    public ResponseDto<List<TimeAxisReo>> timeAxis(@PathVariable final String hospitalArea) {

        return ResponseUtil.wrapSuccess(this.bigScreenService.timeAxis(hospitalArea));
    }

    /**
     * 急救公告大屏 - 移动轨迹
     */
    @ApiOperation(value = "急救公告大屏 - 移动轨迹")
    @PostMapping(name = "移动轨迹", value = "/mobilityTrace")
    public ResponseDto<List<MobilityTraceReo>> mobilityTrace(@RequestBody final MobilityTracePo param) {

        return ResponseUtil.wrapSuccess(this.bigScreenService.mobilityTrace(param));
    }
}
