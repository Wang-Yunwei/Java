package com.mdsd.cloud.controller.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class UavState {
    @Schema(description = "飞控状态")
    private FlightControllerState flightControllerState;

    @Schema(description = "电池状态")
    private BatteryState batteryState;

    @Schema(description = "云台状态")
    private PtzState ptzState;

    @Schema(description = "相机状态")
    private CameraState cameraState;

    @Schema(description = "任务状态")
    private MissionState missionState;

    @Schema(description = "云盒编号")
    private String boxSn;

    @Schema(description = "避障数据")
    private AvoidanceData avoidanceData;

    @Schema(description = "Hms报警信息,飞机告警信息")
    private List<HmsAlarmData> hmsAlarmData;
}
