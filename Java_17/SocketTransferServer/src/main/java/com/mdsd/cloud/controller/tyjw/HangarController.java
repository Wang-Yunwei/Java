package com.mdsd.cloud.controller.tyjw;

import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.tyjw.dto.RechargeDTO;
import com.mdsd.cloud.controller.tyjw.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.tyjw.service.HangarService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangYunwei [2024-07-11]
 */
@Tag(name = "机场接口")
@RequestMapping(name = "机场接口", path = "/airport")
@RestController
public class HangarController {

    private final HangarService service;

    public HangarController(HangarService service) {

        this.service = service;
    }

    @Operation(summary = "机库列表",description = "获取用户名下所有机场")
    @GetMapping(name = "机库列表", path = "/hangarList")
    public ResponseDto<List<RechargeDTO>> hangarList() {

        return ResponseDto.wrapSuccess(service.hangarList());
    }

    @Operation(summary = "机库控制",description = "发送机场控制命令")
    @GetMapping(name = "机库控制", path = "/hangarControl/{hangarId}/{instructId}")
    public ResponseDto<String> hangarControl(@PathVariable String hangarId, @PathVariable Integer instructId) {

        return ResponseDto.wrapSuccess(service.hangarControl(hangarId,instructId));
    }

    @Operation(summary = "规划机库航线")
    @PostMapping(name = "规划机库航线", path = "/line")
    public ResponseDto<String> line(@RequestBody PlanLineDataDTO param) {

        return ResponseDto.wrapSuccess(service.line(param));
    }

    @Operation(summary = "修改机库信息")
    @PostMapping(name = "修改机库信息", path = "/update")
    public ResponseDto<String> update(@RequestBody UpdateAirportInp param) {

        return ResponseDto.wrapSuccess(service.updateAirport(param));
    }

    @Operation(summary = "获取舱外视频地址")
    @GetMapping(name = "获取舱外视频地址", path = "/video/out/{hangarId}/{type}")
    public ResponseDto<String> videoOut(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoOut(hangarId,type));
    }

    @Operation(summary = "获取舱内视频地址")
    @GetMapping(name = "获取舱内视频地址", path = "/video/{hangarId}/{type}")
    public ResponseDto<String> videoIn(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoIn(hangarId,type));
    }

}
