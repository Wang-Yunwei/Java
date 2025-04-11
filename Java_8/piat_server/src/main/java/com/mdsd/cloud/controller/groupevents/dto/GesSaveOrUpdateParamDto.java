package com.mdsd.cloud.controller.groupevents.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 修改和新增参数
 *
 * @author hxn
 * @version 1.0
 * @date 2021/9/14 13:19
 */
@Data
public class GesSaveOrUpdateParamDto {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 来院方式
     */
    private String arrayType;

    /**
     * 主诉
     */
    private String slefReport;

    /**
     * 一级（人数）
     */
    private int level1;

    /**
     * 二级（人数）
     */
    private int level2;

    /**
     * 三级（人数）
     */
    private int level3;

    /**
     * 四级（人数）
     */
    private int level4;

    /**
     * 分诊科室
     */
    private String departmentId;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 状态(0:删除，1:正常，2:作废)
     */
    private String status;

    /**
     * 事件性质
     */
    private String eventProperty;

    /**
     * 就诊科室
     */
    private String clinicDepartmentId;

    /**
     * 发生地点
     */
    private String eventAddress;

}
