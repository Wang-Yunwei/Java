package com.mdsd.cloud.controller.listener.dto;

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
public class BatteryStateInfo {

    @Schema(description = "电池插入飞机状态: 0-插入,1-未插入,注意:如果未插入所有电池信息不可用")
    private Byte isBatteryEmbed;

    @Schema(description = "电池电量百分比")
    private Byte batteryCapacityPercent;

    @Schema(description = "电池电压(单位:mV)")
    private Integer currentVoltage;

    @Schema(description = "当前电池电流(单位:mA)")
    private Integer currentElectric;

    @Schema(description = "满电池容量(单位:mAh)")
    private Integer fullCapacity;

    @Schema(description = "剩余电池容量(单位:mAh)")
    private Integer remainedCapacity;

    @Schema(description = "电池温度 (单位:摄氏度)")
    private Float batteryTemperature;

    @Schema(description = "电池组数 (电池硬件型号有关)")
    private Integer cellCount;

    @Schema(description = "电池循环健康状态: 0-正常,1-警报,2-注意安全")
    private Integer batSOHState;

    @Schema(description = "相对功率百分比(Relative power percentage)")
    private Integer sop;

    @Schema(description = "电池加热状态: 0-未加热,1-加热中,2-保持恒温")
    private Byte heatState;

    @Schema(description = "电池SOC荷电状态: 0-SOC正常,1-SOC_ABNORMAL_HIGH,2-SOC_JUMP_DOWN,3-SOC_JUMP_UP,4-SOC_INVALID")
    private Byte socState;

    @Schema(description = "电池自检错误标志: 0-正常,1-电池自检NTC异常,2-电池自检MOS异常,3-电池自检采样电阻异常,4-电池电芯损坏,5-电池未校准,6-电量计参数异常,注意:如果此值非0,就是电池损坏需要维修")
    private Byte selfCheckError;

}
