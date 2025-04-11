package com.mdsd.cloud.util.dto.trauma;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
@ToString
public class TraumaExcel {

    @ExcelProperty("REGISTER_ID")
    private String REGISTER_ID;

    @ExcelProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @ExcelProperty("PATIENT_NAME")
    private String PATIENT_NAME;

//    @ExcelProperty("VISIT_TYPE")
//    private String VISIT_TYPE;

    @ExcelProperty("GENDER")
    private String GENDER;

//    @ExcelProperty("BIRTHDAY")
//    private Date BIRTHDAY;
//
//    @ExcelProperty("JOB")
//    private String JOB;
//
//    @ExcelProperty("PHONE_NO")
//    private String PHONE_NO;
//
//    @ExcelProperty("ID_NO")
//    private String ID_NO;
//
//    @ExcelProperty("INCIDENCE_ADDRESS")
//    private String INCIDENCE_ADDRESS;
//
//    @ExcelProperty("REGISTER_NO")
//    private String REGISTER_NO;
//
//    @ExcelProperty("OUTPATIENT_ID")
//    private String OUTPATIENT_ID;
//
//    @ExcelProperty("INPATIENT_ID")
//    private String INPATIENT_ID;
//
    @ExcelProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @ExcelProperty("UPDATE_DATE")
    private Date UPDATE_DATE;
//
//    @ExcelProperty("ARCHIVE_DATE")
//    private Date ARCHIVE_DATE;
//
//    @ExcelProperty("PROCESS_STATUS")
//    private Integer PROCESS_STATUS;
//
    @ExcelProperty("STATUS")
    private Integer STATUS;
//
//    @ExcelProperty("ISS_SCORE")
//    private Integer ISS_SCORE;
//
//    @ExcelProperty("DIAGNOSE_FLAG")
//    private Integer DIAGNOSE_FLAG;
//
    @ExcelProperty("REGISTER_TIME")
    private Date REGISTER_TIME;
//
//    @ExcelProperty("IS_SICU")
//    private String IS_SICU;
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
//    @ExcelProperty("BASIC_ILLNESS")
//    private String BASIC_ILLNESS;
//
//    @ExcelProperty("BASIC_ILLNESS_OTHER")
//    private String BASIC_ILLNESS_OTHER;
//
//    @ExcelProperty("DIAGNOSTIC_TIME")
//    private Date DIAGNOSTIC_TIME;
//
//    @ExcelProperty("DIAGNOSTIC_DOCTOR")
//    private String DIAGNOSTIC_DOCTOR;
//
//    @ExcelProperty("INITIAL_DIAGNOSIS_DETAIL")
//    private String INITIAL_DIAGNOSIS_DETAIL;
//
//    @ExcelProperty("CONDITION_LEVEL")
//    private String CONDITION_LEVEL;
//
//    @ExcelProperty("TREATMENT_MEASURES")
//    private String TREATMENT_MEASURES;
//
//    @ExcelProperty("BLOOD_EXPANSION")
//    private String BLOOD_EXPANSION;
//
//    @ExcelProperty("USE_VASOCONSTRICTORS")
//    private String USE_VASOCONSTRICTORS;
//
//    @ExcelProperty("CALL_TIME")
//    private Date CALL_TIME;
//
//    @ExcelProperty("ANSWERING_ALARM")
//    private Date ANSWERING_ALARM;
//
//    @ExcelProperty("AMBULANCES_START")
//    private Date AMBULANCES_START;
//
//    @ExcelProperty("AMBULANCES_ARRIVE")
//    private Date AMBULANCES_ARRIVE;
//
//    @ExcelProperty("PHEPINFO_TRANSFER")
//    private Date PHEPINFO_TRANSFER;
//
//    @ExcelProperty("AMBULANCES_LEAVE")
//    private Date AMBULANCES_LEAVE;
//
//    @ExcelProperty("AMBULANCES_ARRIVE_HOSPITAL")
//    private Date AMBULANCES_ARRIVE_HOSPITAL;
//
//    @ExcelProperty("t")
//    private String t;
//
//    @ExcelProperty("SBP")
//    private String SBP;
//
//    @ExcelProperty("DBP")
//    private String DBP;
//
//    @ExcelProperty("RR")
//    private String RR;
//
//    @ExcelProperty("BREATH")
//    private String BREATH;
//
//    @ExcelProperty("SPO2")
//    private String SPO2;
//
//    @ExcelProperty("PLUSE")
//    private String PLUSE;
//
//    @ExcelProperty("MEDICAL_HISTORY")
//    private String MEDICAL_HISTORY;
//
//    @ExcelProperty("MEDICAL_HISTORY_ETC")
//    private String MEDICAL_HISTORY_ETC;
//
//    @ExcelProperty("HISTORYDRUGUSE")
//    private String HISTORYDRUGUSE;
//
//    @ExcelProperty("ANAPHYLAXIS")
//    private String ANAPHYLAXIS;
//
//    @ExcelProperty("HARM_REASON")
//    private String HARM_REASON;
//
//    @ExcelProperty("HARM_CARS")
//    private String HARM_CARS;
//
//    @ExcelProperty("WOUND_REASON")
//    private String WOUND_REASON;
//
//    @ExcelProperty("WOUND_CARS")
//    private String WOUND_CARS;
//
//    @ExcelProperty("WOUND_PARS")
//    private String WOUND_PARS;
//
//    @ExcelProperty("WOUND_TYPE")
//    private String WOUND_TYPE;
//
//    @ExcelProperty("FALLING_INJURY")
//    private String FALLING_INJURY;
//
//    @ExcelProperty("WEAPON_DAMAGE")
//    private String WEAPON_DAMAGE;
//
//    @ExcelProperty("OTHER_DAMAGE")
//    private String OTHER_DAMAGE;
//
//    @ExcelProperty("TEAM_START")
//    private Date TEAM_START;
//
//    @ExcelProperty("ENTER_RESCUE")
//    private Date ENTER_RESCUE;
//
//    @ExcelProperty("RECOVERY_START_TIME")
//    private Date RECOVERY_START_TIME;
//
//    @ExcelProperty("ARTIFICIAL_AIRWAY_TIME")
//    private Date ARTIFICIAL_AIRWAY_TIME;
//
//    @ExcelProperty("INFUSION_PATHWAY_TIME")
//    private Date INFUSION_PATHWAY_TIME;
//
//    @ExcelProperty("ACID_TIME")
//    private Date ACID_TIME;
//
//    @ExcelProperty("LAB_TIME")
//    private Date LAB_TIME;
//
//    @ExcelProperty("FIRST_BLOOD_TIME")
//    private Date FIRST_BLOOD_TIME;
//
//    @ExcelProperty("EMERGENCY_ECG_TIME")
//    private Date EMERGENCY_ECG_TIME;
//
//    @ExcelProperty("x_RAY_TIME")
//    private Date x_RAY_TIME;
//
//    @ExcelProperty("LEAVE_RECOVERY_ROOM_TIME")
//    private Date LEAVE_RECOVERY_ROOM_TIME;
//
//    @ExcelProperty("PRIMATY_ASSESS_ID")
//    private String PRIMATY_ASSESS_ID;
//
//    @ExcelProperty("AIRWAY_CLEAR")
//    private BigDecimal AIRWAY_CLEAR;
//
//    @ExcelProperty("NECK_FIXED")
//    private BigDecimal NECK_FIXED;
//
//    @ExcelProperty("AIRWAY_SUSPICIOUS_WOUND")
//    private BigDecimal AIRWAY_SUSPICIOUS_WOUND;
//
//    @ExcelProperty("BREATH_SYMPTOM")
//    private String BREATH_SYMPTOM;
//
//    @ExcelProperty("BREATH_FREQUENCY")
//    private String BREATH_FREQUENCY;
//
//    @ExcelProperty("CYANOSIS")
//    private BigDecimal CYANOSIS;
//
//    @ExcelProperty("CIRCULATION_SYMPTOM")
//    private String CIRCULATION_SYMPTOM;
//
//    @ExcelProperty("HEART_RATE")
//    private String HEART_RATE;
//
//    @ExcelProperty("AWARENESS")
//    private BigDecimal AWARENESS;
//
//    @ExcelProperty("LIMBS_SYMPTOM")
//    private BigDecimal LIMBS_SYMPTOM;
//
//    @ExcelProperty("EYE_LEFT")
//    private String EYE_LEFT;
//
//    @ExcelProperty("EYE_RIGHT")
//    private String EYE_RIGHT;
//
//    @ExcelProperty("EYE_EQUAL_SIZE")
//    private BigDecimal EYE_EQUAL_SIZE;
//
//    @ExcelProperty("EYE_REACT_LIGHT")
//    private BigDecimal EYE_REACT_LIGHT;
//
//    @ExcelProperty("REMOVE_CLOTH")
//    private BigDecimal REMOVE_CLOTH;
//
//    @ExcelProperty("SURFACE_WOUND")
//    private BigDecimal SURFACE_WOUND;
//
//    @ExcelProperty("GCS_SCORE")
//    private BigDecimal GCS_SCORE;
//
//    @ExcelProperty("RTS_SCORE")
//    private BigDecimal RTS_SCORE;
//
//    @ExcelProperty("PAIN_SCORE")
//    private BigDecimal PAIN_SCORE;
//
//    @ExcelProperty("ASSESS_DOCTOR")
//    private String ASSESS_DOCTOR;
//
//    @ExcelProperty("ASSESS_TIME")
//    private Date ASSESS_TIME;
//
//    @ExcelProperty("ADVANCED_AIRWAY")
//    private BigDecimal ADVANCED_AIRWAY;
//
//    @ExcelProperty("AIRWAY_AIR")
//    private BigDecimal AIRWAY_AIR;
//
//    @ExcelProperty("PATHWAY")
//    private BigDecimal PATHWAY;
//
//    @ExcelProperty("USE_CRYSTAL")
//    private BigDecimal USE_CRYSTAL;
//
//    @ExcelProperty("CRYSTAL_DOSAGE")
//    private BigDecimal CRYSTAL_DOSAGE;
//
//    @ExcelProperty("USE_GUM")
//    private BigDecimal USE_GUM;
//
//    @ExcelProperty("GUM_DOSAGE")
//    private BigDecimal GUM_DOSAGE;
//
//    @ExcelProperty("USE_BLOOD_PRODUCT")
//    private BigDecimal USE_BLOOD_PRODUCT;
//
//    @ExcelProperty("RED_HANG_DOSAGE")
//    private BigDecimal RED_HANG_DOSAGE;
//
//    @ExcelProperty("PLASMA_DOSAGE")
//    private BigDecimal PLASMA_DOSAGE;
//
//    @ExcelProperty("ALBUMIN_DOASGE")
//    private BigDecimal ALBUMIN_DOASGE;
//
//    @ExcelProperty("OTHER_BLOOD_PRODUCT")
//    private String OTHER_BLOOD_PRODUCT;
//
//    @ExcelProperty("VASOACTIVE_DRUG")
//    private BigDecimal VASOACTIVE_DRUG;
//
//    @ExcelProperty("CARBAMIC")
//    private BigDecimal CARBAMIC;
//
//    @ExcelProperty("PELVIS_FIXED")
//    private BigDecimal PELVIS_FIXED;
//
//    @ExcelProperty("ANALGESIA")
//    private BigDecimal ANALGESIA;
//
//    @ExcelProperty("ANALGESIA_DRUG")
//    private String ANALGESIA_DRUG;
//
//    @ExcelProperty("POCT")
//    private String POCT;
//
//    @ExcelProperty("USE_FAST")
//    private BigDecimal USE_FAST;
//
//    @ExcelProperty("PERICARDIAL_HYDROPS")
//    private BigDecimal PERICARDIAL_HYDROPS;
//
//    @ExcelProperty("CHEST_HYDROPS")
//    private BigDecimal CHEST_HYDROPS;
//
//    @ExcelProperty("CHEST_HYDROPS_SITE")
//    private BigDecimal CHEST_HYDROPS_SITE;
//
//    @ExcelProperty("USE_DPL")
//    private BigDecimal USE_DPL;
//
//    @ExcelProperty("USE_XRAY")
//    private BigDecimal USE_XRAY;
//
//    @ExcelProperty("USE_EXAM")
//    private BigDecimal USE_EXAM;
//
//    @ExcelProperty("USE_CT")
//    private BigDecimal USE_CT;
//
//    @ExcelProperty("SHOCKED")
//    private BigDecimal SHOCKED;
//
//    @ExcelProperty("SHOCK_TYPE")
//    private String SHOCK_TYPE;
//
//    @ExcelProperty("BLEED_SHOCK_TYPE")
//    private String BLEED_SHOCK_TYPE;
//
//    @ExcelProperty("BLEED_AMOUNT")
//    private BigDecimal BLEED_AMOUNT;
//
//    @ExcelProperty("GO_FOR")
//    private BigDecimal GO_FOR;
//
//    @ExcelProperty("CHIEF_COMPLAINT")
//    private String CHIEF_COMPLAINT;
//
//    @ExcelProperty("CT_TYPE")
//    private BigDecimal CT_TYPE;
//
//    @ExcelProperty("CT_TYPE_ITEM")
//    private String CT_TYPE_ITEM;
//
//    @ExcelProperty("CT_HEAD")
//    private BigDecimal CT_HEAD;
//
//    @ExcelProperty("CT_DRAW")
//    private BigDecimal CT_DRAW;
//
//    @ExcelProperty("CT_CHEST")
//    private BigDecimal CT_CHEST;
//
//    @ExcelProperty("CT_ABDOMEN")
//    private BigDecimal CT_ABDOMEN;
//
//    @ExcelProperty("CT_PELVIS")
//    private BigDecimal CT_PELVIS;
//
//    @ExcelProperty("CT_START_TIME")
//    private Date CT_START_TIME;
//
//    @ExcelProperty("CT_END_TIME")
//    private Date CT_END_TIME;
//
//    @ExcelProperty("REBACK_RECOVERY_TIME")
//    private Date REBACK_RECOVERY_TIME;
//
//    @ExcelProperty("CT_REPORT_TIME")
//    private Date CT_REPORT_TIME;
//
//    @ExcelProperty("REQUEST_CONSULTATION_TIME")
//    private Date REQUEST_CONSULTATION_TIME;
//
//    @ExcelProperty("DOCTOR_ARRIVE_TIME")
//    private Date DOCTOR_ARRIVE_TIME;
//
//    @ExcelProperty("ENTER_DIAGNOSTIC_TIME")
//    private Date ENTER_DIAGNOSTIC_TIME;
//
//    @ExcelProperty("COMPLETE_DRAINAG_TIME")
//    private Date COMPLETE_DRAINAG_TIME;
//
//    @ExcelProperty("OPERATION_TIME")
//    private Date OPERATION_TIME;
//
//    @ExcelProperty("INFORMED_CONSENT_TIME")
//    private Date INFORMED_CONSENT_TIME;
//
//    @ExcelProperty("RECOVERY_END_TIME")
//    private Date RECOVERY_END_TIME;
//
//    @ExcelProperty("LEAVE_RECOVERY_TIME")
//    private Date LEAVE_RECOVERY_TIME;
//
//    @ExcelProperty("DIAGNOSE_TIME")
//    private Date DIAGNOSE_TIME;
//
//    @ExcelProperty("DIAGNOSE_DOCTOR")
//    private String DIAGNOSE_DOCTOR;
//
//    @ExcelProperty("PATIENTNO")
//    private String PATIENTNO;
//
//    @ExcelProperty("INSPECTION_TYPE")
//    private String INSPECTION_TYPE;
//
//    @ExcelProperty("INSPECTION_TIME")
//    private Date INSPECTION_TIME;
//
//    @ExcelProperty("MEDICATION_TYPE")
//    private String MEDICATION_TYPE;
//
//    @ExcelProperty("MEDICATION_TIME")
//    private Date MEDICATION_TIME;
//
//    @ExcelProperty("OPERATION_ID")
//    private String OPERATION_ID;
//
//    @ExcelProperty("BLOOD_TYPE")
//    private String BLOOD_TYPE;
//
//    @ExcelProperty("ANAESTYPE")
//    private String ANAESTYPE;
//
//    @ExcelProperty("ANAES_DOCTOR_ID")
//    private String ANAES_DOCTOR_ID;
//
//    @ExcelProperty("ANAES_DOCTOR_NAME")
//    private String ANAES_DOCTOR_NAME;
//
//    @ExcelProperty("OPERATION_SEC")
//    private String OPERATION_SEC;
//
//    @ExcelProperty("DOCTOR_ID")
//    private String DOCTOR_ID;
//
//    @ExcelProperty("DOCTOR_NAME")
//    private String DOCTOR_NAME;
//
//    @ExcelProperty("OPERATION_TYPE")
//    private String OPERATION_TYPE;
//
//    @ExcelProperty("BRACKET_TYPE")
//    private String BRACKET_TYPE;
//
//    @ExcelProperty("OPERATION_PROGRAM")
//    private String OPERATION_PROGRAM;
//
//    @ExcelProperty("CONTRAST_DOSE")
//    private String CONTRAST_DOSE;
//
//    @ExcelProperty("DIAGNOSIS")
//    private String DIAGNOSIS;
//
//    @ExcelProperty("OPINION")
//    private String OPINION;
//
//    @ExcelProperty("BLOOD_VOLUME")
//    private String BLOOD_VOLUME;
//
//    @ExcelProperty("CONTRAST_NAME")
//    private String CONTRAST_NAME;
//
//    @ExcelProperty("INTO_OPERATION_ROOM_TIME")
//    private Date INTO_OPERATION_ROOM_TIME;
//
//    @ExcelProperty("OPERATION_START_TIME")
//    private Date OPERATION_START_TIME;
//
//    @ExcelProperty("OPERATION_END_TIME")
//    private Date OPERATION_END_TIME;
//
//    @ExcelProperty("LEAVE_OPERATION_ROOM_TIME")
//    private Date LEAVE_OPERATION_ROOM_TIME;
//
//    @ExcelProperty("ID")
//    private String ID;
//
//    @ExcelProperty("PATIENT_ID")
//    private String PATIENT_ID;
//
//    @ExcelProperty("CLINIC_NO")
//    private String CLINIC_NO;
//
//    @ExcelProperty("ISUSE_RESP")
//    private String ISUSE_RESP;
//
//    @ExcelProperty("CRT")
//    private String CRT;
//
//    @ExcelProperty("ICU_TIME")
//    private Date ICU_TIME;
//
//    @ExcelProperty("ALL_BLOOD")
//    private BigDecimal ALL_BLOOD;
//
//    @ExcelProperty("PHEP_BLOOD")
//    private BigDecimal PHEP_BLOOD;
//
//    @ExcelProperty("PHEP_TYPE")
//    private String PHEP_TYPE;
//
//    @ExcelProperty("CLINIC_BLOOD")
//    private BigDecimal CLINIC_BLOOD;
//
//    @ExcelProperty("CLINIC_TYPE")
//    private String CLINIC_TYPE;
//
//    @ExcelProperty("INTO_ICU_TIME")
//    private Date INTO_ICU_TIME;
//
//    @ExcelProperty("RESPIRATOR_START_TIME")
//    private Date RESPIRATOR_START_TIME;
//
//    @ExcelProperty("LEAVE_ICU_TIME")
//    private Date LEAVE_ICU_TIME;
//
//    @ExcelProperty("RESPIRATOR_END_TIME")
//    private Date RESPIRATOR_END_TIME;
//
//    @ExcelProperty("ISS_TIME")
//    private Date ISS_TIME;
//
//    @ExcelProperty("SELECTIVE_OPERATION_START_TIME")
//    private Date SELECTIVE_OPERATION_START_TIME;
//
//    @ExcelProperty("SELECTIVE_OPERATION_END_TIME")
//    private Date SELECTIVE_OPERATION_END_TIME;
//
//    @ExcelProperty("IS_VAP")
//    private String IS_VAP;
//
//    @ExcelProperty("ISRECURE")
//    private String ISRECURE;
//
//    @ExcelProperty("STILLTIME")
//    private BigDecimal STILLTIME;
//
//    @ExcelProperty("RECUREALLTIME")
//    private BigDecimal RECUREALLTIME;
//
//    @ExcelProperty("RECUREALLCOST")
//    private BigDecimal RECUREALLCOST;
//
//    @ExcelProperty("DISABILITY_GRADE")
//    private String DISABILITY_GRADE;
//
//    @ExcelProperty("RECURE_START_TIME")
//    private Date RECURE_START_TIME;
//
//    @ExcelProperty("RECURE_DIAGNOSE_TIME")
//    private Date RECURE_DIAGNOSE_TIME;
//
//    @ExcelProperty("RECURE_END_TIME")
//    private Date RECURE_END_TIME;
//
//    @ExcelProperty("INPHOSPITAL_SITUATION")
//    private String INPHOSPITAL_SITUATION;
//
//    @ExcelProperty("INPHOSPITAL_TIME")
//    private Date INPHOSPITAL_TIME;
//
//    @ExcelProperty("OUTPHOSPITAL_TIME")
//    private Date OUTPHOSPITAL_TIME;
//
//    @ExcelProperty("OUTPHOSPITAL_DAY")
//    private BigDecimal OUTPHOSPITAL_DAY;
//
//    @ExcelProperty("OUTEMERGENCYDIAGNOSIS")
//    private String OUTEMERGENCYDIAGNOSIS;
//
//    @ExcelProperty("INHOSPITALDIAGNOSIS")
//    private String INHOSPITALDIAGNOSIS;
//
//    @ExcelProperty("OUTHOSPITAL_DIAGNOSIS")
//    private String OUTHOSPITAL_DIAGNOSIS;
//
//    @ExcelProperty("PROGNOSIS")
//    private String PROGNOSIS;
//
//    @ExcelProperty("ALL_COST")
//    private BigDecimal ALL_COST;
//
//    @ExcelProperty("DRUG_COST")
//    private BigDecimal DRUG_COST;
//
//    @ExcelProperty("EXAM_COST")
//    private BigDecimal EXAM_COST;
//
//    @ExcelProperty("PATIENT_SITUATION")
//    private String PATIENT_SITUATION;
//
//    @ExcelProperty("EMR_REMARK")
//    private String EMR_REMARK;
//
//    @ExcelProperty("IS_ACCORD")
//    private BigDecimal IS_ACCORD;
}
