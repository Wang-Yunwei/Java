package com.mdsd.cloud.controller.notice.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
public class ClinicNoticeRequest {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String noticeType;
}
