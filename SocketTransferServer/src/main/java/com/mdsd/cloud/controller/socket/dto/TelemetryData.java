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
public class TelemetryData {

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "纬度")
    private Double lat;

    @Schema(description = "海拔高度 (椭球高,单位: 米,与遥控器显示值不同源,可能不一致,会受周围气压湿度等环境影响,数据差异及误差比较大)")
    private Float  altitude;

    @Schema(description = "相对高度(单位: 米,飞行器距地面10米以下，此值为真实距地距离，大于10米时为距起飞点的相对高度)")
    private Float  ultrasonic;

    @Schema(description = "俯仰角")
    private Float pitch;

    @Schema(description = "横滚角")
    private Float roll;

    @Schema(description = "偏航角")
    private Float yaw;

    @Schema(description = "空速(单位:米/秒)")
    private Float airspeed;

    @Schema(description = "地速(单位:米/秒)")
    private Float velocity;

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "载荷俯仰角")
    private Float ptpitch;

    @Schema(description = "载荷横滚角")
    private Float ptroll;

    @Schema(description = "载荷航向角")
    private Float ptyaw;

    @Schema(description = "载荷当前倍数")
    private Float zoomfactor;

    @Schema(description = "云盒编号")
    private String boxSn;

    @Schema(description = "电池电量,多个电池以下划线分割,如：80_60")
    private String batteryPower;

    @Schema(description = "卫星计数")
    private Short satelliteCount;

    @Schema(description = "任务编号")
    private Long taskId;

    @Schema(description = "rtk经度")
    private Double rtkLng;

    @Schema(description = "rtk纬度")
    private Double rtkLat;

    @Schema(description = "rtk海拔高度 (平均海平面高度,单位: 米)")
    private Float rtkHFSL;

    @Schema(description = "rtk状态(值为50时[rtk数据fix固定解]，rtk的经纬度及海拔高度值可用)")
    private Integer rtkPositionInfo;

    @Schema(description = "当前架次飞行时长 (单位: 秒)")
    private Long airFlyTimes;

    @Schema(description = "当前架次飞行实际距离 (单位: 米)")
    private Float airFlyDistance;

    @Schema(description = "无人机编号")
    private String uavSn;

    @Schema(description = "无人机型号")
    private String uavModel;

    @Schema(description = "距降落点水平直线距离 (单位: 米)")
    private Float homeRange;

    @Schema(description = "飞行模式: 1-手动控制模式,2-姿态模式,6-MODE_P_GPS,9-热点任务中,11-自动起飞中,12-降落中,14-航线中,15-返航中,17-虚拟摇杆控制中,33-强制降落中,41-解锁电机准备起飞中")
    private Byte flightMode;

    @Schema(description = "距目标点实际距离 (单位: 米)")
    private Float targetDistance;

    @Schema(description = "预计剩余飞行时间")
    private long predictFlyTime;

    @Schema(description = "本架次最大相对高度值")
    private Float ultrasonicMax;

    @Schema(description = "本架次最小相对高度值")
    private Float ultrasonicMin;
}
