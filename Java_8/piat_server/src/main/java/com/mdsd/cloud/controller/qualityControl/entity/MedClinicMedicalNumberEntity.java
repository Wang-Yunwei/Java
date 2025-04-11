package com.mdsd.cloud.controller.qualityControl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@TableName(value = "MED_CLINIC_MEDICAL_NUMBER")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class MedClinicMedicalNumberEntity {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 年份
     */
    @TableField(value = "YEAR")
    private String year;

    /**
     * 院区
     */
    @TableField(value = "HOSPITAL_AREA")
    private String hospitalArea;

    /**
     * 医生人数
     */
    @TableField(value = "DOCTOR_NUMBER")
    private int doctorNumber;

    /**
     * 护士人数
     */
    @TableField(value = "NURSE_NUMBER")
    private int nurseNumber;

    /**
     * 患者总数（万）
     */
    @TableField(value = "PATIENT_NUMBER")
    private float patientNumber;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private String status;

}
