package com.mdsd.cloud.controller.cardnumbermanage;

import com.mdsd.cloud.controller.cardnumbermanage.dto.HisSearchParamDto;
import com.mdsd.cloud.controller.cardnumbermanage.dto.ParamListDto;
import com.mdsd.cloud.controller.cardnumbermanage.service.CheckListService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author hxn
 */
@RestController
@RequestMapping(value = "/CardNumberManage")
@Api(value = "CardNumberManageController", tags = "关联卡号")
public class CardNumberManageController {

    CheckListService checkListService;

    public CardNumberManageController(final CheckListService checkListService) {

        this.checkListService = checkListService;
    }

    @ApiOperation(value = "根据急诊编号信息查询关联挂号信息")
    @GetMapping(name = "根据急诊编号信息查询关联挂号信息", path = "/selectCheckListByRegId/{regId}")
    public ResponseDto<?> selectCheckListByRegId(@PathVariable final String regId) {

        return ResponseUtil.wrapSuccess(this.checkListService.selectCheckListByRegId(regId));
    }

    @ApiOperation(value = "根据急诊编号信息查询关联挂号患者信息")
    @GetMapping(name = "根据急诊编号信息查询关联挂号患者信息", path = "/selectCardInfoByRegId/{regId}")
    public ResponseDto<?> selectCardInfoByRegId(@PathVariable final String regId) {

        return ResponseUtil.wrapSuccess(this.checkListService.selectCardInfoByRegId(regId));
    }

    @ApiOperation(value = "查找his系统中患者对应信息")
    @PostMapping(name = "查找his系统中患者对应信息", path = "/selectListHis")
    public ResponseDto<?> selectListHis(@RequestBody final HisSearchParamDto hisSearchParamDto) {

        return ResponseUtil.wrapSuccess(this.checkListService.selectListHis(hisSearchParamDto));
    }

    @ApiOperation(value = "查找his系统中患者对应信息")
    @PostMapping(name = "查找his系统中患者对应信息", path = "/selectHis")
    public ResponseDto<?> selectHis() {

        return ResponseUtil.wrapSuccess(this.checkListService.selectHis());
    }

    @ApiOperation(value = "关联卡号,匹配卡号,换卡")
    @PostMapping(name = "关联卡号,匹配卡号,换卡", path = "/bindingCard/{status}")
    public ResponseDto<?> bindingCard(@RequestBody final ParamListDto paramListDto, @PathVariable final String status) {

        return ResponseUtil.wrapSuccess(this.checkListService.bindingCard(paramListDto, status));
    }
}
