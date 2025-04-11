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
 * @date 2021/9/17 16:33
 */
@Data
@TableName(value = "MED_CLINIC_REGITIONRELATION")
public class MedClinicRegitionRelationEntity {

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
     * HIS系统挂号编号
     */
    @TableField(value = "REGISTER_NO")
    private String registerNo;

    /**
     * 挂号时间
     */
    @TableField(value = "REGISTER_TIME")
    private LocalDateTime registerTime;

    /**
     * 就诊卡号
     */
    @TableField(value = "CLINIC_CARD")
    private String clinicCard;

    /**
     * 就诊卡类别（医保、自费、离休等）
     */
    @TableField(value = "CARD_TYPE")
    private String cardType;

    /**
     * 病人档案编号
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 挂号科室
     */
    @TableField(value = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 挂号科室中文名称
     */
    @TableField(value = "DEPARTMENT_NAME")
    private String departmentName;

    /**
     * 挂号医生
     */
    @TableField(value = "DOCTOR_ID")
    private String doctorId;

    /**
     * 挂号医生姓名
     */
    @TableField(value = "DOCTOR_NAME")
    private String doctorName;
}
