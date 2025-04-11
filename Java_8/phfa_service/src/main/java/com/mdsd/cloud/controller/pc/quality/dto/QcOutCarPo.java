package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * @author WangYunwei [2022-06-29]
 */
@Getter
@Setter
@ApiModel(value = "QcOutCarPo对象", description = "质控管理 - 出车统计_入参")
public class QcOutCarPo {

    /**
     * 任务状态(可不传,默认值:06)
     */
    @ApiModelProperty(value = "任务状态")
    private String missionStatus = "06";

    /**
     * 开始时间(可不传,默认值:当前月的第一天)
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());

    /**
     * 结束时间(可不传,默认值:当前月的最后一天)
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime = LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

    /**
     * 出车医院
     */
    @ApiModelProperty(value = "出车医院 -> 医院ID")
    private String hospitalId;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分站 -> 站点ID")
    private String stationId;

    /**
     * 送达医院
     */
    @ApiModelProperty(value = "送达医院")
    private String toHospitalId;
}
