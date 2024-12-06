package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class Trauma {
    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String PATIENT_NAME;

    private String VISIT_TYPE;

    private Integer GENDER;

    private Date BIRTHDAY;

    private String JOB;

    private String PHONE_NO;

    private String ID_NO;

    private String INCIDENCE_ADDRESS;

    private String REGISTER_NO;

    private String OUTPATIENT_ID;

    private String INPATIENT_ID;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private Date ARCHIVE_DATE;

    private Integer PROCESS_STATUS;

    private Integer STATUS;

    private Integer ISS_SCORE;

    private Integer DIAGNOSE_FLAG;

    private Date REGISTER_TIME;

    private String IS_SICU;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PATIENT_NO;

    private String PRIMARY_ID;

    private String BASIC_ILLNESS;

    private String BASIC_ILLNESS_OTHER;

    private Date DIAGNOSTIC_TIME;

    private String DIAGNOSTIC_DOCTOR;

    private String INITIAL_DIAGNOSIS_DETAIL;

    private String CONDITION_LEVEL;

    private String TREATMENT_MEASURES;

    private String BLOOD_EXPANSION;

    private String USE_VASOCONSTRICTORS;

    private Date CALL_TIME;

    private Date ANSWERING_ALARM;

    private Date AMBULANCES_START;

    private Date AMBULANCES_ARRIVE;

    private Date PHEPINFO_TRANSFER;

    private Date AMBULANCES_LEAVE;

    private Date AMBULANCES_ARRIVE_HOSPITAL;

    private String t;

    private String SBP;

    private String DBP;

    private String RR;

    private String BREATH;

    private String SPO2;

    private String PLUSE;

    private String MEDICAL_HISTORY;

    private String MEDICAL_HISTORY_ETC;

    private String HISTORYDRUGUSE;

    private String ANAPHYLAXIS;

    private String HARM_REASON;

    private String HARM_CARS;

    private String WOUND_REASON;

    private String WOUND_CARS;

    private String WOUND_PARS;

    private String WOUND_TYPE;

    private String FALLING_INJURY;

    private String WEAPON_DAMAGE;

    private String OTHER_DAMAGE;

    private Date TEAM_START;

    private Date ENTER_RESCUE;

    private Date RECOVERY_START_TIME;

    private Date ARTIFICIAL_AIRWAY_TIME;

    private Date INFUSION_PATHWAY_TIME;

    private Date ACID_TIME;

    private Date LAB_TIME;

    private Date FIRST_BLOOD_TIME;

    private Date EMERGENCY_ECG_TIME;

    private Date x_RAY_TIME;

    private Date LEAVE_RECOVERY_ROOM_TIME;

    private String PRIMATY_ASSESS_ID;

    private BigDecimal AIRWAY_CLEAR;

    private BigDecimal NECK_FIXED;

    private BigDecimal AIRWAY_SUSPICIOUS_WOUND;

    private String BREATH_SYMPTOM;

    private String BREATH_FREQUENCY;

    private BigDecimal CYANOSIS;

    private String CIRCULATION_SYMPTOM;

    private String HEART_RATE;

    private BigDecimal AWARENESS;

    private BigDecimal LIMBS_SYMPTOM;

    private String EYE_LEFT;

    private String EYE_RIGHT;

    private BigDecimal EYE_EQUAL_SIZE;

    private BigDecimal EYE_REACT_LIGHT;

    private BigDecimal REMOVE_CLOTH;

    private BigDecimal SURFACE_WOUND;

    private BigDecimal GCS_SCORE;

    private BigDecimal RTS_SCORE;

    private BigDecimal PAIN_SCORE;

    private String ASSESS_DOCTOR;

    private Date ASSESS_TIME;

    private BigDecimal ADVANCED_AIRWAY;

    private BigDecimal AIRWAY_AIR;

    private BigDecimal PATHWAY;

    private BigDecimal USE_CRYSTAL;

    private BigDecimal CRYSTAL_DOSAGE;

    private BigDecimal USE_GUM;

    private BigDecimal GUM_DOSAGE;

    private BigDecimal USE_BLOOD_PRODUCT;

    private BigDecimal RED_HANG_DOSAGE;

    private BigDecimal PLASMA_DOSAGE;

    private BigDecimal ALBUMIN_DOASGE;

    private String OTHER_BLOOD_PRODUCT;

    private BigDecimal VASOACTIVE_DRUG;

    private BigDecimal CARBAMIC;

    private BigDecimal PELVIS_FIXED;

    private BigDecimal ANALGESIA;

    private String ANALGESIA_DRUG;

    private String POCT;

    private BigDecimal USE_FAST;

    private BigDecimal PERICARDIAL_HYDROPS;

    private BigDecimal CHEST_HYDROPS;

    private BigDecimal CHEST_HYDROPS_SITE;

    private BigDecimal USE_DPL;

    private BigDecimal USE_XRAY;

    private BigDecimal USE_EXAM;

    private BigDecimal USE_CT;

    private BigDecimal SHOCKED;

    private String SHOCK_TYPE;

    private String BLEED_SHOCK_TYPE;

    private BigDecimal BLEED_AMOUNT;

    private BigDecimal GO_FOR;

    private String CHIEF_COMPLAINT;

    private BigDecimal CT_TYPE;

    private String CT_TYPE_ITEM;

    private BigDecimal CT_HEAD;

    private BigDecimal CT_DRAW;

    private BigDecimal CT_CHEST;

    private BigDecimal CT_ABDOMEN;

    private BigDecimal CT_PELVIS;

    private Date CT_START_TIME;

    private Date CT_END_TIME;

    private Date REBACK_RECOVERY_TIME;

    private Date CT_REPORT_TIME;

    private Date REQUEST_CONSULTATION_TIME;

    private Date DOCTOR_ARRIVE_TIME;

    private Date ENTER_DIAGNOSTIC_TIME;

    private Date COMPLETE_DRAINAG_TIME;

    private Date OPERATION_TIME;

    private Date INFORMED_CONSENT_TIME;

    private Date RECOVERY_END_TIME;

    private Date LEAVE_RECOVERY_TIME;

    private Date DIAGNOSE_TIME;

    private String DIAGNOSE_DOCTOR;

    private String PATIENTNO;

    private String INSPECTION_TYPE;

    private Date INSPECTION_TIME;

    private String MEDICATION_TYPE;

    private Date MEDICATION_TIME;

    private String OPERATION_ID;

    private String BLOOD_TYPE;

    private String ANAESTYPE;

    private String ANAES_DOCTOR_ID;

    private String ANAES_DOCTOR_NAME;

    private String OPERATION_SEC;

    private String DOCTOR_ID;

    private String DOCTOR_NAME;

    private String OPERATION_TYPE;

    private String BRACKET_TYPE;

    private String OPERATION_PROGRAM;

    private String CONTRAST_DOSE;

    private String DIAGNOSIS;

    private String OPINION;

    private String BLOOD_VOLUME;

    private String CONTRAST_NAME;

    private Date INTO_OPERATION_ROOM_TIME;

    private Date OPERATION_START_TIME;

    private Date OPERATION_END_TIME;

    private Date LEAVE_OPERATION_ROOM_TIME;

    private String ID;

    private String PATIENT_ID;

    private String CLINIC_NO;

    private String ISUSE_RESP;

    private String CRT;

    private Date ICU_TIME;

    private BigDecimal ALL_BLOOD;

    private BigDecimal PHEP_BLOOD;

    private String PHEP_TYPE;

    private BigDecimal CLINIC_BLOOD;

    private String CLINIC_TYPE;

    private Date INTO_ICU_TIME;

    private Date RESPIRATOR_START_TIME;

    private Date LEAVE_ICU_TIME;

    private Date RESPIRATOR_END_TIME;

    private Date ISS_TIME;

    private Date SELECTIVE_OPERATION_START_TIME;

    private Date SELECTIVE_OPERATION_END_TIME;

    private String IS_VAP;

    private String ISRECURE;

    private BigDecimal STILLTIME;

    private BigDecimal RECUREALLTIME;

    private BigDecimal RECUREALLCOST;

    private String DISABILITY_GRADE;

    private Date RECURE_START_TIME;

    private Date RECURE_DIAGNOSE_TIME;

    private Date RECURE_END_TIME;

    private String INPHOSPITAL_SITUATION;

    private Date INPHOSPITAL_TIME;

    private Date OUTPHOSPITAL_TIME;

    private BigDecimal OUTPHOSPITAL_DAY;

    private String OUTEMERGENCYDIAGNOSIS;

    private String INHOSPITALDIAGNOSIS;

    private String OUTHOSPITAL_DIAGNOSIS;

    private String PROGNOSIS;

    private BigDecimal ALL_COST;

    private BigDecimal DRUG_COST;

    private BigDecimal EXAM_COST;

    private String PATIENT_SITUATION;

    private String EMR_REMARK;

    private BigDecimal IS_ACCORD;
}
