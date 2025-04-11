package com.mdsd.cloud.controller.pc.emergencycar;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListPo;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListRo;
import com.mdsd.cloud.controller.pc.emergencycar.service.EmergencyCarService;
import com.mdsd.cloud.entity.MedPhepVehiclesEntity;
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
 * 急救车
 *
 * @author WangYunwei [2022-05-24]
 */
@RestController
@RequestMapping("/pc/EmergencyCarController/v1")
@Api(value = "WEB - 急救车", tags = "PC - EmergencyCar", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmergencyCarController {

    EmergencyCarService emergencyCarService;

    public EmergencyCarController(final EmergencyCarService emergencyCarService) {

        this.emergencyCarService = emergencyCarService;
    }

    /**
     * 急救车 - 保存or更新
     */
    @ApiOperation(value = "急救车 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/eccSaveOrUpdate")
    public ResponseDto<Boolean> eccSaveOrUpdate(@RequestBody final MedPhepVehiclesEntity medPhepVehiclesEntity) {

        return ResponseUtil.wrapSuccess(this.emergencyCarService.eccSaveOrUpdate(medPhepVehiclesEntity));
    }

    /**
     * 急救车 - 删除
     *
     * @param ids 急救车ID集合
     */
    @ApiOperation(value = "急救车 - 删除")
    @PostMapping(name = "删除", value = "/eccDelete")
    public ResponseDto<Boolean> eccDelete(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.emergencyCarService.eccDelete(ids));
    }

    /**
     * 急救车 - 分页列表
     */
    @ApiOperation(value = "急救车 - 分页列表")
    @PostMapping(name = "分页列表", value = "/eccPageList")
    public ResponseDto<IPage<EccPageListRo>> eccPageList(@RequestBody final EccPageListPo eccPageListPo) {

        return ResponseUtil.wrapSuccess(this.emergencyCarService.eccPageList(eccPageListPo));
    }
}
