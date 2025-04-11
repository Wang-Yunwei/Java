package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_SHIFT_RECORD")
public class MedPhepShiftRecordEntity {

    /**
     * 主键
     */
    @TableId(value = "SHIFT_RECORD_ID", type = IdType.ASSIGN_UUID)
    private String shiftRecordId;

    /**
     * 任务ID
     */
    @TableField(value = "MISSION_ID")
    private String missionId;

    /**
     * 所属患者
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 患者主诉
     */
    @TableField(value = "CHIEF_COMPLAINT")
    private String chiefComplaint;

    /**
     * 初步诊断
     */
    @TableField(value = "DIAGNOSIS")
    private String diagnosis;

    /**
     * 急救措施
     */
    @TableField(value = "TREATMENT")
    private String treatment;

    /**
     * 病情判断
     */
    @TableField(value = "CONDITION_GRADE")
    private String conditionGrade;

    /**
     * 是否报警
     */
    @TableField(value = "ALERT_FLAT")
    private String alertFlat;

    /**
     * 出警派出所或交警队
     */
    @TableField(value = "POLICE_ADDRESS")
    private String policeAddress;

    /**
     * 交接医院ID
     */
    @TableField(value = "HANDOVER_HOSPITAL_ID")
    private String handoverHospitalId;

    /**
     * 交接医院名
     */
    @TableField(value = "HANDOVER_HOSPITAL_NAME")
    private String handoverHospitalName;

    /**
     * 交接科室
     */
    @TableField(value = "DEPT")
    private String dept;

    /**
     * 急救医生
     */
    @TableField(value = "DOCTOR")
    private String doctor;

    /**
     * 急救护士
     */
    @TableField(value = "NURSE")
    private String nurse;

    /**
     * 出诊医生
     */
    @TableField(value = "EMERGENCY_DOCTOR")
    private String emergencyDoctor;

    /**
     * 接诊护士
     */
    @TableField(value = "EMERGENCY_NURSE")
    private String emergencyNurse;

    /**
     * 其他
     */
    @TableField(value = "OTHER_INFO")
    private String otherInfo;

    /**
     * 交接时间
     */
    @TableField(value = "SHIFT_TIME")
    private LocalDateTime shiftTime;

    /**
     * 提交时间
     */
    @TableField(value = "SUBMIT_TIME")
    private LocalDateTime submitTime;

    /**
     * 提交状态
     */
    @TableField(value = "SUBMIT_STATUS")
    private String submitStatus;

    /**
     * 辅助检查
     */
    @TableField(value = "ACCESSORY_EXAM")
    private String accessoryExam;
    
    /**
     * 出诊医护
     */
    @TableField(value = "OUT_OF_OFFICE_DIAGNOSIS")
    private String outOfOfficeDiagnosis;

    /**
     * 接诊医护
     */
    @TableField(value = "ACCEPT_DIAGNOSIS")
    private String acceptDiagnosis;

}
