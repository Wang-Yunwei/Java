package com.mdsd.cloud.controller.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知
 *
 * @author wcq
 * @version 1.0
 * @date 2021/12/24 15:36
 */
@Data
public class ClinicNoticeVo {

    @ApiModelProperty(value = "任类类型")
    private String noticeType;

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否分诊状态（1已分诊， 0未分诊）")
    private Integer checkStatus;
}
