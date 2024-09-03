package com.mdsd.cloud.controller.tyjw;

import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2024-09-03]
 */
@Tag(name = "天宇 - API")
@RestController
public class TyjwController {


    private final ITyjwService service;

    public TyjwController(ITyjwService service) {

        this.service = service;
    }

    @Operation(summary = "GetToken",description = "换取AccessToken(鉴权),同时执行TCP连接注册")
    @PostMapping(name = "换取AccessToken(鉴权),同时执行TCP连接注册", path = "/auth/getToken")
    public ResponseDto<GetTokenOup> getToken(@RequestBody GetTokenInp param) {

        return ResponseDto.wrapSuccess(service.getToken(param));
    }

    @Operation(summary = "云盒 - 列表")
    @GetMapping(name = "云盒 - 列表", path = "/cloud_box/getCloudBoxList")
    public ResponseDto<List<GetCloudBoxListOup>> getCloudBoxList() {

        return ResponseDto.wrapSuccess(service.getCloudBoxList());
    }


    @Operation(summary = "云盒 - 修改设置")
    @PostMapping(name = "云盒 - 修改设置", path = "/cloud_box/update")
    public ResponseDto<String> update(@RequestBody UpdateCloudBoxInp param) {

        return ResponseDto.wrapSuccess(service.updateCloudBox(param));
    }

    @Operation(summary = "云盒 - 获取飞行历史")
    @PostMapping(name = "云盒 - 获取飞行历史", path = "/cloud_box/history")
    public ResponseDto<List<HistoryOup>> history(@RequestBody HistoryInp param) {

        return ResponseDto.wrapSuccess(service.history(param));
    }

    @Operation(summary = "云盒 - 修改推流地址", description = "当需要将云盒视频推流到第三方流媒体服务器时调此接口修改推流地址")
    @PostMapping(name = "云盒 - 修改推流地址", path = "/cloud_box/updateLive")
    public ResponseDto<String> updateLive(@RequestBody UpdateLiveInp param) {

        return ResponseDto.wrapSuccess(service.updateLive(param));
    }

    @Operation(summary = "云盒 - 开始直播推流", description = "云盒默认为手动推流方式,如果需要开机自动推流请联系我方技术人员")
    @GetMapping(name = "云盒 - 开始直播推流", path = "/cloud_box/openLive/{boxSn}")
    public ResponseDto<String> openLive(@PathVariable String boxSn) {

        return ResponseDto.wrapSuccess(service.openLive(boxSn));
    }

    @Operation(summary = "云盒 - 结束直播推流")
    @GetMapping(name = "云盒 - 结束直播推流", path = "/cloud_box/closeLive/{boxSn}")
    public ResponseDto<String> closeLive(@PathVariable String boxSn) {

        return ResponseDto.wrapSuccess(service.closeLive(boxSn));
    }

    @Operation(summary = "云盒 - 获取直播地址", description = "Rtsp推流时只返回rtsp拉流地址,rtmp推流时返回rtmp、flv和rtc拉流地址")
    @GetMapping(name = "云盒 - 获取直播地址", path = "/cloud_box/getLiveAddress/{boxSn}")
    public ResponseDto<GetLiveAddressOup> getLiveAddress(@PathVariable String boxSn) {

        return ResponseDto.wrapSuccess(service.getLiveAddress(boxSn));
    }

    @Operation(summary = "云盒 - 获取任务照片")
    @PostMapping(name = "云盒 - 获取任务照片", path = "/cloud_box/getPhotos")
    public ResponseDto<List<GetPhotosOup>> getPhotos(@RequestBody GetPhotosInp param) {

        return ResponseDto.wrapSuccess(service.getPhotos(param));
    }

    @Operation(summary = "云盒 - 航线转换 (Kmz转Protobuf)")
    @PostMapping(name = "云盒 - 航线转换", path = "/cloud_box/convert")
    public ResponseDto<String> convert(@RequestParam(value = "kmzFile") MultipartFile file) {

        return ResponseDto.wrapSuccess(service.convert(file));
    }

    @Operation(summary = "云盒 - 机场或云盒立即执行指定的航线模板任务")
    @PostMapping(name = "云盒 - 立即执行航线模板任务", path = "/cloud_box/template")
    public ResponseDto<String> template(@RequestBody TemplateInp param) {

        return ResponseDto.wrapSuccess(service.template(param));
    }

    @Operation(summary = "机库 - 列表",description = "获取用户名下所有机场")
    @GetMapping(name = "机库 - 列表", path = "/airport/hangarList")
    public ResponseDto<List<RechargeDTO>> hangarList() {

        return ResponseDto.wrapSuccess(service.hangarList());
    }

    @Operation(summary = "机库 - 控制",description = "发送机场控制命令")
    @GetMapping(name = "机库 - 控制", path = "/airport/hangarControl/{hangarId}/{instructId}")
    public ResponseDto<String> hangarControl(@PathVariable String hangarId, @PathVariable Integer instructId) {

        return ResponseDto.wrapSuccess(service.hangarControl(hangarId,instructId));
    }

    @Operation(summary = "机库 - 规划航线")
    @PostMapping(name = "机库 - 规划航线", path = "/hangar/line")
    public ResponseDto<String> line(@RequestBody PlanLineDataDTO param) {

        return ResponseDto.wrapSuccess(service.line(param));
    }

    @Operation(summary = "机库 - 修改信息")
    @PostMapping(name = "机库 - 修改信息", path = "/airport/update")
    public ResponseDto<String> update(@RequestBody UpdateAirportInp param) {

        return ResponseDto.wrapSuccess(service.updateAirport(param));
    }

    @Operation(summary = "机库 - 获取舱外视频地址")
    @GetMapping(name = "机库 - 获取舱外视频地址", path = "/airport/video/out/{hangarId}/{type}")
    public ResponseDto<String> videoOut(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoOut(hangarId,type));
    }

    @Operation(summary = "机库 - 获取舱内视频地址")
    @GetMapping(name = "机库 - 获取舱内视频地址", path = "/airport/video/{hangarId}/{type}")
    public ResponseDto<String> videoIn(@PathVariable String hangarId,@PathVariable String type) {

        return ResponseDto.wrapSuccess(service.videoIn(hangarId,type));
    }
}
