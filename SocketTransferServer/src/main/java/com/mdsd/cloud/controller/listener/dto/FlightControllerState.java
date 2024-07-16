package com.mdsd.cloud.controller.listener.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author WangYunwei [2024-07-15]
 */
public class FlightControllerState {

    @Schema(description = "卫星计数")
    private Short satelliteCount;

    @Schema(description = "GPS信号级别 (范围: 0-5 [4,5等级可以执行航线])")
    private Byte gpsSignalLevel;

    @Schema(description = "飞行模式：1-手动控制模式，2-姿态模式，6-MODE_P_GPS，9-热点任务中，11-自动起飞中，12-降落中，14-航线中，15-返航中，17-虚拟摇杆控制中，33-强制降落中，41-解锁电机准备起飞中")
    private Byte flightMode;

    @Schema(description = "飞行状态:0-电机未启动(地面)，1-电机启动未起飞(地面)，2-起飞在空中(空中)")
    private Byte flightStatus;

    @Schema(description = "起飞点 (只包含经纬度信息)")
    private PointData startPoint;

    @Schema(description = "返航点 (只包含经纬度信息)")
    private PointData homePoint;

    @Schema(description = "返航高度 (单位: 米)")
    private Integer homeHeight;

    @Schema(description = "打点飞行模式 (0-节能模式【飞机直线飞到目标点,需飞机在空中并且有控制权】,1-安全模式【飞机飞行至目标点高度再平飞到目标点,飞机可以在地面】 默认为节能模式)")
    private Byte safeLine;

    @Schema(description = "控制权状态 (0-遥控器,1-msdk,4-云盒)")
    private Byte deviceStatus;

    @Schema(description = "UWB星数 (只有等于4时，才可以开启uwb精准降落模式)")
    private Integer uwbNodeCount;

    @Schema(description = "遥控器档位模式 (说明: 1.遥控器与飞机正常连接:(M300:T-P-S;M30:F-S-N;M3:C-N-S)，2.遥控器与飞机未连接:W，3.只有在M300-P挡、M30-N挡、M3-N挡下云盒才能正常控制无人机)")
    private String rcMode;

    @Schema(description = "是否紧急制动中 (0-否，1-是,飞行器在地面限制起飞,飞行器在空中限制飞行.如果想操作飞行,请先解除紧急制动)")
    private Byte isEmergencyBrakeing;

    @Schema(description = "飞行器是否在所有自定义电子围栏外 (0-电子围栏内,1-电子围栏外)")
    private Byte outFlyAreaFlag;

    @Schema(description = "飞行器是否在自定义禁飞区内 (0-禁飞区外,1-禁飞区内)")
    private Byte inNoFlyAreaFlag;

}
