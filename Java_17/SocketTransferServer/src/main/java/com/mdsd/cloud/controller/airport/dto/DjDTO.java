package com.mdsd.cloud.controller.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class DjDTO {

    @Schema(description = "机场绑定的云盒编号")
    private String boxSn;

    @Schema(description = "机场编号")
    private String hangarId;

    @Schema(description = "机场类型: 3-大疆机库")
    private Byte hangarType;

    @Schema(description = "机场名称")
    private String hangarName;

    @Schema(description = "机场经度")
    private Double hangarLng;

    @Schema(description = "机场纬度")
    private Double hangarLat;

    @Schema(description = "备降点经度")
    private Double secondLng;

    @Schema(description = "备降点纬度")
    private Double secondLat;

    @Schema(description = "机场在线状态: 0-离线,1-在线")
    private Byte online;

    @Schema(description = "舱外视频: 0-无,1-有")
    private Byte outVideo;

    @Schema(description = "充电状态: 0-空闲,1-充电中")
    private Byte chargeState;

    @Schema(description = "电量百分比")
    private Byte capacityPercent;

    @Schema(description = "机场状态: 0-空闲中,1-现场调试,2-远程调试,3-固件升级中,4-作业中")
    private Byte modeCode;

    @Schema(description = "舱盖状态: 0-关闭,1-打开,2-半开,3-舱盖状态异常")
    private Byte coverState;

    @Schema(description = "推杆状态: 0-关闭,1-打开,2-半开,3-推杆状态异常")
    private Byte putterState;

    @Schema(description = "补光灯状态: 0-关闭,1-打开")
    private Byte supplementLightState;

    @Schema(description = "飞机是否在舱: 0-舱外,1-舱内")
    private Byte droneInDock;

    @Schema(description = "机场声光报警状态: 0-声光报警关闭,1-声光报警开启")
    private Byte alarmState;

    @Schema(description = "机场累计作业次数")
    private Byte jobNumber;

    @Schema(description = "舱内温度 (摄氏度)")
    private Float temperature;

    @Schema(description = "舱内湿度 (%RH)")
    private Float humidity;

    @Schema(description = "机场累计运行时长 (秒)")
    private Byte accTime;

    @Schema(description = "环境温度 (摄氏度)")
    private Float environmentTemperature;

    @Schema(description = "风速 (米每秒)")
    private Float windSpeed;

    @Schema(description = "降雨量: 0-无雨,1-小雨,2-中雨,3-大雨")
    private Byte rainfall;

    @Schema(description = "空调模式: 0-空闲,1-制冷,2-加热,3-除湿")
    private Byte airMode;

    @Schema(description = "机库指令按钮名称及指令号,机库在线时有此值")
    private List<InstructDTO> instructList;

}
