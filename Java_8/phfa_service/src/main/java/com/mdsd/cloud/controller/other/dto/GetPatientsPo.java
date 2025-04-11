package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiaoneng.h
 * @date 2022/11/9 15:56
 */
@Data
public class GetPatientsPo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

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
}
