package com.mdsd.cloud.controller.tyjw.dto;

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
public class RechargeDTO {

    @Schema(description = "机场绑定的云盒编号")
    private String boxSn;

    @Schema(description = "机场编号")
    private String hangarId;

    @Schema(description = "机场类型: 1-充电机场")
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

    @Schema(description = "机场门状态: 打开(open)、关闭(close)、异常(error)")
    private String hangarDoor;

    @Schema(description = "推杆状态: 打开(open)、关闭(close)、异常(error)")
    private String hangerBar;

    @Schema(description = "上下推杠状态: 打开(open)、关闭(close)、异常(error)")
    private String hangerTdBar;

    @Schema(description = "左右推杠状态: 打开(open)、关闭(close)、异常(error)")
    private String hangerLrBar;

    @Schema(description = "空调状态: 打开(open)、关闭(close)、异常(error)")
    private String airCondition;

    @Schema(description = "充电状态: 关机(close)、充电(charging)、待机(standby)、开机(takeoff)、充电箱断电(outage)")
    private String chargeState;

    @Schema(description = "无人机手柄状态: 打开(open)、关闭(close)、异常(error)")
    private String uavController;

    @Schema(description = "风速 (m/s)")
    private Float windspeed;

    @Schema(description = "风向 (北风、东北风、东风、东南风、南风、西南风、西风、西北风)")
    private String winddirction;

    @Schema(description = "是否下雨：0-无雨,1-有雨")
    private Byte rain;

    @Schema(description = "无人机电量: 未知状态(0)、0%~25%电量(1)、25%~50%电量(2)、50%~75%电量(3)、75%~100%电量(4)、满电量(100)")
    private Byte batteryValue;

    @Schema(description = "机场指令按钮名称及指令号,机场在线时有此值")
    private List<InstructDTO> instructList;
}
