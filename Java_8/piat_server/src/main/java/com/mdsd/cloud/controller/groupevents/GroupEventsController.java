package com.mdsd.cloud.controller.groupevents;

import com.mdsd.cloud.controller.groupevents.dto.GesDetailsParamDto;
import com.mdsd.cloud.controller.groupevents.dto.GesSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.groupevents.dto.RegitionListsParamDto;
import com.mdsd.cloud.controller.groupevents.service.GroupEventsService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hxn
 */
@RestController
@RequestMapping(value = "/GroupEvents")
@Api(value = "GroupEventsController", tags = "群伤事件")
public class GroupEventsController {

    GroupEventsService groupEventsService;

    public GroupEventsController(final GroupEventsService groupEventsService) {

        this.groupEventsService = groupEventsService;
    }

    @ApiOperation(value = "新增or修改群伤事件详情")
    @PostMapping(name = "新增or修改群伤事件详情", path = "/gesSaveOrUpdate")
    public ResponseDto<?> gesSaveOrUpdate(@RequestBody final GesSaveOrUpdateParamDto events) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.gesSaveOrUpdate(events));
    }

    @ApiOperation(value = "查看群伤事件list")
    @PostMapping(name = "查看群伤事件list", path = "/selectList")
    public ResponseDto<?> selectList(@RequestBody final GesDetailsParamDto gesDetailsParamDto) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.gesSelectPageEvents(gesDetailsParamDto));
    }

    @ApiOperation(value = "修改群伤事件状态/删除")
    @PostMapping(name = "修改群伤事件状态", path = "/updateStatus/{status}")
    public ResponseDto<?> updateStatus(@RequestBody final List<String> ids, @PathVariable final String status) {

        return ResponseUtil.wrapSuccess(ResponseUtil.wrapSuccess(this.groupEventsService.updateStatus(ids, status)));
    }

    @ApiOperation(value = "导出群伤事件")
    @PostMapping(name = "导出群伤事件", path = "/exportExcel")
    public ResponseDto<?> exportExcel(final HttpServletRequest request, final HttpServletResponse response, @RequestBody final GesDetailsParamDto gesDetailsParamDto) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.exportExcel(gesDetailsParamDto, response, request));
    }

    @ApiOperation(value = "查询群伤患者")
    @PostMapping(name = "查询群伤患者", path = "/gesSelectByIdList")
    public ResponseDto<?> gesSelectByIdList(@RequestBody final GesDetailsParamDto gesDetailsParamDto) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.gesSelectByIdList(gesDetailsParamDto));
    }

    @ApiOperation(value = "解除关联")
    @PostMapping(name = "解除关联", path = "/disassociate")
    public ResponseDto<?> disassociate(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.disassociate(ids));
    }

    @ApiOperation(value = "导入患者关系绑定")
    @PostMapping(name = "导入患者关系绑定", path = "/bindingCardInfoList/{eventId}")
    public ResponseDto<?> bindingCardInfoList(@RequestBody final List<String> ids, @PathVariable final String eventId) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.bindingCardInfoList(ids, eventId));
    }

    @ApiOperation("查询主诉信息")
    @PostMapping(name = "查询主诉信息", path = "/selectPatientSay")
    public ResponseDto<?> selectPatientSay(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.selectPatientSay(ids));
    }

    @ApiOperation("打印分诊凭条")
    @PostMapping(name = "打印分诊凭条", path = "/printCallSlip/{id}/{templateId}")
    public ResponseDto<?> printCallSlip(@PathVariable final String id, @PathVariable final String templateId, final HttpServletResponse response, final HttpServletRequest request) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.printCallSlip(id, templateId, response, request));
    }

    @ApiOperation("打印腕带")
    @PostMapping(name = "打印腕带", path = "/printWristBands/{id}/{templateId}")
    public ResponseDto<?> printWristBands(@PathVariable final String id, @PathVariable final String templateId, final HttpServletResponse response, final HttpServletRequest request) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.printWristBands(id, templateId, response, request));
    }

    @ApiOperation(value = "查看分诊列表list")
    @PostMapping(name = "查看分诊列表list", path = "/selectByCardInfo")
    public ResponseDto<?> selectByCardInfo(@RequestBody final RegitionListsParamDto regitionListsParamDto) {

        return ResponseUtil.wrapSuccess(this.groupEventsService.selectByCardInfo(regitionListsParamDto));
    }
}