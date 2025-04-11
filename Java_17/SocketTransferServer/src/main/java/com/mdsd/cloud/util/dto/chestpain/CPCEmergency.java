package com.mdsd.cloud.util.dto.chestpain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class CPCEmergency {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String DISPATCH_UNIT_CODE;

    private String DISPATCH_UNIT_NAME;

    private String IS_BYPASS_EMERGENCY;

    private String BYPASS_EMERGENCY_CODE;

    private Date ARRIVED_EMERGENCY_TIME;

    private String IS_BYPASS_CCU;

    private Date ARRIVED_CCU_DATE;

    /**
     * 是否是网络医院
     */
    private String IS_NETWORD_HOSPITAL = "0";

    private String HOSPITAL_NAME;

    private Date OUTHOSPITAL_VISIT_TIME;

    private Date TRANSFER_TIME;

    private Date AMBULANCE_ARRIVED_TIME;

    private Date LEAVE_OUTHOSPITAL_TIME;

    private Date ARRIVED_SCENE_TIME;

    private Date ARRIVED_HOSPITAL_TIME;

    private Date INHOSPITAL_ADMISSION_TIME;

    private String ATTACK_DEPARTMENT;

    private Date CONSULTATION_TIME;

    private Date LEAVE_DEPARTMENT_TIME;

    private String FIRST_MC_CODE;

    private String FIRST_MC_NAME;

    /**
     * 首诊医生名称
     */
    private String FIRST_DOCTOR_NAME = "首诊医生名称";

    /**
     * 首次医疗接触时间
     */
    private Date FIRST_MC_TIME;

    private Date PHEP_ECG_TIME;

    /**
     * 院内首份心电图时间
     */
    private Date INHOSPITAL_ECG_TIME;

    /**
     * 心电图诊断时间
     */
    private Date ECG_DIAGNOSE_TIME;

    private String IS_REMOTE_ECGTRAN_CHECKED;

    private Date TRAN_DATE;

    /**
     * 是否远程心电图输送
     */
    private String IS_REMOTE_ECGTRAN;

    /**
     * 意识
     */
    private String CONSCIOUSNESS_TYPE = "清醒";

    /**
     * 呼吸
     */
    private String RESPIRATION_RATE = "23";

    /**
     * 脉搏
     */
    private String PULSE_RATE = "78";

    /**
     * 心率
     */
    private String HEART_RATE = "78";

    /**
     * 血压
     */
    private String BLOOD_PRESSURE = "130/82";

    private String KILLIP_LEVEL;

    private Date SAMPLING_TIME;

    private Date REPORT_TIME;

    private String CTNI_VALUE;

    private String CR_VALUE;

    private String CP_DIAGNOSIS_CODE;

    private Date DIAGNOSIS_TIME;

    /**
     * 诊断医生名称
     */
    private String DIAGNOSIS_DOCTOR_NAME = "诊断医生名称";

    private Date ACS_DELIVERY_TIME;

    private String ASPIRIN_DOSE;

    private String ACS_DRUG_TYPE;

    private String ACS_DRUG_DOSE;

    /**
     * 是否溶栓治疗 选填（1 有 0 无，传1,0或空值）
     */
    private String IS_THROMBOLYSIS;

    /**
     * Grace评估发病后曾出现心脏骤停 选填（1 有 0 无，传1,0或空值）
     */
    private String IS_ARREST = "0";

    /**
     * Grace评估心电图ST段是否改变 选填（1 有 0 无，传1,0或空值）
     */
    private String IS_CHANGE = "0";

    /**
     * Grace评估心肌坏死标志物是否升高 选填（1 有 0 无，传1,0或空值）
     */
    private String IS_RISE = "0";

    private String GRACE_VALUE;

    private String RISK_LAMINATION;

    /**
     * 描述
     */
    private String DIAGNOSIS_DESC;

    private String OUTCOME_DESC;

    private String PATIENT_CASE_NOTE;

    /**
     * 心律失常 选填（1 有 0 无，传1,0或空值）
     */
    private String ARRHYTHMIA = "0";

    /**
     * 扩张性心肌病 选填（1 有 0 无，传1,0或空值）
     */
    private String DCM = "0";

    /**
     * 缺血性心肌病 选填（1 有 0 无，传1,0或空值）
     */
    private String ICM = "0";

    /**
     * 肥厚型心肌病 选填（1 有 0 无，传1,0或空值）
     */
    private String HCM = "0";

    /**
     * 心肌炎 选填（1 有 0 无，传1,0或空值）
     */
    private String CARDITIS = "0";

    /**
     * 冠心病 选填（1 有 0 无，传1,0或空值）
     */
    private String CHD = "0";

    /**
     * 瓣膜性心脏病 选填（1 有 0 无，传1,0或空值）
     */
    private String AVHD = "0";

    /**
     * 陈旧性心肌梗死 选填（1 有 0 无，传1,0或空值）
     */
    private String OMI = "0";

    /**
     * 心绞痛 选填（1 有 0 无，传1,0或空值）
     */
    private String AP = "0";

    /**
     * 心悸 选填（1 有 0 无，传1,0或空值）
     */
    private String PALPITATE = "0";

    /**
     * 房颤 选填（1 有 0 无，传1,0或空值）
     */
    private String AF = "0";

    /**
     * 高血压 选填（1 有 0 无，传1,0或空值）
     */
    private String HYPERTENSION = "0";

    /**
     * 心衰 选填（1 有 0 无，传1,0或空值）
     */
    private String HF = "0";

    /**
     * 房扑 选填（1 有 0 无，传1,0或空值）
     */
    private String ATRIALFLUTTER = "0";

    /**
     * 室早 选填（1 有 0 无，传1,0或空值）
     */
    private String VPC = "0";

    /**
     * 房早 选填（1 有 0 无，传1,0或空值）
     */
    private String APB = "0";

    /**
     * 室上速 选填（1 有 0 无，传1,0或空值）
     */
    private String ST = "0";

    private String THROMBOLYSIS_TYPE;

    private Date SIGN_AGREE_TIME;

    private Date THROM_START_TIME;

    private Date THROM_END_TIME;

    private String THROM_DRUG_TYPE;

    private String THROM_DRUG_CODE;

    private String IS_REPATENCY;

    private String THROM_RESULT_DESC;

    private Date START_AGREE_TIME;

    private String PHEP_FILE_NAME;

    private String IN_FILE_NAME;

    private String CP_DIAGNOSIS_NAME;

    private Date BYPASS_EMERGENCY_LEAVE_TIME;

    private String KILLIP_UNIT;

    private Date HANDLE_TIME;

    private String PATIENT_OUTCOME;

    private String DOCTOR_NAME = "医生";

    private String IS_ANTICOAGULATION;

    private String SCREENING;

    private String IS_DIRECT;

    private String EMERGENCY_LOG;

    private String CTNI_UNIT;

    private String CR_UNIT;

    private String THROM_TREATMENT_PLACE;

    private String HANDLE_WAY;

    private String PHEP_ECG_IMAGE_BASE64;

    private String INHOSPITAL_ECG_IMAGE_BASE64;

    private Date ANTICOAGULATION_DATE;

    private String ANTICOAGULATION_DRUG;

    private String ANTICOAGULATION_UNIT;

    private String RD;

    private String DSD;

    private String NSD;

    private String PD;

    private String MD;

    private String SSD;

    private String OTHER;

    private String CTNT_VALUE;

    private String CTNT_UNIT;

    private String CTNI_STATUS;

    private String CTNT_STATUS;

    private String DEPARTMENT;

    private String STATUS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
