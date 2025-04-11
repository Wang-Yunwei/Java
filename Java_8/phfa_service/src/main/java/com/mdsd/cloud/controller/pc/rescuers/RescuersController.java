package com.mdsd.cloud.controller.pc.rescuers;

import com.mdsd.cloud.controller.pc.rescuers.dto.PageListOrDetailsPo;
import com.mdsd.cloud.controller.pc.rescuers.service.RescuersService;
import com.mdsd.cloud.entity.MedPhepRescuersEntity;
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
 * @author WangYunwei [2022-12-06]
 */
@RestController
@RequestMapping("/pc/RescuersController/v1")
@Api(value = "WEB - 急救人员", tags = "PC - RescuersController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RescuersController {

    RescuersService rescuersService;

    public RescuersController(final RescuersService rescuersService) {

        this.rescuersService = rescuersService;
    }

    @ApiOperation(value = "急救人员 - 保存or修改")
    @PostMapping(name = "保存or修改", path = "/saveOrUpdate")
    public ResponseDto<String> saveOrUpdate(@RequestBody final MedPhepRescuersEntity param) {

        return ResponseUtil.wrapSuccess(this.rescuersService.saveOrUpdate(param));
    }

    @ApiOperation(value = "急救人员 - 批量删除")
    @PostMapping(name = "删除", path = "/batchDeletion")
    public ResponseDto<Boolean> batchDeletion(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.rescuersService.batchDeletion(ids));
    }

    @ApiOperation(value = "急救人员 - 分页or详情")
    @PostMapping(name = "分页or详情", path = "/pageListOrDetails")
    public ResponseDto<?> pageListOrDetails(@RequestBody final PageListOrDetailsPo param) {

        return ResponseUtil.wrapSuccess(this.rescuersService.pageListOrDetails(param));
    }
}
