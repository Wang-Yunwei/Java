package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class HistoryInp {

    @Schema(description = "云盒编号,传此值查询指定云盒飞行历史,不传则查询用户名下云盒所有飞行历史")
    private String boxSn;

    @Schema(description = "开始时间 (yyyy-MM-dd)")
    private String startTime;

    @Schema(description = "结束时间 (yyyy-MM-dd)")
    private String endTime;

    @Schema(description = "当前页码,默认为1")
    private Integer pageIndex = 1;

    @Schema(description = "每页显示数量,默认为15")
    private Integer pageSize = 15;
}
