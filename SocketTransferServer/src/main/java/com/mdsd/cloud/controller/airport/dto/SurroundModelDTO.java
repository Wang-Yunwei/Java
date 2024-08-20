package com.mdsd.cloud.controller.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2024-07-27]
 */
@Getter
@Setter
@Accessors(chain = true)
public class SurroundModelDTO {

    @Schema(description = "作业区域")
    private List<PointDataDTO> area;

    @Schema(description = "建模平台编号: 1-瞰景,2-天宇")
    private Integer serviceId;

    @Schema(description = "相机编号")
    private Integer cameraId;

    @Schema(description = "是否实时建模: 0=不,1=是")
    private Integer isModeling;

    @Schema(description = "测绘高度(单位:米,范围20~1500)")
    private Integer height;

    @Schema(description = "边界区域")
    private List<PointDataDTO> boundary;

    @Schema(description = "曝光点数(范围: 12、16、20、24)")
    private Integer exposureCount;
}
