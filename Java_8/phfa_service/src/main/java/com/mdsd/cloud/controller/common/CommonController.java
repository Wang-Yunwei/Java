package com.mdsd.cloud.controller.common;

import com.mdsd.cloud.controller.common.dto.*;
import com.mdsd.cloud.controller.common.service.CommonService;
import com.mdsd.cloud.feign.dto.SendNoticePo;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共接口
 *
 * @author WangYunwei [2022-04-13]
 */
@RestController
@RequestMapping(value = "/CommonController/v1")
@Api(tags = "公共接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {

    CommonService commonService;

    public CommonController(final CommonService commonService) {

        this.commonService = commonService;
    }

    /**
     * Common - 获取UUID
     *
     * @param request request.getParameter("number")
     */
    @ApiOperation(value = "Common - 获取UUID")
    @GetMapping(name = "获取UUID", value = "/getUUID")
    public ResponseDto<?> getUUID(final HttpServletRequest request) {

        return ResponseUtil.wrapSuccess(this.commonService.getUUID(request));
    }

    /**
     * SSE - 建立连接
     */
    @ApiOperation(value = "SSE - 建立连接")
    @GetMapping(name = "建立连接", value = "/connectSSE/{sseId}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connectSSE(@PathVariable final String sseId) {

        return this.commonService.connectSSE(sseId);
    }

    /**
     * SSE - 推送数据
     */
    @ApiOperation(value = "SSE - 推送数据")
    @PostMapping(name = "推送数据", value = "/pushDateToSSE")
    public ResponseDto<Boolean> pushDataToSSE(@RequestBody final @Validated PushDateToSSEPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.pushDataToSSE(param));
    }

    /**
     * Redis - 保存数据到List
     */
    @ApiOperation(value = "Redis - SaveToList")
    @PostMapping(name = "SaveToList", value = "/redisSaveToList")
    public ResponseDto<Boolean> redisSaveToList(@RequestBody final RedisSaveToListPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.redisSaveToList(param));
    }

    /**
     * Auth - 获取权限Token
     */
    @ApiOperation(value = "Auth - 获取权限Token")
    @PostMapping(name = "获取权限Token", value = "/login/getAuthToken")
    public ResponseDto<?> getAuthToken(@RequestBody final GetAuthTokenPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.getAuthToken(param));
    }

    /**
     * Auth - 获取医院列表
     */
    @ApiOperation(value = "Auth - 获取医院列表")
    @GetMapping(name = "获取医院列表", value = "/getHospitals")
    public ResponseDto<?> getHospitals() {

        return ResponseUtil.wrapSuccess(this.commonService.getHospitals());
    }

    /**
     * Auth - 获取用户
     *
     * @param request getParameter("queryValue")-模糊查询字段,不传则查询所用户;getHeader("jwt-token")-权限权限的jwtToken
     */
    @ApiOperation(value = "Auth - 获取用户")
    @GetMapping(name = "获取用户", value = "/getSomeUser")
    public ResponseDto<?> getSomeUser(@RequestParam(name = "queryValue") final String queryValue, final HttpServletRequest request) {

        return ResponseUtil.wrapSuccess(this.commonService.getSomeUser(
                request.getParameter("queryValue"),
                request.getHeader("jwt-token")));
    }

    /**
     * Auth - 获取医院所有部门
     *
     * @param hospitalId 医院ID
     */
    @ApiOperation(value = "Auth - 获取医院所有部门")
    @GetMapping(name = "获取医院所有部门", value = "/getDepartments/{hospitalId}")
    public ResponseDto<?> getDepartments(@PathVariable final String hospitalId) {

        return ResponseUtil.wrapSuccess(this.commonService.getDepartments(hospitalId));
    }

    /**
     * Auth - 根据医院ID获取医生Or护士
     */
    @ApiOperation(value = "Auth - 根据医院ID获取医生Or护士")
    @PostMapping(name = "根据医院ID获取医生Or护士", value = "/getDoctorOrNurseByHospital")
    public ResponseDto<?> getDoctorOrNurseByHospital(@RequestBody final GetDoctorOrNurseByHospitalPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.getDoctorOrNurseByHospital(param));
    }

    /**
     * IM - 给某个用户或者一些用户发送实时通知
     */
    @ApiOperation(value = "IM - 给某个用户或者一些用户发送实时通知")
    @PostMapping(name = "给某个用户或者一些用户发送实时通知", value = "/sendNotice")
    public ResponseDto<Boolean> sendNotice(@RequestBody final SendNoticePo param) {

        return ResponseUtil.wrapSuccess(this.commonService.sendNotice(param));
    }

    /**
     * DAS - 推送数据
     *
     * @param missionId 任务ID
     */
    @ApiOperation(value = "DAS - 推送数据")
    @GetMapping(name = "推送数据", value = "/pushDataToDas/{missionId}")
    public ResponseDto<Boolean> pushDataToDas(@PathVariable final String missionId) {

        return ResponseUtil.wrapSuccess(this.commonService.pushDataToDas(missionId));
    }

}
