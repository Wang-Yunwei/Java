package com.mdsd.cloud.controller.socket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class BatteryState {

    @Schema(description = "电池数量")
    private Byte batteryNum;

    @Schema(description = "电池电量,多个电池以下划线分割,如: 80_60")
    private String batteryPower;

    @Schema(description = "电池电压,多个电池以下划线分割,如: 40_30(单位:V)[注意：如果2块电池电压差大于0.7V,飞机限制起飞]")
    private String batteryVoltage;

    @Schema(description = "第一块电池状态信息 (安装在飞机左边电池槽)")
    private BatteryStateInfo firstBatteryInfo;

    @Schema(description = "第二块电池状态信息 (安装在飞机右边电池槽)")
    private BatteryStateInfo secondBatteryInfo;

}
