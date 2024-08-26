package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class HistoryOup {

    @Schema(description = "任务编号",example = "274")
    private Integer taskId;

    @Schema(description = "云盒编号",example = "M31220230425010")
    private String boxSn;

    @Schema(description = "云盒别名",example = "M31220230425010")
    private String boxName;

    @Schema(description = "任务名称",example = "20230915-B-5")
    private String taskName;

    @Schema(description = "照片数量",example = "0")
    private String photoCount;

    @Schema(description = "遥测文件地址 (文件格式见PB定义中飞行历史)",example = "http://uav-cloud-history.yukong.live/storage/1/pb/274.pb")
    private String telemetryPath;

    @Schema(description = "视频文件地址 (视频格式为mp4)",example = "http://uav-cloud-history.yukong.live/storage/1/video/273.mp4")
    private String videoPath;

    @Schema(description = "任务开始时间 (yyyy-MM-dd hh:mm:ss)",example = "2023-09-15 16:57:02")
    private String startTime;

    @Schema(description = "任务结束时间 (yyyy-MM-dd hh:mm:ss)",example = "2023-09-15 18:37:21")
    private String endTime;
}
