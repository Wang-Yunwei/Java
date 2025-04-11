package com.mdsd.cloud.controller.cardnumbermanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/17 16:25
 */
@Data
@TableName(value = "MED_CLINIC_CHECK_LIST")
public class MedClinicCheckListEntity {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 急诊编号
     */
    @TableField(value = "REG_ID")
    private String regId;

    /**
     * 患者档案号
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 挂号编号
     */
    @TableField(value = "REGISTER_NO")
    private String registerNo;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;
}
