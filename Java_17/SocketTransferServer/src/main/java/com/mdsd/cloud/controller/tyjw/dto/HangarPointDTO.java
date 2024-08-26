package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-27]
 */
@Getter
@Setter
public class HangarPointDTO {

    @Schema(description = "机库ID")
    private String hangarId;

    @Schema(description = "机库经度")
    private Double hangarLng;

    @Schema(description = "机库纬度")
    private Double hangarLat;

    @Schema(description = "备降点经度")
    private Double alternateLng;

    @Schema(description = "备降点纬度")
    private Double alternateLat;

    @Schema(description = "飞机回仓后是否等待所有媒体文件传完再关仓: 0-立即关仓, 1-等待传完后关仓")
    private Integer mediaUploadFlag;
}
