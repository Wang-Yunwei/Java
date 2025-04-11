package com.mdsd.cloud.controller.pc.quality.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-07-01]
 */
@Getter
@Setter
@ApiModel(value = "QcMeanResponseTimePo对象", description = "质控管理 - 平均反应时间_入参")
public class QcMeanResponseTimePo {

    /**
     * 任务状态(可不传,默认值:06)
     */
    @ApiModelProperty(value = "任务状态(06-完成急救)")
    private String missionStatus = "06";

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
