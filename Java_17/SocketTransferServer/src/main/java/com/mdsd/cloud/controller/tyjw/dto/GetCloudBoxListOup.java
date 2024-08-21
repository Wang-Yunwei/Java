package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-11]
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetCloudBoxListOup {

    @Schema(description = "云盒SN编号",example = "M31220230425010")
    private String boxSn;

    @Schema(description = "云盒别名",example = "M31220230425010")
    private String boxName;

    @Schema(description = "云盒版本号",example = "DKM_0.4.4")
    private String boxVersion;

    @Schema(description = "启动时间 (yyyy-MM-dd hh:mm:ss)",example = "2023-09-15 16:56:46")
    private String startTime;

    @Schema(description = "云盒在线状: 0-离线,1-在线",example = "1")
    private Integer onLine;

    @Schema(description = "视频服务器地址(用于通过UDP方式接收视频原始数据)",example = "118.178.106.75")
    private String videoServer;

    @Schema(description = "视频服务器端口(用于通过UDP方式接收视频原始数据)",example = "17047")
    private Integer videoPort;

    @Schema(description = "任务编号",example = "274")
    private Integer lastTaskId;
}
