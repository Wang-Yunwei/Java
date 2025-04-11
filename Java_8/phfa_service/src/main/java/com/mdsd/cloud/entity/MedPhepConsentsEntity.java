package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @ClassName MedPhepConsentsEntity 患者知情同意书
 * @Date 2021/11/12 13:11
 */
@Data
@TableName(value = "MED_PHEP_CONSENTS")
@AllArgsConstructor
@NoArgsConstructor
public class MedPhepConsentsEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 任务ID
     */
    @TableField(value = "MISSION_ID")
    private String missionId;

    /**
     * 患者ID
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 初步诊断
     */
    @TableField(value = "DIAGNOSIS")
    private String diagnosis;

    /**
     * 告知时间
     */
    @TableField(value = "TELL_TIME")
    private LocalDateTime tellTime;

    /**
     * 患者要求的其他信息
     */
    @TableField(value = "PATIENT_REQUEST_OTHER")
    private String patientRequestOther;

    /**
     * 与患者的关系
     */
    @TableField(value = "PATIENT_RELATIONSHIP")
    private String patientRelationship;

    /**
     * 患者的要求序号
     */
    @TableField(value = "PATIENT_REQUEST")
    private String patientRequest;

    /**
     * 患方签名
     */
    @TableField(value = "PATIENT_SIGNAGURE")
    private String patientSignagure;

    /**
     * 医院签名时间
     */
    @TableField(value = "HOSPITAL_SIGNAGURE_TIME")
    private LocalDateTime hospitalSignagureTime;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 委托人签字时间
     */
    @TableField(value = "CONSIGNOR_SIGNAGURE_TIME")
    private LocalDateTime consignorSignagureTime;

    /**
     * 患方签名时间
     */
    @TableField(value = "PATIENT_SIGNAGURE_TIME")
    private LocalDateTime patientSignagureTime;

    /**
     * 医院签名
     */
    @TableField(value = "HOSPITAL_SIGNAGURE")
    private String hospitalSignagure;

    /**
     * 委托人签字
     */
    @TableField(value = "CONSIGNOR_SIGNAGURE")
    private String consignorSignagure;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 病情判断
     */
    @TableField(value = "CONDITION_GRADE")
    private String conditionGrade;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private String status;

    /**
     * 是否知情同意
     */
    @TableField(value = "IS_INFORMED")
    private String isInformed;

    /**
     * 救治措施
     */
    @TableField(value = "TREATMENT_MEASURES")
    private String treatmentMeasures;
}
