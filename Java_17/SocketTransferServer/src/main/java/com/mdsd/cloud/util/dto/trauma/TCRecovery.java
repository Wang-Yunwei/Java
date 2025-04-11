package com.mdsd.cloud.util.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCRecovery {
    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String PRIMATY_ASSESS_ID;

    private BigDecimal AIRWAY_CLEAR;

    private BigDecimal NECK_FIXED;

    private BigDecimal AIRWAY_SUSPICIOUS_WOUND;

    private String BREATH_SYMPTOM;

    private String BREATH_FREQUENCY;

    private String SPO2;

    private BigDecimal CYANOSIS;

    private String CIRCULATION_SYMPTOM;

    private String DBP;

    private String SBP;

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

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private Date DIAGNOSE_TIME;

    private String DIAGNOSE_DOCTOR;

    private String PATIENTNO;

    private String INSPECTION_TYPE;

    private Date INSPECTION_TIME;

    private String MEDICATION_TYPE;

    private Date MEDICATION_TIME;

    private String PRIMARY_ID;
}
