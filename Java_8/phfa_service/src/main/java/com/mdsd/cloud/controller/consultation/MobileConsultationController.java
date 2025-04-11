package com.mdsd.cloud.controller.consultation;

import com.mdsd.cloud.controller.consultation.dto.MccListPo;
import com.mdsd.cloud.controller.consultation.dto.MccListRo;
import com.mdsd.cloud.controller.consultation.dto.MccSaveOrUpdateParDto;
import com.mdsd.cloud.controller.consultation.service.MobileConsultationService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 移动会诊
 *
 * @author WangYunwei [2021-12-31]
 */
@RestController
@RequestMapping("/MobileConsultationController/v1")
@Api(tags = "移动会诊", produces = MediaType.APPLICATION_JSON_VALUE)
public class MobileConsultationController {

    MobileConsultationService mobileConsultationService;

    public MobileConsultationController(final MobileConsultationService mobileConsultationService) {

        this.mobileConsultationService = mobileConsultationService;
    }

    /**
     * 移动会诊 - 保存or更新
     */
    @ApiOperation(value = "移动会诊 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/mccSaveOrUpdate")
    public ResponseDto<String> mccSaveOrUpdate(@RequestBody final MccSaveOrUpdateParDto mccSaveOrUpdateParDto) {

        return ResponseUtil.wrapSuccess(this.mobileConsultationService.mccSaveOrUpdate(mccSaveOrUpdateParDto));
    }

    /**
     * 移动会诊 - 列表
     */
    @ApiOperation(value = "移动会诊 - 列表")
    @PostMapping(name = "列表", value = "/mccList")
    public ResponseDto<List<MccListRo>> mccList(@RequestBody final MccListPo mccListPo, final HttpServletRequest req) {

        mccListPo.setBusinessToken(req.getHeader("jwt-token"));
        return ResponseUtil.wrapSuccess(this.mobileConsultationService.mccList(mccListPo));
    }
}
