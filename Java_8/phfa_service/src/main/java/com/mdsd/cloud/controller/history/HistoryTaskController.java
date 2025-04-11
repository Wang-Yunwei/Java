package com.mdsd.cloud.controller.history;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.history.dto.HtcListPagePo;
import com.mdsd.cloud.controller.history.dto.HtcListPo;
import com.mdsd.cloud.controller.history.dto.HtcListRo;
import com.mdsd.cloud.controller.history.service.HistoryTaskService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 历史任务
 *
 * @author WangYunwei [2022-06-28]
 */
@RestController
@RequestMapping(value = "/HistoryTaskController/v1")
@Api(tags = "历史任务", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryTaskController {

    HistoryTaskService historyTaskService;

    public HistoryTaskController(final HistoryTaskService historyTaskService) {

        this.historyTaskService = historyTaskService;
    }

    /**
     * 历史任务 - 列表
     */
    @ApiOperation(value = "历史任务 - 列表")
    @PostMapping(name = "列表", value = "/htcList")
    public ResponseDto<List<HtcListRo>> htcList(@RequestBody final HtcListPo htcListPo) {

        return ResponseUtil.wrapSuccess(this.historyTaskService.htcList(htcListPo));
    }

    /**
     * 历史任务 - 分布列表
     */
    @ApiOperation(value = "历史任务 - 分页列表")
    @PostMapping(name = "列表", value = "/htcPageList")
    public ResponseDto<IPage<HtcListRo>> htcPageList(@RequestBody final HtcListPagePo htcListPagePo) {

        return ResponseUtil.wrapSuccess(this.historyTaskService.htcPageList(htcListPagePo));
    }
}
