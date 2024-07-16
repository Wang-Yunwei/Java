package com.mdsd.cloud.controller.airport;

import com.mdsd.cloud.controller.airport.dto.HangarListOup;
import com.mdsd.cloud.controller.airport.dto.LineInp;
import com.mdsd.cloud.controller.airport.dto.PlanLineDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateInp;
import com.mdsd.cloud.controller.airport.service.AirportService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangYunwei [2024-07-11]
 */
@Tag(name = "机场接口")
@RequestMapping(name = "机场接口", path = "/airport")
@RestController
public class AirportController {

    private AirportService service;

    public AirportController(AirportService service) {

        this.service = service;
    }

    @Operation(summary = "机库列表",description = "获取用户名下所有机场")
    @GetMapping(name = "机库列表", path = "/hangarList")
    public ResponseDto<HangarListOup> hangarList() {

        return ResponseDto.wrapSuccess(service.hangarList());
    }

    @Operation(summary = "机库控制",description = "发送机场控制命令")
    @GetMapping(name = "机库控制", path = "/hangarControl/{hangarId}/{instructId}")
    public ResponseDto hangarControl(@PathVariable String hangarId,@PathVariable Integer instructId) {

        service.hangarControl(hangarId,instructId);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "规划机库航线")
    @PostMapping(name = "规划机库航线", path = "/line")
    public ResponseDto line(@RequestBody PlanLineDTO param) {

        service.line(param);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "修改机库信息")
    @PostMapping(name = "修改机库信息", path = "/update")
    public ResponseDto update(@RequestBody UpdateInp param) {

        service.update(param);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "获取舱外视频地址")
    @PostMapping(name = "获取舱外视频地址", path = "/video/out/{hangarId}/{type}")
    public ResponseDto videoOut(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoOut(hangarId,type));
    }

    @Operation(summary = "获取舱内视频地址")
    @PostMapping(name = "获取舱内视频地址", path = "/video/{hangarId}/{type}")
    public ResponseDto videoIn(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoIn(hangarId,type));
    }

}
