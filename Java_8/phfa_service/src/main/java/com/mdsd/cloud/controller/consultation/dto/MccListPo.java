package com.mdsd.cloud.controller.consultation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-02-14]
 */
@Getter
@Setter
@ApiModel(value = "移动会诊 - 列表_入参")
public class MccListPo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

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

    /**
     * 业务token
     */
    @ApiModelProperty(value = "业务token")
    private String businessToken;
}
