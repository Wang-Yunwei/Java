package com.mdsd.cloud.controller.cloudbox.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetLiveAddressOup {

    @Schema(description = "rtsp直播地址")
    private String rtsp;

    @Schema(description = "rtmp直播地址")
    private String rtmp;

    @Schema(description = "flv直播地址")
    private String flv;

    @Schema(description = "webrtc直播地址")
    private String rtc;
}
