package com.mdsd.cloud.controller.cloudbox;

import com.mdsd.cloud.controller.cloudbox.dto.*;
import com.mdsd.cloud.controller.cloudbox.service.CloudBoxService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangYunwei [2024-07-11]
 */
@Tag(name = "云盒接口")
@RequestMapping(name = "云盒接口", path = "/cloud_box")
@RestController
public class CloudBoxController {

    private final CloudBoxService service;

    public CloudBoxController(CloudBoxService service) {

        this.service = service;
    }

    @Operation(summary = "获取云盒列表")
    @GetMapping(name = "获取云盒列表", path = "/getCloudBoxList")
    public ResponseDto<List<GetCloudBoxListOup>> getCloudBoxList() {

        return ResponseDto.wrapSuccess(service.getCloudBoxList());
    }


    @Operation(summary = "修改云盒设置")
    @PostMapping(name = "修改云盒设置", path = "/update")
    public ResponseDto<String> update(@RequestBody UpdateCloudBoxInp param) {

        service.update(param);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "获取飞行历史")
    @PostMapping(name = "获取飞行历史", path = "/history")
    public ResponseDto<List<HistoryOup>> history(@RequestBody HistoryInp param) {

        return ResponseDto.wrapSuccess(service.history(param));
    }

    @Operation(summary = "修改推流地址",description = "当需要将云盒视频推流到第三方流媒体服务器时调此接口修改推流地址")
    @PostMapping(name = "修改推流地址", path = "/updateLive")
    public ResponseDto<String> updateLive(@RequestBody UpdateLiveInp param) {

        service.updateLive(param);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "开始直播推流",description = "云盒默认为手动推流方式,如果需要开机自动推流请联系我方技术人员")
    @GetMapping(name = "开始直播推流", path = "/openLive/{boxSn}")
    public ResponseDto<String> openLive(@PathVariable String boxSn) {

        service.openLive(boxSn);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "结束直播推流")
    @GetMapping(name = "结束直播推流", path = "/closeLive/{boxSn}")
    public ResponseDto<String> closeLive(@PathVariable String boxSn) {

        service.closeLive(boxSn);
        return ResponseDto.wrapSuccess();
    }

    @Operation(summary = "获取直播地址",description = "Rtsp推流时只返回rtsp拉流地址,rtmp推流时返回rtmp、flv和rtc拉流地址")
    @GetMapping(name = "获取直播地址", path = "/getLiveAddress/{boxSn}")
    public ResponseDto<GetLiveAddressOup> getLiveAddress(@PathVariable String boxSn) {

        return ResponseDto.wrapSuccess(service.getLiveAddress(boxSn));
    }

    @Operation(summary = "获取任务照片")
    @PostMapping(name = "获取任务照片", path = "/getPhotos")
    public ResponseDto<List<GetPhotosOup>> getPhotos(@RequestBody GetPhotosInp param) {

        return ResponseDto.wrapSuccess(service.getPhotos(param));
    }


}
