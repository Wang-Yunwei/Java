package com.mdsd.cloud.controller.groupevents.dto;

import lombok.Data;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/30 10:04
 */
@Data
public class BaseDto {
    /**
     * 页码
     */
    private long current;

    /**
     * 页长
     */
    private long pageSize;

}
