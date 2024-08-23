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
public class PointDataDTO {

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "纬度")
    private Double lat;

    @Schema(description = "高度 (单位: 米,范围0~遥控器限高,此值为相对起飞点高度,如果太高会有失去网络覆盖风险)")
    private Float height;

    @Schema(description = "速度 (单位: 米/秒,范围 0 ~ 15),默认8")
    private Float speed;

    @Schema(description = "动作数组")
    private List<PointActionDTO> actions;

    @Schema(description = "航线飞行模式: 1-直线飞行,2-协调转弯,3-曲线飞行(停),4-曲线飞行(不停)")
    private Integer flightPathMode;

    @Schema(description = "转弯半径(仅协调转弯模式下有效),取值范围1~655.35单位:米,两点间距离要小于此值,此值要根据实际情况计算,切勿随便设此值")
    private Float dampingDistance;

    @Schema(description = "是否为安全返航点: 0-不是,1-是 (暂时未启用)",deprecated = true)
    private Integer safeGohomeFlag;

    @Schema(description = "偏航模式: 0-自动,1-锁定,2-遥控器控制,3-云台依照航点方向旋转,4-朝向兴趣点,5-飞机和云台的偏航同时旋转")
    private Integer headingMode;

    @Schema(description = "航点偏航 (-180 ~ 180)")
    private Float heading;

    @Schema(description = "兴趣点下标,默认-1")
    private Integer interestIndex;

    @Schema(description = "组下标")
    private Integer groupIndex;

}
