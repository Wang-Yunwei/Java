package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class UpdateLiveInp {

    @Schema(description = "云盒SN编号")
    private String boxSn;

    @Schema(description = "推流地址,支持rtmp和rtsp方式")
    private String pushUrl;
}
