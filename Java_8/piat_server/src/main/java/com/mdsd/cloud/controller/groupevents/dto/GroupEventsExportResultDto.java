package com.mdsd.cloud.controller.groupevents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/10/19 13:44
 */
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupEventsExportResultDto {
    /**
     * 主键
     */
    private String eventId;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 发生时间
     */
    private LocalDateTime eventTime;

    /**
     * 事件概述
     */
    private String describe;

    /**
     * 抢救措施
     */
    private String measures;

    /**
     * 事件性质
     */
    private String eventProperty;

    /**
     * 发生地点
     */
    private String eventAddress;

    private List<CardInfoExportResultDto> cardInfoExportResultDtoList;
}
