package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author hxn
 * <p>
 * 2021/11/12 09点26分
 * 病历信息表
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_MEDICAL_RECORDS")
public class MedPhepMedicalRecordsEntity {

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID")
    @TableId(value = "RECORD_ID", type = IdType.ASSIGN_UUID)
    private String recordId;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 病历编号
     */
    @ApiModelProperty(value = "病历编号")
    @TableField(value = "CODE")
    private String code;

    /**
     * 现场救治地点
     */
    @ApiModelProperty(value = "现场救治地点")
    @TableField(value = "ADDRESS")
    private String address;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @TableField(value = "CONTACT")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField(value = "CONTACT_NUMBER")
    private String contactNumber;

    /**
     * 呼救原因
     */
    @ApiModelProperty(value = "呼救原因")
    @TableField(value = "REASON_TYPE")
    private String reasonType;

    /**
     * 疾病类别
     */
    @ApiModelProperty(value = "疾病类别")
    @TableField(value = "DISEASE_CATEGORY")
    private String diseaseCategory;

    /**
     * 病史提供类型
     */
    @ApiModelProperty(value = "病史提供类型")
    @TableField(value = "MEDICAL_HIS_PRO_TYPE")
    private String medicalHisProType;

    /**
     * 病史提供者
     */
    @ApiModelProperty(value = "病史提供者")
    @TableField(value = "HISTORY_PROVIDER")
    private String historyProvider;

    /**
     * 发病时间flag
     */
    @ApiModelProperty(value = "发病时间flag")
    @TableField(value = "IS_MORB")
    private String isMorb;

    /**
     * 发病时间区间
     */
    @ApiModelProperty(value = "发病时间区间")
    @TableField(value = "MORBTIMEAREA")
    private String morbtimearea;

    /**
     * 患者主诉
     */
    @ApiModelProperty(value = "患者主诉")
    @TableField(value = "CHIEF_COMPLAINT")
    private String chiefComplaint;

    /**
     * 现病史
     */
    @ApiModelProperty(value = "现病史")
    @TableField(value = "PRESENT_ILLNESS")
    private String presentIllness;

    /**
     * 既往史
     */
    @ApiModelProperty(value = "既往史")
    @TableField(value = "PAST_HISTORY")
    private String pastHistory;

    /**
     * 过敏史
     */
    @ApiModelProperty(value = "过敏史")
    @TableField(value = "ALLERGIC_HISTORY")
    private String allergicHistory;

    /**
     * 瞳孔（左）
     */
    @ApiModelProperty(value = "瞳孔（左）")
    @TableField(value = "LEFT_PULIL")
    private String leftPulil;

    /**
     * 瞳孔（右）
     */
    @ApiModelProperty(value = "瞳孔（右）")
    @TableField(value = "RIGHT_PULIL")
    private String rightPulil;

    /**
     * 外伤
     */
    @ApiModelProperty(value = "外伤")
    @TableField(value = "IS_TRAUMA")
    private String isTrauma;

    /**
     * 外伤部位
     */
    @ApiModelProperty(value = "外伤部位")
    @TableField(value = "TRAUMA_PART")
    private String traumaPart;

    /**
     * 外伤类型
     */
    @ApiModelProperty(value = "外伤类型")
    @TableField(value = "TRAUMA_TYPE")
    private String traumaType;

    /**
     * 血糖（mmol/L）
     */
    @ApiModelProperty(value = "血糖（mmol/L）")
    @TableField(value = "BLOODGLUCOSE")
    private Float bloodglucose;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    @TableField(value = "DIAGNOSIS")
    private String diagnosis;

    /**
     * 治疗措施
     */
    @ApiModelProperty(value = "治疗措施")
    @TableField(value = "TREATMENT")
    private String treatment;

    /**
     * 其它治疗措施
     */
    @ApiModelProperty(value = "其它治疗措施")
    @TableField(value = "OTHER_TREATMENT")
    private String otherTreatment;

    /**
     * 其它用药
     */
    @ApiModelProperty(value = "其它用药")
    @TableField(value = "OTHER_DRUGE")
    private String otherDruge;

    /**
     * 病情判断
     */
    @ApiModelProperty(value = "病情判断")
    @TableField(value = "CONDITION_GRADE")
    private String conditionGrade;

    /**
     * 病情转归
     */
    @ApiModelProperty(value = "病情转归")
    @TableField(value = "RESCUE_CHANGE_RESULT")
    private String rescueChangeResult;

    /**
     * 出诊结果
     */
    @ApiModelProperty(value = "出诊结果")
    @TableField(value = "TREATMENT_RESULT")
    private String treatmentResult;

    /**
     * 送往医院
     */
    @ApiModelProperty(value = "送往医院")
    @TableField(value = "TO_HOSPITAL")
    private String toHospital;

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称")
    @TableField(value = "TO_HOSPITAL_NAME")
    private String toHospitalName;

    /**
     * 是否通知医院
     */
    @ApiModelProperty(value = "是否通知医院")
    @TableField(value = "IS_NOTIFY_EMERGENCY")
    private int isNotifyEmergency;

    /**
     * 通知急诊科时间
     */
    @ApiModelProperty(value = "通知急诊科时间")
    @TableField(value = "NOTIFY_EMERGENCY_TIME")
    private LocalDateTime notifyEmergencyTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")

    private LocalDateTime createTime;

    /**
     * 现场情况
     */
    @ApiModelProperty(value = "现场情况")
    @TableField(value = "SCENE")
    private String scene;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 病历归档状态
     */
    @ApiModelProperty(value = "病历归档状态")
    @TableField(value = "ARCHIVE_STATUS")
    private String archiveStatus;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    @TableField(value = "SUBMIT_USER")
    private String submitUser;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    @TableField(value = "SUBMIT_TIME")
    private LocalDateTime submitTime;

    /**
     * 驳回人
     */
    @ApiModelProperty(value = "驳回人")
    @TableField(value = "REJECT_USER")
    private String rejectUser;

    /**
     * 驳回时间
     */
    @ApiModelProperty(value = "驳回时间")
    @TableField(value = "REJECT_TIME")
    private LocalDateTime rejectTime;

    /**
     * 归档人
     */
    @ApiModelProperty(value = "归档人")
    @TableField(value = "ARCHIVE_USER")
    private String archiveUser;

    /**
     * 归档时间
     */
    @ApiModelProperty(value = "归档时间")
    @TableField(value = "ARCHIVE_TIME")
    private LocalDateTime archiveTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField(value = "STATUS")
    private String status;

    /**
     * 其他
     */
    @ApiModelProperty(value = "其他")
    @TableField(value = "OTHER_INFO")
    private String otherInfo;

    /**
     * 其它检查
     */
    @ApiModelProperty(value = "其它检查")
    @TableField(value = "OTHER_CHECK")
    private String otherCheck;
}
