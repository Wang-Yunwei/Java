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
public class PlanLineDataDTO {

    @Schema(description = "结束航线后动作: 1-返航,2-原地悬停,3-原地降落,4-原地绕飞,5-返回至第一个航点并悬停")
    private Integer finishedAction;

    @Schema(description = "最大飞行速度(单位:米/秒,范围0~15),默认15")
    private Float maxSpeed;

    @Schema(description = "自动飞行速度(单位:米/秒,范围0~15),默认8")
    private Float autoSpeed;

    @Schema(description = "坐标点数组")
    private List<PointDataDTO> points;

    @Schema(description = "网络失联后动作: 0-返回HOME点,1=继续航线,默认0")
    private Integer loseAction;

    @Schema(description = "返航高度 (单位:米,范围20~1500)")
    private Integer homeHeight;

    @Schema(description = "是否开启节能模式: 0-不是,1-是(说明: 节能模式下,飞机沿最短直线距离由起飞点到航线第一个航点,非升高到第一个航点高度再飞往第一个航点)")
    private Integer isSaveEnergyMode;

    @Schema(description = "起飞点机库信息")
    private HangarPointDTO takePoint;

    @Schema(description = "降落点机库信息")
    private HangarPointDTO landPoint;

    @Schema(description = "兴趣点数组 (对象只包含经纬高)")
    private List<PointDataDTO> interests;

    @Schema(description = "普通建模")
    private NormalModelDTO normalModel;

    @Schema(description = "环绕建模")
    private SurroundModelDTO surroundModel;

    @Schema(description = "起飞点(对象只包含经纬高)")
    private PointDataDTO takeOffPoint;

    @Schema(description = "安全起飞高度 (单位:米,范围20~1500)")
    private Integer securityHeight;

    @Schema(description = "航线模板编号,非模板航线时传 0")
    private Integer templateId;
}
