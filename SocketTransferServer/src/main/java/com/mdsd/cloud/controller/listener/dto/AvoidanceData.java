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
public class AvoidanceData {

    private Byte downHealth;

    @Schema(description = "距下障碍物的距离(单位:米),downHealth为1时值有效")
    private Float down;

    private Byte frontHealth;

    @Schema(description = "距前障碍物的距离(单位:米),frontHealth为1时值有效")
    private Float front;

    private Byte rightHealth;

    @Schema(description = "距右障碍物的距离(单位:米),rightHealth为1时值有效")
    private Float right;

    private Byte backHealth;

    @Schema(description = "距后障碍物的距离(单位:米),backHealth为1时值有效")
    private Float back;

    private Byte leftHealth;

    @Schema(description = "距左障碍物的距离(单位:米),leftHealth为1时值有效")
    private Float left;

    private Byte upHealth;

    @Schema(description = "距上障碍物的距离(单位:米),upHealth为1时值有效")
    private Float up;

    @Schema(description = "下视觉避障开关状态: 0-关闭,1-打开")
    private Byte avoidanceEnableStatusDown;

    @Schema(description = "上视觉避障开关状态: 0-关闭,1-打开")
    private Byte avoidanceEnableStatusUp;

    @Schema(description = "水平视觉避障开关状态: 0-关闭,1-打开")
    private Byte avoidanceEnableStatusHorizontal;
}