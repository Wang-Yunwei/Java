package com.mdsd.cloud.controller.map;

import com.mdsd.cloud.controller.map.dto.RecordTrackPo;
import com.mdsd.cloud.controller.map.dto.SmcListOrDetailPo;
import com.mdsd.cloud.controller.map.dto.SmcSaveOrUpdatePo;
import com.mdsd.cloud.controller.map.service.SpecialtyMapService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专科地图
 *
 * @author WangYunwei [2021-11-15]
 */
@RestController
@RequestMapping("/SpecialtyMapController/v1")
@Api(tags = "地图", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecialtyMapController {

    SpecialtyMapService specialtyMapService;

    public SpecialtyMapController(final SpecialtyMapService specialtyMapService) {

        this.specialtyMapService = specialtyMapService;
    }

    /**
     * 专科地图 - 记录轨迹
     */
    @ApiOperation(value = "专科地图 - 记录轨迹")
    @PostMapping(name = "记录轨迹", value = "/recordTrack")
    public ResponseDto<Boolean> recordTrack(@RequestBody final RecordTrackPo recordTrackPo) {

        return ResponseUtil.wrapSuccess(this.specialtyMapService.recordTrack(recordTrackPo));
    }

    /**
     * 专科地图 - 保存or更新
     */
    @ApiOperation(value = "专科地图 - 保存or更新")
    @PostMapping(name = "保存or更新", value = "/smcSaveOrUpdate")
    public ResponseDto<Boolean> smcSaveOrUpdate(@RequestBody final SmcSaveOrUpdatePo smcSaveOrUpdatePo) {

        return ResponseUtil.wrapSuccess(this.specialtyMapService.smcSaveOrUpdate(smcSaveOrUpdatePo));
    }

    /**
     * 专科地图 - 移除
     *
     * @param missionIds 任务ID集合
     */
    @ApiOperation(value = "专科地图 - 移除")
    @PostMapping(name = "移除", value = "/smcRemove")
    public ResponseDto<Boolean> smcRemove(@RequestBody final List<String> missionIds) {

        return ResponseUtil.wrapSuccess(this.specialtyMapService.smcRemove(missionIds));
    }

    /**
     * 专科地图 - 列表or详情
     */
    @ApiOperation(value = "专科地图 - 列表or详情")
    @PostMapping(name = "列表or详情", value = "/smcListOrDetail")
    public ResponseDto<?> smcListOrDetail(@RequestBody final SmcListOrDetailPo smcListOrDetailPo) {

        return ResponseUtil.wrapSuccess(this.specialtyMapService.smcListOrDetail(smcListOrDetailPo));
    }

    /**
     * 专科地图 - 根据MissionId删除
     *
     * @param missionId 任务ID
     */
    @ApiOperation(value = "REDIS - 根据MissionId删除")
    @GetMapping(name = "根据MissionId删除", value = "/redisRemoveByMissionId/{missionId}")
    public ResponseDto<Boolean> redisRemoveByMissionId(@PathVariable final String missionId) {

        return ResponseUtil.wrapSuccess(this.specialtyMapService.redisRemoveByMissionId(missionId));
    }
}
