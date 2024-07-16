package com.mdsd.cloud.controller.listener.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Year;

/**
 * @author WangYunwei [2024-07-15]
 */
public class HmsAlarmData {

    @Schema(description = "报警信息ID")
    private Integer alarmId;

    @Schema(description = "等级[1/2-警告 3/4/5-错误]")
    private Byte reportLevel;

    @Schema(description = "报警信息描述 (说明: 支持中文和英文版本,默认中文,如需切换请联系技术支持)")
    private String alarmMssInfo;
}
