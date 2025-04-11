package com.mdsd.cloud.controller.pc.emergencystation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListPo;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListRo;
import com.mdsd.cloud.controller.pc.emergencystation.service.EmergencyStationService;
import com.mdsd.cloud.entity.MedPhepStationEntity;
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
 * 急救站
 *
 * @author WangYunwei [2022-05-31]
 */
@RestController
@RequestMapping(value = "/pc/EmergencyStationController/v1")
@Api(value = "WEB - 急救站", tags = "PC - EmergencyStation", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmergencyStationController {

    EmergencyStationService emergencyStationService;

    public EmergencyStationController(final EmergencyStationService emergencyStationService) {

        this.emergencyStationService = emergencyStationService;
    }

    /**
     * 急救站 - 保存or更新
     */
    @ApiOperation(value = "急救站 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/escSaveOrUpdate")
    public ResponseDto<Boolean> escSaveOrUpdate(@RequestBody final MedPhepStationEntity medPhepStationEntity) {

        return ResponseUtil.wrapSuccess(this.emergencyStationService.escSaveOrUpdate(medPhepStationEntity));
    }

    /**
     * 急救站 - 删除
     *
     * @param ids 急救站ID集合
     */
    @ApiOperation(value = "急救站 - 删除")
    @PostMapping(name = "删除", value = "/escDelete")
    public ResponseDto<Boolean> escDelete(@RequestBody final List<String> ids) {

        return ResponseUtil.wrapSuccess(this.emergencyStationService.escDelete(ids));
    }

    /**
     * 急救站 - 分页列表
     */
    @ApiOperation(value = "急救站 - 分页列表")
    @PostMapping(name = "保存or更新", value = "/escPageList")
    public ResponseDto<IPage<EscPageListRo>> escPageList(@RequestBody final EscPageListPo escPageListPo) {

        return ResponseUtil.wrapSuccess(this.emergencyStationService.escPageList(escPageListPo));
    }
}
