package com.mdsd.cloud.controller.map.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-26]
 */
@Getter
@Setter
public class CurrentPointsDto {

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal lng;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;

    /**
     * 采集时间
     */
    @ApiModelProperty(value = "采集时间")
    private LocalDateTime recordingTime = LocalDateTime.now();

}
