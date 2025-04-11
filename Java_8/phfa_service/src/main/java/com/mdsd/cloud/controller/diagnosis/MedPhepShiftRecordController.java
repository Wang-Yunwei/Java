package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepShiftRecordService;
import com.mdsd.cloud.controller.diagnosis.vo.PrintDeliveryRecordRo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftRecordDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftSaveOrUpdateVo;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 急救交接单
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping(value = "/medPhepShiftRecord")
@Api(value = "MedPhepShiftRecordController", tags = "急救交接单")
public class MedPhepShiftRecordController {

    MedPhepShiftRecordService medPhepShiftRecordService;

    public MedPhepShiftRecordController(final MedPhepShiftRecordService medPhepShiftRecordService) {

        this.medPhepShiftRecordService = medPhepShiftRecordService;
    }

    /**
     * 新增or修改
     */
    @ApiOperation(value = "新增or修改")
    @PostMapping(name = "新增or修改", value = "/saveOrUpdateShift")
    public ResponseDto<?> saveOrUpdateShift(@RequestBody final ShiftSaveOrUpdateVo shiftSaveOrUpdateVo) {

        this.medPhepShiftRecordService.saveOrUpdateShift(shiftSaveOrUpdateVo);
        return ResponseUtil.wrapSuccess();
    }

    /**
     * 查询详情信息
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "查询详情信息")
    @GetMapping(name = "查询详情信息", value = "/queryDetail/{patientId}")
    public ResponseDto<ShiftRecordDetailVo> queryDetail(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepShiftRecordService.queryDetail(patientId));
    }

    /**
     * 预览患者交接单
     *
     * @param templateId 模板ID
     * @param patientId  患者ID
     */
    @ApiOperation(value = "预览患者交接单")
    @PostMapping(name = "预览患者交接单", value = "/printShiftRecord/{templateId}/{patientId}")
    public void printShiftRecord(@PathVariable final String templateId, @PathVariable final String patientId, final HttpServletResponse response) {

        this.medPhepShiftRecordService.printShiftRecord(templateId, patientId, response);
    }

    /**
     * 急救交接单 - 打印交接记录
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "急救交接单 - 打印交接记录")
    @GetMapping(name = "打印交接记录", value = "/printDeliveryRecord/{patientId}")
    public ResponseDto<PrintDeliveryRecordRo> printDeliveryRecord(@PathVariable final String patientId) {

        return ResponseUtil.wrapSuccess(this.medPhepShiftRecordService.printDeliveryRecord(patientId));
    }

}