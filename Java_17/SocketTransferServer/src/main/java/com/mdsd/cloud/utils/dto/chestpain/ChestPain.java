package com.mdsd.cloud.utils.dto.chestpain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class ChestPain {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String CITIZEN_CARD;

    private String IDCARD;

    private String NAME;

    private String GENDER;

    private String AGE_VALUE;

    private String AGE_UNIT;

    private String CONTACT_PHONE;

    private String OUTPATIENT_ID;

    private String INPATIENT_ID;

    private String ATTACK_ADDRESS;

    private Date ATTACK_TIME;

    private String IS_NULL_ATTACK_DETAIL_TIME;

    private String ATTACK_ZONE;

    private String IS_HELP;

    private String IS_PERSISTENT;

    private String IS_INTERMITTENT;

    private String IS_LAXATION;

    private String IS_BELLYACHE;

    private String IS_DYSPNEA;

    private String IS_SHOCK;

    private String IS_HEART_ATTACK;

    private String IS_MALIGNANT_ARRHYTHMIA;

    private String IS_CPR;

    private String IS_HEMORRHAGE;

    private String IS_OTHER;

    private String COMING_WAY_CODE;

    private String CP_DIAGNOSIS_CODE;

    private Date HELP_DATE;

    private String HELP_CODE;

    private String STATUS;

    private String PROCESS_STATUS;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private Date ARCHIVE_DATE;

    private Date REGIST_TIME;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PATIENT_NO;

    private String PRIMARY_ID;

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

    private Date FIRST_TREATMENT_TIME;

    private String IS_EMPCI;

    private String IS_THROMBOLYSIS_DT;

    private String IS_REPCI;

    private String IS_EMRADIOGRAPHY;

    private String IS_ELPCI;

    private String IS_ELRADIOGRAPHY;

    private String IS_CABG;

    private String IS_NOREPERFUSION;

    private String IS_REPOTHER;

    private String OTHER_TREATMENT_MEASURE;

    private String INTERVENTION_PERSON;

    private Date DECISION_OPERATION_TIME;

    private Date START_CONDUIT_TIME;

    private Date ACTIVATE_CONDUIT_TIME;

    private Date ARRIVE_CONDUIT_TIME;

    private Date START_PUNCTURE_TIME;

    private Date START_RADIOGRAPHY_TIME;

    private Date END_RADIOGRAPHY_TIME;

    private Date AGAIN_SIGN_AGREE_TIME;

    private Date BALLOON_EXPANSION_TIME;

    private Date START_OPERATION_TIME;

    private Date END_OPERATION_TIME;

    private String DTWOB_TIME;

    private String IS_DELAY;

    private String DIAGNOSIS_UNIT_CODE_DT;

    private Date THROM_START_TIME_DT;

    private Date THROM_END_TIME_DT;

    private String THROM_DRUG_TYPE_DT;

    private String THROM_DRUG_CODE_DT;

    private String IS_REPATENCY_DT;

    private String THROM_RESULT_DESC_DT;

    private String PREOPERATIVE_TIMI_LEVEL;

    private String POSTOPERATIVE_TIMI_LEVEL;

    private Date DECISION_CABG_TIME;

    private Date START_CABG_TIME;

    private Date END_CABG_TIME;

    private String PERFUSION_MEASURE_CODE;

    private String PERFUSION_MEASURE_DESC;

    private Date START_INTERVENTION_DATE;

    private Date END_INTERVENTION_DATE;

    private String DELAY_REASON;

    private Date SIGN_OPERATE_AGREE_TIME;

    private String OPERATION_RESULT;

    private Date STAND_RID_DATE;

    private Date START_TREATE_DATE;

    private Date CCU_INTO_DATE;

    private String TREATMENT_STRATEGY_CODE;

    private String INTERLAYER_TYPE;

    private Date ECC_CONSULTATION_DATE;

    private Date IMCD_NOTICE_DATE;

    private Date IMCD_CONSULTATION_DATE;

    private Date CHECK_RESULT_DATE;

    private Date CDU_CHECK_DATE;

    private Date CDU_ARRIVE_DATE;

    private String IS_CDU;

    private Date CT_REPORT_DATE;

    private Date CT_SCAN_DATE;

    private Date CT_ARRIVE_DATE;

    private Date USER_ARRIVE_DATE;

    private Date CT_FINISH_DATE;

    private Date CT_NOTICE_DATE;

    private String IS_ECT;

    private Date ANTI_TREATMENT_DATE;

    private String NSTEMI_TYPE;

    private String THROM_CHECK;

    private String INTENSIFY_STATINS_TREAT;

    private String RECEPTOR_RETARDANT_USING;

    private Date NOTICE_CDU_TIME;

    private Date THROM_START_AGREE_TIME;

    private Date THROM_SIGN_AGREE_TIME;

    private String TIME_INTERVAL;

    private Date START_OPERATE_AGREE_TIME;

    private Date ACTUAL_INTERVENTION_DATE;

    private String PERFUSION_MEASURE_OTHER;

    private String IS_MRI;

    private Date SUCCESS_PUNCTURE_TIME;

    private String IS_TPCI;

    private String IS_HEART_FAILURE;

    private String HOD;

    private String TOTAL_COST;

    private String OUTCOME_CODE;

    private String OUTCOME_NAME;

    private Date LEAVE_TIME;

    private String TREATMENT_RESULT_CODE;

    private String TREATMENT_RESULT_NAME;

    private Date HAND_TIME;

    private String HAND_HOSPITAL_NAME;

    private Date DEATH_TIME;

    private String DEATH_CAUSE_CODE;

    private String DEATH_CAUSE_DESC;

    private String REMARK;

    private String MEDICAL_DESC;

    private Date TRANSFER_DATE;

    private String ADMISSION_DEPT;

    private String TRANSFER_REASON;

    private String IS_NET_HOSPITAL;

    private String IS_TRANS_PCI;

    private String NO_TRANS_PCI_REASON;

    private String IS_DIRECT_CATHETER;

    private String OUT_GRUG_DAPT;

    private String OUT_GRUG_ACEIORARB;

    private String OUT_DRUG_STATINS;

    private String OUT_DRUG_RETARDANT;

}
