package com.mdsd.cloud.controller.cloudbox.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetPhotosInp {

    @Schema(description = "任务编号")
    private Long taskId;

    @Schema(description = "当前页码,默认为1")
    private Integer pageIndex = 1;

    @Schema(description = "每页显示数量,默认为15")
    private Integer pageSize = 1;
}

