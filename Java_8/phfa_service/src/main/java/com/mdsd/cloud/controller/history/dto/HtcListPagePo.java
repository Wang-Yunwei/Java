package com.mdsd.cloud.controller.history.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiaoneng.h
 * @date 2023/8/1 14:10
 */
@Data
public class HtcListPagePo {

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("起始页（从1开始）")
    protected Integer pageNo = 1;

    @ApiModelProperty("每页大小")
    protected Integer pageSize = 10;
}
