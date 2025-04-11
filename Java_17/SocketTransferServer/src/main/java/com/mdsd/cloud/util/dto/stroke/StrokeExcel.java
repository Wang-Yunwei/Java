package com.mdsd.cloud.util.dto.stroke;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class StrokeExcel {
    @ExcelProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @ExcelProperty("REGISTER_ID")
    private String REGISTER_ID;

    @ExcelProperty("CITIZEN_CARD")
    private String CITIZEN_CARD;

    @ExcelProperty("IDCARD")
    private String IDCARD;

    @ExcelProperty("NAME")
    private String NAME;

    @ExcelProperty("GENDER")
    private String GENDER;

//    @ExcelProperty("AGE")
//    private String AGE;
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
//    @ExcelProperty("ATTACK_TIME")
//    private Date ATTACK_TIME;
//
//    @ExcelProperty("ATTACK_ADDRESS")
//    private String ATTACK_ADDRESS;
//
//    @ExcelProperty("COMMING_WAY")
//    private String COMMING_WAY;
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
//    @ExcelProperty("GREEN_CHANNEL_TYPE")
//    private String GREEN_CHANNEL_TYPE;
//
//    @ExcelProperty("DIAGNOSIS")
//    private String DIAGNOSIS;
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
    @ExcelProperty("STATUS")
    private String STATUS;
//
//    @ExcelProperty("PATIENT_NO")
//    private String PATIENT_NO;
//
//    @ExcelProperty("PRIMARY_ID")
//    private String PRIMARY_ID;
//
//    @ExcelProperty("HELP_TIME")
//    private Date HELP_TIME;
//
//    @ExcelProperty("ARRIVED_SCENE_TIME")
//    private Date ARRIVED_SCENE_TIME;
//
//    @ExcelProperty("FIRST_DOCTOR_NAME")
//    private String FIRST_DOCTOR_NAME;
//
//    @ExcelProperty("FIRST_MC_TIME")
//    private Date FIRST_MC_TIME;
//
//    @ExcelProperty("PHEP_ECG_TIME")
//    private Date PHEP_ECG_TIME;
//
//    @ExcelProperty("ECG_DIAGNOSE_TIME")
//    private Date ECG_DIAGNOSE_TIME;
//
//    @ExcelProperty("SENSE")
//    private String SENSE;
//
//    @ExcelProperty("T")
//    private BigDecimal T;
//
//    @ExcelProperty("R")
//    private int R;
//
//    @ExcelProperty("P")
//    private int P;
//
//    @ExcelProperty("HR")
//    private int HR;
//
//    @ExcelProperty("SP")
//    private int SP;
//
//    @ExcelProperty("DP")
//    private int DP;
//
//    @ExcelProperty("SPO2")
//    private BigDecimal SPO2;
//
//    @ExcelProperty("FAST")
//    private String FAST;
//
//    @ExcelProperty("ARRIVED_HOSPITAL_TIME")
//    private Date ARRIVED_HOSPITAL_TIME;
//
//    @ExcelProperty("VISIT_TIME")
//    private Date VISIT_TIME;
//
//    @ExcelProperty("ST_RECEPTION_TIME")
//    private Date ST_RECEPTION_TIME;
//
//    @ExcelProperty("RUSH_IN_TIME")
//    private Date RUSH_IN_TIME;
//
//    @ExcelProperty("OPEN_VEIN_TIME")
//    private Date OPEN_VEIN_TIME;
//
//    @ExcelProperty("RUSH_OUT_TIME")
//    private Date RUSH_OUT_TIME;
//
//    @ExcelProperty("INHOSPITAL_ECG_TIME")
//    private Date INHOSPITAL_ECG_TIME;
//
//    @ExcelProperty("ECG_DIAGNOSIS_TIME")
//    private Date ECG_DIAGNOSIS_TIME;
//
//    @ExcelProperty("NOTICE_CT")
//    private String NOTICE_CT;
//
//    @ExcelProperty("CT_OPEN_TIME")
//    private Date CT_OPEN_TIME;
//
//    @ExcelProperty("CT_START_TIME")
//    private Date CT_START_TIME;
//
//    @ExcelProperty("CT_END_TIME")
//    private Date CT_END_TIME;
//
//    @ExcelProperty("CT_RESULT_TIME")
//    private Date CT_RESULT_TIME;
//
//    @ExcelProperty("CT_IS_HEMORRHAGE")
//    private String CT_IS_HEMORRHAGE;
//
//    @ExcelProperty("HEMORRHAGE_PART")
//    private String HEMORRHAGE_PART;
//
//    @ExcelProperty("PATIENT_TURN")
//    private String PATIENT_TURN;
//
//    @ExcelProperty("NOTICE_NEUROLOGY")
//    private String NOTICE_NEUROLOGY;
//
//    @ExcelProperty("BRT_APPLY_TIME")
//    private Date BRT_APPLY_TIME;
//
//    @ExcelProperty("BRT_REPORT_TIME")
//    private Date BRT_REPORT_TIME;
//
//    @ExcelProperty("BCRT_APPLY_TIME")
//    private Date BCRT_APPLY_TIME;
//
//    @ExcelProperty("BCRT_REPORT_TIME")
//    private Date BCRT_REPORT_TIME;
//
//    @ExcelProperty("PLT")
//    private String PLT;
//
//    @ExcelProperty("PT")
//    private String PT;
//
//    @ExcelProperty("APTT")
//    private String APTT;
//
//    @ExcelProperty("INR")
//    private String INR;
//
//    @ExcelProperty("GLU")
//    private String GLU;
//
//    @ExcelProperty("MAIN_COMPLAINT")
//    private String MAIN_COMPLAINT;
//
//    @ExcelProperty("MEDICAL_HISTORY")
//    private String MEDICAL_HISTORY;
//
//    @ExcelProperty("PAST_HISTORY")
//    private String PAST_HISTORY;
//
//    @ExcelProperty("PERSONAL_HISTORY")
//    private String PERSONAL_HISTORY;
//
//    @ExcelProperty("IS_DRUG_THERAPY")
//    private String IS_DRUG_THERAPY;
//
//    @ExcelProperty("DRUG_TYPE")
//    private String DRUG_TYPE;
//
//    @ExcelProperty("DRUG_CODES")
//    private String DRUG_CODES;
//
//    @ExcelProperty("MRS_VALUE")
//    private BigDecimal MRS_VALUE;
//
////    private org.tempuri.SDCCVADrugInfoView[] drugInfo;
//
//    @ExcelProperty("IS_ANTIPLATELET")
//    private String IS_ANTIPLATELET;
//
//    @ExcelProperty("IS_ANTICOAGULATION")
//    private String IS_ANTICOAGULATION;
//
//    @ExcelProperty("IS_ANTIHYPERTENSIVE")
//    private String IS_ANTIHYPERTENSIVE;
//
//    @ExcelProperty("IS_HYPOGLYCEMIC")
//    private String IS_HYPOGLYCEMIC;
//
//    @ExcelProperty("IS_LIPIDMODULATION")
//    private String IS_LIPIDMODULATION;
//
//    @ExcelProperty("NOT_USE_REASON_ANTIPLATELET")
//    private String NOT_USE_REASON_ANTIPLATELET;
//
//    @ExcelProperty("NOT_USE_REASON_ANTICOAGULATION")
//    private String NOT_USE_REASON_ANTICOAGULATION;
//
//    @ExcelProperty("NOT_USE_REASON_ANTIHYPERTEN")
//    private String NOT_USE_REASON_ANTIHYPERTEN;
//
//    @ExcelProperty("NOT_USE_REASON_HYPOGLYCEMIC")
//    private String NOT_USE_REASON_HYPOGLYCEMIC;
//
//    @ExcelProperty("NOT_USE_REASON_LIPIDMODULATION")
//    private String NOT_USE_REASON_LIPIDMODULATION;
//
//    @ExcelProperty("NO_DRUG_REASON")
//    private String NO_DRUG_REASON;
//
//    @ExcelProperty("IS_NIHSS")
//    private String IS_NIHSS;
//
//    @ExcelProperty("DIAGNOSTICIAN")
//    private String DIAGNOSTICIAN;
//
//    @ExcelProperty("DIAGNOSIS_TIME")
//    private Date DIAGNOSIS_TIME;
//
//    @ExcelProperty("IS_THROMBOLYSIS")
//    private String IS_THROMBOLYSIS;
//
//    @ExcelProperty("UNTHROMBOLYTIC_REASONS")
//    private String UNTHROMBOLYTIC_REASONS;
//
//    @ExcelProperty("THROM_AGREE_SIGN_TIME")
//    private Date THROM_AGREE_SIGN_TIME;
//
//    @ExcelProperty("THROM_ORDER_TIME")
//    private Date THROM_ORDER_TIME;
//
//    @ExcelProperty("THROM_VEIN_TIME")
//    private Date THROM_VEIN_TIME;
//
//    @ExcelProperty("WEIGHT")
//    private String WEIGHT;
//
//    @ExcelProperty("RT_PA_DOSE")
//    private String RT_PA_DOSE;
//
//    @ExcelProperty("THROM_END_TIME")
//    private Date THROM_END_TIME;
//
//    @ExcelProperty("THROM_DRUGS")
//    private String THROM_DRUGS;
//
//    @ExcelProperty("IS_THROM_DRUG_ALLERGY")
//    private String IS_THROM_DRUG_ALLERGY;
//
//    @ExcelProperty("THROM_UNTOWARD_EFFECT")
//    private String THROM_UNTOWARD_EFFECT;
//
//    @ExcelProperty("THROM_ABORD_TIME")
//    private Date THROM_ABORD_TIME;
//
//    @ExcelProperty("IS_MULTIMODAL_CHECK")
//    private String IS_MULTIMODAL_CHECK;
//
//    @ExcelProperty("IS_CTA")
//    private String IS_CTA;
//
//    @ExcelProperty("IS_CTP")
//    private String IS_CTP;
//
//    @ExcelProperty("IS_MRI_MRA")
//    private String IS_MRI_MRA;
//
//    @ExcelProperty("IS_PWI")
//    private String IS_PWI;
//
//    @ExcelProperty("IS_INDICATION")
//    private String IS_INDICATION;
//
//    @ExcelProperty("IS_ARTERIAL_THROM")
//    private String IS_ARTERIAL_THROM;
//
//    @ExcelProperty("IS_OTHER_ANTITHROMBOTICS")
//    private String IS_OTHER_ANTITHROMBOTICS;
//
//    @ExcelProperty("OTHER_ANTITHROMBOTICS")
//    private String OTHER_ANTITHROMBOTICS;
//
//    @ExcelProperty("ARTERIAL_THROM_TYPE")
//    private String ARTERIAL_THROM_TYPE;
//
//    @ExcelProperty("CONDUIT_ROOM_START_TIME")
//    private Date CONDUIT_ROOM_START_TIME;
//
//    @ExcelProperty("CONDUIT_ROOM_ACTIVATE_TIME")
//    private Date CONDUIT_ROOM_ACTIVATE_TIME;
//
//    @ExcelProperty("CONDUIT_ROOM_ARRIVE_TIME")
//    private Date CONDUIT_ROOM_ARRIVE_TIME;
//
//    @ExcelProperty("PUNCTURE_START_TIME")
//    private Date PUNCTURE_START_TIME;
//
//    @ExcelProperty("PUNCTURE_SUCCESS_TIME")
//    private Date PUNCTURE_SUCCESS_TIME;
//
//    @ExcelProperty("FEMORAL_SHEATHING_END_TIME")
//    private Date FEMORAL_SHEATHING_END_TIME;
//
//    @ExcelProperty("ARCH_ANGIOGRAPHY_TIME")
//    private Date ARCH_ANGIOGRAPHY_TIME;
//
//    @ExcelProperty("ARCH_ANGIOGRAPHY_END_TIME")
//    private Date ARCH_ANGIOGRAPHY_END_TIME;
//
//    @ExcelProperty("VASCULAR_RECANALIZATION_TIME")
//    private Date VASCULAR_RECANALIZATION_TIME;
//
//    @ExcelProperty("OPERATION_END_TIME")
//    private Date OPERATION_END_TIME;
//
//    @ExcelProperty("THROMBECTOMY_EQUIPMENT")
//    private String THROMBECTOMY_EQUIPMENT;
//
//    @ExcelProperty("IS_STENT")
//    private String IS_STENT;
//
//    @ExcelProperty("THROMBECTOMY_SUCCESS")
//    private String THROMBECTOMY_SUCCESS;
//
//    @ExcelProperty("THROMBECTOMY_TIMES")
//    private String THROMBECTOMY_TIMES;
//
//    @ExcelProperty("THROMBECTOMY_END_TIME")
//    private Date THROMBECTOMY_END_TIME;
//
//    @ExcelProperty("mTICI")
//    private String mTICI;
//
//    @ExcelProperty("IS_HEMORRHAGIC")
//    private String IS_HEMORRHAGIC;
//
//    @ExcelProperty("OCSP")
//    private String OCSP;
//
//    @ExcelProperty("TOAST")
//    private String TOAST;
//
//    @ExcelProperty("CISS")
//    private String CISS;
//
//    @ExcelProperty("COMPLICATIONS")
//    private String COMPLICATIONS;
//
//    @ExcelProperty("IS_USE_AD")
//    private String IS_USE_AD;
//
//    @ExcelProperty("IS_USE_AFD")
//    private String IS_USE_AFD;
//
//    @ExcelProperty("NOT_USE_AD_REASON")
//    private String NOT_USE_AD_REASON;
//
//    @ExcelProperty("NOT_USE_AFD_REASON")
//    private String NOT_USE_AFD_REASON;
//
//    @ExcelProperty("ADMISSION_TIME")
//    private Date ADMISSION_TIME;
//
//    @ExcelProperty("DISCHARGE_TIME")
//    private Date DISCHARGE_TIME;
//
//    @ExcelProperty("DISCHARGE_DIAGNOSIS")
//    private String DISCHARGE_DIAGNOSIS;
//
//    @ExcelProperty("DISCHARGE_TYPE")
//    private String DISCHARGE_TYPE;
//
//    @ExcelProperty("COST")
//    private BigDecimal COST;
//
//    @ExcelProperty("MEDICAL_COST")
//    private BigDecimal MEDICAL_COST;
//
//    @ExcelProperty("INSPECT_COST")
//    private BigDecimal INSPECT_COST;
//
//    @ExcelProperty("HOD")
//    private BigDecimal HOD;
}
