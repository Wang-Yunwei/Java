package com.mdsd.cloud.controller.common;

import com.mdsd.cloud.controller.common.dto.DasQueryPo;
import com.mdsd.cloud.controller.common.dto.GetAuthTokenPo;
import com.mdsd.cloud.controller.common.dto.GetBusinessTokenPo;
import com.mdsd.cloud.controller.common.dto.GetDoctorOrNurseByHospitalPo;
import com.mdsd.cloud.controller.common.service.CommonService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import com.mdsd.cloud.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WangYunwei [2022-07-13]
 */
@RestController
@RequestMapping("/CommonController/v1")
@Api(value = "WEB - 公共接口", tags = "Common", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {

    CommonService commonService;

    public CommonController(final CommonService commonService) {

        this.commonService = commonService;
    }

    @ApiOperation(value = "Common - 获取UUID")
    @GetMapping(name = "获取UUID", path = "/getUUID")
    public ResponseDto<?> getUUID() {

        return ResponseUtil.wrapSuccess(CommonUtils.GetRandomUUID());
    }

    @ApiOperation(value = "DAS - 院前急救")
    @PostMapping(name = "院前急救", path = "/dasQuery")
    public ResponseDto<?> dasQuery(@RequestBody final DasQueryPo dasQueryPo) {

        return ResponseUtil.wrapSuccess(this.commonService.dasQuery(dasQueryPo));
    }

    @ApiOperation(value = "Auth - 获取业务Token")
    @PostMapping(name = "获取业务Token", path = "/login/getBusinessToken")
    public ResponseDto<?> getBusinessToken(@RequestBody final GetBusinessTokenPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.getBusinessToken(param));
    }

    @ApiOperation(value = "Auth - 获取权限Token")
    @PostMapping(name = "获取权限Token", path = "/login/getAuthToken")
    public ResponseDto<?> getAuthToken(@RequestBody final GetAuthTokenPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.getAuthToken(param));
    }

    @ApiOperation(value = "Auth - 获取医院列表")
    @GetMapping(name = "获取医院列表", path = "/getHospitals")
    public ResponseDto<?> getHospitals() {

        return ResponseUtil.wrapSuccess(this.commonService.getHospitals());
    }

    @ApiOperation(value = "Auth - 获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryValue", value = "模糊查询值", dataType = "String")
    })
    @GetMapping(name = "获取用户", path = "/getSomeUser")
    public ResponseDto<?> getSomeUser(final HttpServletRequest request) {

        return ResponseUtil.wrapSuccess(this.commonService.getSomeUser(request.getParameter("queryValue"), request.getHeader("jwt-token")));
    }

    @ApiOperation(value = "Auth - 获取医院所有部门")
    @GetMapping(name = "获取医院所有部门", path = "/getDepartments/{hospitalId}")
    public ResponseDto<?> getDepartments(@PathVariable final String hospitalId) {

        return ResponseUtil.wrapSuccess(this.commonService.getDepartments(hospitalId));
    }

    @ApiOperation(value = "Auth - 根据医院ID获取医生Or护士")
    @PostMapping(name = "根据医院ID获取医生Or护士", path = "/getDoctorOrNurseByHospital")
    public ResponseDto<?> getDoctorOrNurseByHospital(@RequestBody final GetDoctorOrNurseByHospitalPo param) {

        return ResponseUtil.wrapSuccess(this.commonService.getDoctorOrNurseByHospital(param));
    }
}
