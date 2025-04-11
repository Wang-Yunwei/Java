package com.mdsd.cloud.controller.groupevents.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询条件
 *
 * @author hxn
 * @version 1.0
 * @date 2021/9/14 13:19
 */
@Data
public class GesDetailsParamDto extends BaseDto {

    /**
     * 主键
     */
    private String eventId;

    /**
     * 查询条件开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 查询条件结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 状态(0:删除，1:正常，2:作废)
     */
    private String status;

}
