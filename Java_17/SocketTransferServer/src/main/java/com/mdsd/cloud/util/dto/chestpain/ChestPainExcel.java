package com.mdsd.cloud.util.dto.chestpain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class ChestPainExcel {
    @ExcelProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @ExcelProperty("REGISTER_ID")
    private String REGISTER_ID;

//    @ExcelProperty("CITIZEN_CARD")
//    private String CITIZEN_CARD;
//
//    @ExcelProperty("IDCARD")
//    private String IDCARD;

    @ExcelProperty("NAME")
    private String NAME;

    @ExcelProperty("GENDER")
    private String GENDER;

//    @ExcelProperty("AGE_VALUE")
//    private String AGE_VALUE;
//
//    @ExcelProperty("AGE_UNIT")
//    private String AGE_UNIT;
//
//    @ExcelProperty("CONTACT_PHONE")
//    private String CONTACT_PHONE;
//
//    @ExcelProperty("OUTPATIENT_ID")
//    private String OUTPATIENT_ID;
//
//    @ExcelProperty("INPATIENT_ID")
//    private String INPATIENT_ID;
//
//    @ExcelProperty("ATTACK_ADDRESS")
//    private String ATTACK_ADDRESS;
//
//    @ExcelProperty("ATTACK_TIME")
//    private Date ATTACK_TIME;
//
//    @ExcelProperty("IS_NULL_ATTACK_DETAIL_TIME")
//    private String IS_NULL_ATTACK_DETAIL_TIME;
//
//    @ExcelProperty("ATTACK_ZONE")
//    private String ATTACK_ZONE;
//
//    @ExcelProperty("IS_HELP")
//    private String IS_HELP;
//
//    @ExcelProperty("IS_PERSISTENT")
//    private String IS_PERSISTENT;
//
//    @ExcelProperty("IS_INTERMITTENT")
//    private String IS_INTERMITTENT;
//
//    @ExcelProperty("IS_LAXATION")
//    private String IS_LAXATION;
//
//    @ExcelProperty("IS_BELLYACHE")
//    private String IS_BELLYACHE;
//
//    @ExcelProperty("IS_DYSPNEA")
//    private String IS_DYSPNEA;
//
//    @ExcelProperty("IS_SHOCK")
//    private String IS_SHOCK;
//
//    @ExcelProperty("IS_HEART_ATTACK")
//    private String IS_HEART_ATTACK;
//
//    @ExcelProperty("IS_MALIGNANT_ARRHYTHMIA")
//    private String IS_MALIGNANT_ARRHYTHMIA;
//
//    @ExcelProperty("IS_CPR")
//    private String IS_CPR;
//
//    @ExcelProperty("IS_HEMORRHAGE")
//    private String IS_HEMORRHAGE;
//
//    @ExcelProperty("IS_OTHER")
//    private String IS_OTHER;
//
//    @ExcelProperty("COMING_WAY_CODE")
//    private String COMING_WAY_CODE;
//
//    @ExcelProperty("CP_DIAGNOSIS_CODE")
//    private String CP_DIAGNOSIS_CODE;
//
//    @ExcelProperty("HELP_DATE")
//    private Date HELP_DATE;
//
//    @ExcelProperty("HELP_CODE")
//    private String HELP_CODE;
//
    @ExcelProperty("STATUS")
    private String STATUS;
//
//    @ExcelProperty("PROCESS_STATUS")
//    private String PROCESS_STATUS;
//
    @ExcelProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @ExcelProperty("UPDATE_DATE")
    private Date UPDATE_DATE;
//
//    @ExcelProperty("ARCHIVE_DATE")
//    private Date ARCHIVE_DATE;
//
    @ExcelProperty("REGIST_TIME")
    private Date REGIST_TIME;
//
//    @ExcelProperty("UPLOAD_ORGAN_CODE")
//    private String UPLOAD_ORGAN_CODE;
//
//    @ExcelProperty("UPLOAD_TIME")
//    private Date UPLOAD_TIME;
//
//    @ExcelProperty("UPLOAD_SYSTEM")
//    private String UPLOAD_SYSTEM;
//
//    @ExcelProperty("CREATE_TIME")
//    private Date CREATE_TIME;
//
//    @ExcelProperty("CLEAN_FLAG")
//    private String CLEAN_FLAG;
//
//    @ExcelProperty("DATA_ORIGIN")
//    private String DATA_ORIGIN;
//
//    @ExcelProperty("PATIENT_NO")
//    private String PATIENT_NO;
//
//    @ExcelProperty("PRIMARY_ID")
//    private String PRIMARY_ID;
//
//    @ExcelProperty("DISPATCH_UNIT_CODE")
//    private String DISPATCH_UNIT_CODE;
//
//    @ExcelProperty("DISPATCH_UNIT_NAME")
//    private String DISPATCH_UNIT_NAME;
//
//    @ExcelProperty("IS_BYPASS_EMERGENCY")
//    private String IS_BYPASS_EMERGENCY;
//
//    @ExcelProperty("BYPASS_EMERGENCY_CODE")
//    private String BYPASS_EMERGENCY_CODE;
//
//    @ExcelProperty("ARRIVED_EMERGENCY_TIME")
//    private Date ARRIVED_EMERGENCY_TIME;
//
//    @ExcelProperty("IS_BYPASS_CCU")
//    private String IS_BYPASS_CCU;
//
//    @ExcelProperty("ARRIVED_CCU_DATE")
//    private Date ARRIVED_CCU_DATE;
//
//    @ExcelProperty("HOSPITAL_NAME")
//    private String HOSPITAL_NAME;
//
//    @ExcelProperty("OUTHOSPITAL_VISIT_TIME")
//    private Date OUTHOSPITAL_VISIT_TIME;
//
//    @ExcelProperty("TRANSFER_TIME")
//    private Date TRANSFER_TIME;
//
//    @ExcelProperty("AMBULANCE_ARRIVED_TIME")
//    private Date AMBULANCE_ARRIVED_TIME;
//
//    @ExcelProperty("LEAVE_OUTHOSPITAL_TIME")
//    private Date LEAVE_OUTHOSPITAL_TIME;
//
//    @ExcelProperty("ARRIVED_SCENE_TIME")
//    private Date ARRIVED_SCENE_TIME;
//
//    @ExcelProperty("ARRIVED_HOSPITAL_TIME")
//    private Date ARRIVED_HOSPITAL_TIME;
//
//    @ExcelProperty("INHOSPITAL_ADMISSION_TIME")
//    private Date INHOSPITAL_ADMISSION_TIME;
//
//    @ExcelProperty("ATTACK_DEPARTMENT")
//    private String ATTACK_DEPARTMENT;
//
//    @ExcelProperty("CONSULTATION_TIME")
//    private Date CONSULTATION_TIME;
//
//    @ExcelProperty("LEAVE_DEPARTMENT_TIME")
//    private Date LEAVE_DEPARTMENT_TIME;
//
//    @ExcelProperty("FIRST_MC_CODE")
//    private String FIRST_MC_CODE;
//
//    @ExcelProperty("FIRST_MC_NAME")
//    private String FIRST_MC_NAME;
//
//    /**
//     * 首次医疗接触时间
//     */
//    @ExcelProperty("FIRST_MC_TIME")
//    private Date FIRST_MC_TIME;
//
//    @ExcelProperty("PHEP_ECG_TIME")
//    private Date PHEP_ECG_TIME;
//
//    /**
//     * 院内首份心电图时间
//     */
//    @ExcelProperty("INHOSPITAL_ECG_TIME")
//    private Date INHOSPITAL_ECG_TIME;
//
//    /**
//     * 心电图诊断时间
//     */
//    @ExcelProperty("ECG_DIAGNOSE_TIME")
//    private Date ECG_DIAGNOSE_TIME;
//
//    @ExcelProperty("IS_REMOTE_ECGTRAN_CHECKED")
//    private String IS_REMOTE_ECGTRAN_CHECKED;
//
//    @ExcelProperty("TRAN_DATE")
//    private Date TRAN_DATE;
//
//    /**
//     * 是否远程心电图输送
//     */
//    @ExcelProperty("IS_REMOTE_ECGTRAN")
//    private String IS_REMOTE_ECGTRAN;
//
//    @ExcelProperty("KILLIP_LEVEL")
//    private String KILLIP_LEVEL;
//
//    @ExcelProperty("SAMPLING_TIME")
//    private Date SAMPLING_TIME;
//
//    @ExcelProperty("REPORT_TIME")
//    private Date REPORT_TIME;
//
//    @ExcelProperty("CTNI_VALUE")
//    private String CTNI_VALUE;
//
//    @ExcelProperty("CR_VALUE")
//    private String CR_VALUE;
//
//    @ExcelProperty("DIAGNOSIS_TIME")
//    private Date DIAGNOSIS_TIME;
//
//    @ExcelProperty("ACS_DELIVERY_TIME")
//    private Date ACS_DELIVERY_TIME;
//
//    @ExcelProperty("ASPIRIN_DOSE")
//    private String ASPIRIN_DOSE;
//
//    @ExcelProperty("ACS_DRUG_TYPE")
//    private String ACS_DRUG_TYPE;
//
//    @ExcelProperty("ACS_DRUG_DOSE")
//    private String ACS_DRUG_DOSE;
//
//    /**
//     * 是否溶栓治疗 选填（1 有 0 无，传1,0或空值）
//     */
//    @ExcelProperty("IS_THROMBOLYSIS")
//    private String IS_THROMBOLYSIS;
//
//
//    @ExcelProperty("GRACE_VALUE")
//    private String GRACE_VALUE;
//
//    @ExcelProperty("RISK_LAMINATION")
//    private String RISK_LAMINATION;
//
//    /**
//     * 描述
//     */
//    @ExcelProperty("DIAGNOSIS_DESC")
//    private String DIAGNOSIS_DESC;
//
//    @ExcelProperty("OUTCOME_DESC")
//    private String OUTCOME_DESC;
//
//    @ExcelProperty("PATIENT_CASE_NOTE")
//    private String PATIENT_CASE_NOTE;
//
//    @ExcelProperty("THROMBOLYSIS_TYPE")
//    private String THROMBOLYSIS_TYPE;
//
//    @ExcelProperty("SIGN_AGREE_TIME")
//    private Date SIGN_AGREE_TIME;
//
//    @ExcelProperty("THROM_START_TIME")
//    private Date THROM_START_TIME;
//
//    @ExcelProperty("THROM_END_TIME")
//    private Date THROM_END_TIME;
//
//    @ExcelProperty("THROM_DRUG_TYPE")
//    private String THROM_DRUG_TYPE;
//
//    @ExcelProperty("THROM_DRUG_CODE")
//    private String THROM_DRUG_CODE;
//
//    @ExcelProperty("IS_REPATENCY")
//    private String IS_REPATENCY;
//
//    @ExcelProperty("THROM_RESULT_DESC")
//    private String THROM_RESULT_DESC;
//
//    @ExcelProperty("START_AGREE_TIME")
//    private Date START_AGREE_TIME;
//
//    @ExcelProperty("PHEP_FILE_NAME")
//    private String PHEP_FILE_NAME;
//
//    @ExcelProperty("IN_FILE_NAME")
//    private String IN_FILE_NAME;
//
//    @ExcelProperty("CP_DIAGNOSIS_NAME")
//    private String CP_DIAGNOSIS_NAME;
//
//    @ExcelProperty("BYPASS_EMERGENCY_LEAVE_TIME")
//    private Date BYPASS_EMERGENCY_LEAVE_TIME;
//
//    @ExcelProperty("KILLIP_UNIT")
//    private String KILLIP_UNIT;
//
//    @ExcelProperty("HANDLE_TIME")
//    private Date HANDLE_TIME;
//
//    @ExcelProperty("PATIENT_OUTCOME")
//    private String PATIENT_OUTCOME;
//
//    @ExcelProperty("IS_ANTICOAGULATION")
//    private String IS_ANTICOAGULATION;
//
//    @ExcelProperty("SCREENING")
//    private String SCREENING;
//
//    @ExcelProperty("IS_DIRECT")
//    private String IS_DIRECT;
//
//    @ExcelProperty("EMERGENCY_LOG")
//    private String EMERGENCY_LOG;
//
//    @ExcelProperty("CTNI_UNIT")
//    private String CTNI_UNIT;
//
//    @ExcelProperty("CR_UNIT")
//    private String CR_UNIT;
//
//    @ExcelProperty("THROM_TREATMENT_PLACE")
//    private String THROM_TREATMENT_PLACE;
//
//    @ExcelProperty("HANDLE_WAY")
//    private String HANDLE_WAY;
//
//    @ExcelProperty("PHEP_ECG_IMAGE_BASE64")
//    private String PHEP_ECG_IMAGE_BASE64;
//
//    @ExcelProperty("INHOSPITAL_ECG_IMAGE_BASE64")
//    private String INHOSPITAL_ECG_IMAGE_BASE64;
//
//    @ExcelProperty("ANTICOAGULATION_DATE")
//    private Date ANTICOAGULATION_DATE;
//
//    @ExcelProperty("ANTICOAGULATION_DRUG")
//    private String ANTICOAGULATION_DRUG;
//
//    @ExcelProperty("ANTICOAGULATION_UNIT")
//    private String ANTICOAGULATION_UNIT;
//
//    @ExcelProperty("RD")
//    private String RD;
//
//    @ExcelProperty("DSD")
//    private String DSD;
//
//    @ExcelProperty("NSD")
//    private String NSD;
//
//    @ExcelProperty("PD")
//    private String PD;
//
//    @ExcelProperty("MD")
//    private String MD;
//
//    @ExcelProperty("SSD")
//    private String SSD;
//
//    @ExcelProperty("OTHER")
//    private String OTHER;
//
//    @ExcelProperty("CTNT_VALUE")
//    private String CTNT_VALUE;
//
//    @ExcelProperty("CTNT_UNIT")
//    private String CTNT_UNIT;
//
//    @ExcelProperty("CTNI_STATUS")
//    private String CTNI_STATUS;
//
//    @ExcelProperty("CTNT_STATUS")
//    private String CTNT_STATUS;
//
//    @ExcelProperty("DEPARTMENT")
//    private String DEPARTMENT;
//
//    @ExcelProperty("FIRST_TREATMENT_TIME")
//    private Date FIRST_TREATMENT_TIME;
//
//    @ExcelProperty("IS_EMPCI")
//    private String IS_EMPCI;
//
//    @ExcelProperty("IS_THROMBOLYSIS_DT")
//    private String IS_THROMBOLYSIS_DT;
//
//    @ExcelProperty("IS_REPCI")
//    private String IS_REPCI;
//
//    @ExcelProperty("IS_EMRADIOGRAPHY")
//    private String IS_EMRADIOGRAPHY;
//
//    @ExcelProperty("IS_ELPCI")
//    private String IS_ELPCI;
//
//    @ExcelProperty("IS_ELRADIOGRAPHY")
//    private String IS_ELRADIOGRAPHY;
//
//    @ExcelProperty("IS_CABG")
//    private String IS_CABG;
//
//    @ExcelProperty("IS_NOREPERFUSION")
//    private String IS_NOREPERFUSION;
//
//    @ExcelProperty("IS_REPOTHER")
//    private String IS_REPOTHER;
//
//    @ExcelProperty("OTHER_TREATMENT_MEASURE")
//    private String OTHER_TREATMENT_MEASURE;
//
//    @ExcelProperty("INTERVENTION_PERSON")
//    private String INTERVENTION_PERSON;
//
//    @ExcelProperty("DECISION_OPERATION_TIME")
//    private Date DECISION_OPERATION_TIME;
//
//    @ExcelProperty("START_CONDUIT_TIME")
//    private Date START_CONDUIT_TIME;
//
//    @ExcelProperty("ACTIVATE_CONDUIT_TIME")
//    private Date ACTIVATE_CONDUIT_TIME;
//
//    @ExcelProperty("ARRIVE_CONDUIT_TIME")
//    private Date ARRIVE_CONDUIT_TIME;
//
//    @ExcelProperty("START_PUNCTURE_TIME")
//    private Date START_PUNCTURE_TIME;
//
//    @ExcelProperty("START_RADIOGRAPHY_TIME")
//    private Date START_RADIOGRAPHY_TIME;
//
//    @ExcelProperty("END_RADIOGRAPHY_TIME")
//    private Date END_RADIOGRAPHY_TIME;
//
//    @ExcelProperty("AGAIN_SIGN_AGREE_TIME")
//    private Date AGAIN_SIGN_AGREE_TIME;
//
//    @ExcelProperty("BALLOON_EXPANSION_TIME")
//    private Date BALLOON_EXPANSION_TIME;
//
//    @ExcelProperty("START_OPERATION_TIME")
//    private Date START_OPERATION_TIME;
//
//    @ExcelProperty("END_OPERATION_TIME")
//    private Date END_OPERATION_TIME;
//
//    @ExcelProperty("DTWOB_TIME")
//    private String DTWOB_TIME;
//
//    @ExcelProperty("IS_DELAY")
//    private String IS_DELAY;
//
//    @ExcelProperty("DIAGNOSIS_UNIT_CODE_DT")
//    private String DIAGNOSIS_UNIT_CODE_DT;
//
//    @ExcelProperty("THROM_START_TIME_DT")
//    private Date THROM_START_TIME_DT;
//
//    @ExcelProperty("THROM_END_TIME_DT")
//    private Date THROM_END_TIME_DT;
//
//    @ExcelProperty("THROM_DRUG_TYPE_DT")
//    private String THROM_DRUG_TYPE_DT;
//
//    @ExcelProperty("THROM_DRUG_CODE_DT")
//    private String THROM_DRUG_CODE_DT;
//
//    @ExcelProperty("IS_REPATENCY_DT")
//    private String IS_REPATENCY_DT;
//
//    @ExcelProperty("THROM_RESULT_DESC_DT")
//    private String THROM_RESULT_DESC_DT;
//
//    @ExcelProperty("PREOPERATIVE_TIMI_LEVEL")
//    private String PREOPERATIVE_TIMI_LEVEL;
//
//    @ExcelProperty("POSTOPERATIVE_TIMI_LEVEL")
//    private String POSTOPERATIVE_TIMI_LEVEL;
//
//    @ExcelProperty("DECISION_CABG_TIME")
//    private Date DECISION_CABG_TIME;
//
//    @ExcelProperty("START_CABG_TIME")
//    private Date START_CABG_TIME;
//
//    @ExcelProperty("END_CABG_TIME")
//    private Date END_CABG_TIME;
//
//    @ExcelProperty("PERFUSION_MEASURE_CODE")
//    private String PERFUSION_MEASURE_CODE;
//
//    @ExcelProperty("PERFUSION_MEASURE_DESC")
//    private String PERFUSION_MEASURE_DESC;
//
//    @ExcelProperty("START_INTERVENTION_DATE")
//    private Date START_INTERVENTION_DATE;
//
//    @ExcelProperty("END_INTERVENTION_DATE")
//    private Date END_INTERVENTION_DATE;
//
//    @ExcelProperty("DELAY_REASON")
//    private String DELAY_REASON;
//
//    @ExcelProperty("SIGN_OPERATE_AGREE_TIME")
//    private Date SIGN_OPERATE_AGREE_TIME;
//
//    @ExcelProperty("OPERATION_RESULT")
//    private String OPERATION_RESULT;
//
//    @ExcelProperty("STAND_RID_DATE")
//    private Date STAND_RID_DATE;
//
//    @ExcelProperty("START_TREATE_DATE")
//    private Date START_TREATE_DATE;
//
//    @ExcelProperty("CCU_INTO_DATE")
//    private Date CCU_INTO_DATE;
//
//    @ExcelProperty("TREATMENT_STRATEGY_CODE")
//    private String TREATMENT_STRATEGY_CODE;
//
//    @ExcelProperty("INTERLAYER_TYPE")
//    private String INTERLAYER_TYPE;
//
//    @ExcelProperty("ECC_CONSULTATION_DATE")
//    private Date ECC_CONSULTATION_DATE;
//
//    @ExcelProperty("IMCD_NOTICE_DATE")
//    private Date IMCD_NOTICE_DATE;
//
//    @ExcelProperty("IMCD_CONSULTATION_DATE")
//    private Date IMCD_CONSULTATION_DATE;
//
//    @ExcelProperty("CHECK_RESULT_DATE")
//    private Date CHECK_RESULT_DATE;
//
//    @ExcelProperty("CDU_CHECK_DATE")
//    private Date CDU_CHECK_DATE;
//
//    @ExcelProperty("CDU_ARRIVE_DATE")
//    private Date CDU_ARRIVE_DATE;
//
//    @ExcelProperty("IS_CDU")
//    private String IS_CDU;
//
//    @ExcelProperty("CT_REPORT_DATE")
//    private Date CT_REPORT_DATE;
//
//    @ExcelProperty("CT_SCAN_DATE")
//    private Date CT_SCAN_DATE;
//
//    @ExcelProperty("CT_ARRIVE_DATE")
//    private Date CT_ARRIVE_DATE;
//
//    @ExcelProperty("USER_ARRIVE_DATE")
//    private Date USER_ARRIVE_DATE;
//
//    @ExcelProperty("CT_FINISH_DATE")
//    private Date CT_FINISH_DATE;
//
//    @ExcelProperty("CT_NOTICE_DATE")
//    private Date CT_NOTICE_DATE;
//
//    @ExcelProperty("IS_ECT")
//    private String IS_ECT;
//
//    @ExcelProperty("ANTI_TREATMENT_DATE")
//    private Date ANTI_TREATMENT_DATE;
//
//    @ExcelProperty("NSTEMI_TYPE")
//    private String NSTEMI_TYPE;
//
//    @ExcelProperty("THROM_CHECK")
//    private String THROM_CHECK;
//
//    @ExcelProperty("INTENSIFY_STATINS_TREAT")
//    private String INTENSIFY_STATINS_TREAT;
//
//    @ExcelProperty("RECEPTOR_RETARDANT_USING")
//    private String RECEPTOR_RETARDANT_USING;
//
//    @ExcelProperty("NOTICE_CDU_TIME")
//    private Date NOTICE_CDU_TIME;
//
//    @ExcelProperty("THROM_START_AGREE_TIME")
//    private Date THROM_START_AGREE_TIME;
//
//    @ExcelProperty("THROM_SIGN_AGREE_TIME")
//    private Date THROM_SIGN_AGREE_TIME;
//
//    @ExcelProperty("TIME_INTERVAL")
//    private String TIME_INTERVAL;
//
//    @ExcelProperty("START_OPERATE_AGREE_TIME")
//    private Date START_OPERATE_AGREE_TIME;
//
//    @ExcelProperty("ACTUAL_INTERVENTION_DATE")
//    private Date ACTUAL_INTERVENTION_DATE;
//
//    @ExcelProperty("PERFUSION_MEASURE_OTHER")
//    private String PERFUSION_MEASURE_OTHER;
//
//    @ExcelProperty("IS_MRI")
//    private String IS_MRI;
//
//    @ExcelProperty("SUCCESS_PUNCTURE_TIME")
//    private Date SUCCESS_PUNCTURE_TIME;
//
//    @ExcelProperty("IS_TPCI")
//    private String IS_TPCI;
//
//    @ExcelProperty("IS_HEART_FAILURE")
//    private String IS_HEART_FAILURE;
//
//    @ExcelProperty("HOD")
//    private String HOD;
//
//    @ExcelProperty("TOTAL_COST")
//    private String TOTAL_COST;
//
//    @ExcelProperty("OUTCOME_CODE")
//    private String OUTCOME_CODE;
//
//    @ExcelProperty("OUTCOME_NAME")
//    private String OUTCOME_NAME;
//
//    @ExcelProperty("LEAVE_TIME")
//    private Date LEAVE_TIME;
//
//    @ExcelProperty("TREATMENT_RESULT_CODE")
//    private String TREATMENT_RESULT_CODE;
//
//    @ExcelProperty("TREATMENT_RESULT_NAME")
//    private String TREATMENT_RESULT_NAME;
//
//    @ExcelProperty("HAND_TIME")
//    private Date HAND_TIME;
//
//    @ExcelProperty("HAND_HOSPITAL_NAME")
//    private String HAND_HOSPITAL_NAME;
//
//    @ExcelProperty("DEATH_TIME")
//    private Date DEATH_TIME;
//
//    @ExcelProperty("DEATH_CAUSE_CODE")
//    private String DEATH_CAUSE_CODE;
//
//    @ExcelProperty("DEATH_CAUSE_DESC")
//    private String DEATH_CAUSE_DESC;
//
//    @ExcelProperty("REMARK")
//    private String REMARK;
//
//    @ExcelProperty("MEDICAL_DESC")
//    private String MEDICAL_DESC;
//
//    @ExcelProperty("TRANSFER_DATE")
//    private Date TRANSFER_DATE;
//
//    @ExcelProperty("ADMISSION_DEPT")
//    private String ADMISSION_DEPT;
//
//    @ExcelProperty("TRANSFER_REASON")
//    private String TRANSFER_REASON;
//
//    @ExcelProperty("IS_NET_HOSPITAL")
//    private String IS_NET_HOSPITAL;
//
//    @ExcelProperty("IS_TRANS_PCI")
//    private String IS_TRANS_PCI;
//
//    @ExcelProperty("NO_TRANS_PCI_REASON")
//    private String NO_TRANS_PCI_REASON;
//
//    @ExcelProperty("IS_DIRECT_CATHETER")
//    private String IS_DIRECT_CATHETER;
//
//    @ExcelProperty("OUT_GRUG_DAPT")
//    private String OUT_GRUG_DAPT;
//
//    @ExcelProperty("OUT_GRUG_ACEIORARB")
//    private String OUT_GRUG_ACEIORARB;
//
//    @ExcelProperty("OUT_DRUG_STATINS")
//    private String OUT_DRUG_STATINS;
//
//    @ExcelProperty("OUT_DRUG_RETARDANT")
//    private String OUT_DRUG_RETARDANT;
}
