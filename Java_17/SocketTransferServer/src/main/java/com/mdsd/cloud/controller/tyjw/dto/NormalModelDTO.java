package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2024-07-27]
 */
@Getter
@Setter
public class NormalModelDTO {

    @Schema(description = "作业区域")
    private List<PointDataDTO> area;

    @Schema(description = "建模平台编号(默认1): 1-瞰景,2-大势")
    private Integer serviceId;

    @Schema(description = "相机编号")
    private Integer cameraId;

    @Schema(description = "是否实时建模: 0-不,1-是")
    private Integer isModeling;

    @Schema(description = "作业模式: 1-倾斜,2-正摄")
    private Integer mode;

    @Schema(description = "横向重叠率")
    private Integer hOverlap;

    @Schema(description = "纵向重叠率")
    private Integer vOverlap;

    @Schema(description = "正反: 1-正,2-反")
    private Integer mirror;

    @Schema(description = "方向: 1-南北,2-东西")
    private Integer direction;

    @Schema(description = "航线飞行模式: 1-直线飞行,2-协调转弯")
    private Integer flightPathMode;

    @Schema(description = "测绘高度(单位:米,范围20~1500)")
    private Integer height;
}
