package com.mdsd.cloud.controller.groupevents.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/14 13:19
 */
@Data
@TableName(value = "MED_CLINIC_GROUPEVENTS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MedClinicGroupEventsEntity {

    /**
     * 主键
     */
    @TableId(value = "EVENTID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String eventId;

    /**
     * 事件名称
     */
    @ExcelProperty(index = 0, value = "事件名称")
    @TableField(value = "EVENTNAME")
    private String eventName;

    /**
     * 发生时间
     */
    @ExcelProperty(index = 1, value = "发生时间")
    @TableField(value = "EVENTTIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;

    /**
     * 事件概述
     */
    @ExcelProperty(index = 2, value = "事件概述")
    @TableField(value = "DESCRIBE")
    private String describe;

    /**
     * 抢救措施
     */
    @ExcelProperty(index = 3, value = "抢救措施")
    @TableField(value = "MEASURES")
    private String measures;

    /**
     * 来院方式
     */
    @TableField(value = "ARRAY_TYPE")
    private String arrayType;

    /**
     * 主诉
     */
    @TableField(value = "SELF_REPORT")
    private String slefReport;

    /**
     * 一级（人数）
     */
    @TableField(value = "LEVEL1")
    private int level1;

    /**
     * 二级（人数）
     */
    @TableField(value = "LEVEL2")
    private int level2;

    /**
     * 三级（人数）
     */
    @TableField(value = "LEVEL3")
    private int level3;

    /**
     * 四级（人数）
     */
    @TableField(value = "LEVEL4")
    private int level4;

    /**
     * 分诊科室
     */
    @TableField(value = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_ID")
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 状态(0:删除，1:正常，2:作废)
     */
    @TableField(value = "STATUS")
    private String status;

    /**
     * 事件性质
     */
    @ExcelProperty(index = 4, value = "事件性质")
    @TableField(value = "EVENTPROPERTY")
    private String eventProperty;

    /**
     * 就诊科室
     */
    @TableField(value = "CLINIC_DEPARTMENTID")
    private String clinicDepartmentId;

    /**
     * 发生地点
     */
    @ExcelProperty(index = 5, value = "发生地点")
    @TableField(value = "EVENT_ADDRESS")
    private String eventAddress;

}
