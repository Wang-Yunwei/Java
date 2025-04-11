package com.mdsd.cloud.util.dto.hrm;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HrmExcel {

    @ExcelProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @ExcelProperty("REGISTER_ID")
    private String REGISTER_ID;

    @ExcelProperty("MCH_NO")
    private String MCH_NO;

    @ExcelProperty("CITIZEN_CARD")
    private String CITIZEN_CARD;

    @ExcelProperty("IDCARD")
    private String IDCARD;

    @ExcelProperty("NAME")
    private String NAME;

    @ExcelProperty("GENDER")
    private String GENDER;

//    @ExcelProperty("AGE_VALUE")
//    private String AGE_VALUE;
//
//    @ExcelProperty("BIRTHDAY")
//    private Date BIRTHDAY;
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
//    @ExcelProperty("COMING_WAY_CODE")
//    private String COMING_WAY_CODE;
//
//    @ExcelProperty("PROCESS_STATUS")
//    private String PROCESS_STATUS;
//
    @ExcelProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @ExcelProperty("UPDATE_DATE")
    private Date UPDATE_DATE;
//
//    @ExcelProperty("ARCHIVE_TIME")
//    private Date ARCHIVE_TIME;
//
    @ExcelProperty("STATUS")
    private String STATUS;
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
//    @ExcelProperty("HOSPITAL_NAME")
//    private String HOSPITAL_NAME;
//
//    @ExcelProperty("APPLY_TIME")
//    private Date APPLY_TIME;
//
//    @ExcelProperty("HUSBAND_NAME")
//    private String HUSBAND_NAME;
//
//    @ExcelProperty("HUSBAND_PHONE")
//    private String HUSBAND_PHONE;
//
//    @ExcelProperty("t")
//    private String t;
//
//    @ExcelProperty("p")
//    private String p;
//
//    @ExcelProperty("r")
//    private String r;
//
//    @ExcelProperty("BP")
//    private String BP;
//
//    @ExcelProperty("SPO")
//    private String SPO;
//
//    @ExcelProperty("UDP")
//    private String UDP;
//
//    @ExcelProperty("FHR")
//    private String FHR;
//
//    @ExcelProperty("UC")
//    private String UC;
//
//    @ExcelProperty("FETUS")
//    private String FETUS;
//
//    @ExcelProperty("SENSE")
//    private String SENSE;
//
//    @ExcelProperty("PRC")
//    private String PRC;
//
//    @ExcelProperty("BIRTH_PROCESS")
//    private String BIRTH_PROCESS;
//
//    @ExcelProperty("PRENATAL_HEMORRHAGE")
//    private String PRENATAL_HEMORRHAGE;
//
//    @ExcelProperty("HAEMORRHAGE")
//    private String HAEMORRHAGE;
//
//    @ExcelProperty("DIAGNOSIS")
//    private String DIAGNOSIS;
//
//    @ExcelProperty("DIAGNOSIS_OTHER")
//    private String DIAGNOSIS_OTHER;
//
//    @ExcelProperty("ILLNESS_DESC")
//    private String ILLNESS_DESC;
//
//    @ExcelProperty("HANDLE_DESC")
//    private String HANDLE_DESC;
//
//    @ExcelProperty("REFERRAL_REASON_DESC")
//    private String REFERRAL_REASON_DESC;
//
//    @ExcelProperty("CALL_TIME")
//    private Date CALL_TIME;
//
//    @ExcelProperty("ARRIVAL_TIME")
//    private Date ARRIVAL_TIME;
//
//    @ExcelProperty("ARRIVAL_HOS_TIME")
//    private Date ARRIVAL_HOS_TIME;
//
//    @ExcelProperty("BS")
//    private String BS;
//
//    @ExcelProperty("SPECIALITY_CHECK")
//    private String SPECIALITY_CHECK;
//
//    @ExcelProperty("DIAGNOSIS_DESC")
//    private String DIAGNOSIS_DESC;
//
//    @ExcelProperty("ILLNESS_STATE")
//    private String ILLNESS_STATE;
//
//    @ExcelProperty("THERAPEUTIC_MEASURE")
//    private String THERAPEUTIC_MEASURE;
//
//    @ExcelProperty("RECEPTION_TIME")
//    private Date RECEPTION_TIME;
//
//    @ExcelProperty("FUH")
//    private String FUH;
//
//    @ExcelProperty("AC")
//    private String AC;
//
//    @ExcelProperty("FM")
//    private String FM;
//
//    @ExcelProperty("IS_GREEN_CHANNEL")
//    private String IS_GREEN_CHANNEL;
//
//    @ExcelProperty("ENTER_RR_TIME")
//    private Date ENTER_RR_TIME;
//
//    @ExcelProperty("LEAVE_RR_TIME")
//    private Date LEAVE_RR_TIME;
//
//    @ExcelProperty("VEIN_OPENING_TIME")
//    private Date VEIN_OPENING_TIME;
//
//    @ExcelProperty("MDT_RECEPTION_TIME")
//    private Date MDT_RECEPTION_TIME;
//
//    @ExcelProperty("MDT_DEPT")
//    private String MDT_DEPT;
//
//    @ExcelProperty("ADMISSION_TIME")
//    private Date ADMISSION_TIME;
//
//    @ExcelProperty("HOPI")
//    private String HOPI;
//
//    @ExcelProperty("PH")
//    private String PH;
//
//    @ExcelProperty("AH")
//    private String AH;
//
//    @ExcelProperty("g_WEEKS")
//    private String g_WEEKS;
//
//    @ExcelProperty("g_WEEKS_REMAIN")
//    private String g_WEEKS_REMAIN;
//
//    @ExcelProperty("g_TIMES")
//    private String g_TIMES;
//
//    @ExcelProperty("d_TIMES")
//    private String d_TIMES;
//
//    @ExcelProperty("MAIN_COMPLAINT")
//    private String MAIN_COMPLAINT;
//
//    @ExcelProperty("IS_FAMILY_HISTORY")
//    private String IS_FAMILY_HISTORY;
//
//    @ExcelProperty("FAMILY_HISTORY")
//    private String FAMILY_HISTORY;
//
//    @ExcelProperty("FAMILY_HISTORY_OTHER")
//    private String FAMILY_HISTORY_OTHER;
//
//    @ExcelProperty("IS_AP_HISTORY")
//    private String IS_AP_HISTORY;
//
//    @ExcelProperty("AP_HISTORY")
//    private String AP_HISTORY;
//
//    @ExcelProperty("AP_HISTORY_OTHER")
//    private String AP_HISTORY_OTHER;
//
//    @ExcelProperty("IS_EP_STAGE")
//    private String IS_EP_STAGE;
//
//    @ExcelProperty("EP_STAGE")
//    private String EP_STAGE;
//
//    @ExcelProperty("EP_STAGE_OTHER")
//    private String EP_STAGE_OTHER;
//
//    @ExcelProperty("MENARCHE_AGE")
//    private String MENARCHE_AGE;
//
//    @ExcelProperty("MENSTRUAL_PERIOD")
//    private String MENSTRUAL_PERIOD;
//
//    @ExcelProperty("MENSTRUAL_CYCLE")
//    private String MENSTRUAL_CYCLE;
//
//    @ExcelProperty("MENSTRUAL_VOLUME")
//    private String MENSTRUAL_VOLUME;
//
//    @ExcelProperty("DYSMENORRHEA")
//    private String DYSMENORRHEA;
//
//    @ExcelProperty("DIAGNOSIS_TIME")
//    private Date DIAGNOSIS_TIME;
//
//    @ExcelProperty("DIAGNOSIS_DOCTOR")
//    private String DIAGNOSIS_DOCTOR;
//
//    @ExcelProperty("DIAGNOSIS_DOCTOR_LEVEL")
//    private String DIAGNOSIS_DOCTOR_LEVEL;
//
//    @ExcelProperty("TREATMENT_TYPE")
//    private String TREATMENT_TYPE;
//
//    @ExcelProperty("HAS_UCS_INDICATION")
//    private String HAS_UCS_INDICATION;
//
//    @ExcelProperty("UCS_INDICATION")
//    private String UCS_INDICATION;
//
//    @ExcelProperty("UCS_INDICATION_PS")
//    private String UCS_INDICATION_PS;
//
//    @ExcelProperty("UCS_INDICATION_PC")
//    private String UCS_INDICATION_PC;
//
//    @ExcelProperty("UCS_INDICATION_OTHER")
//    private String UCS_INDICATION_OTHER;
//
//    @ExcelProperty("IS_UCS")
//    private String IS_UCS;
//
//    @ExcelProperty("OP_DECISION_TIME")
//    private Date OP_DECISION_TIME;
//
//    @ExcelProperty("ENTER_OR_TIME")
//    private Date ENTER_OR_TIME;
//
//    @ExcelProperty("OP_NAME")
//    private String OP_NAME;
//
//    @ExcelProperty("ANAESTHESIA_TYPE")
//    private String ANAESTHESIA_TYPE;
//
//    @ExcelProperty("OP_START_TIME")
//    private Date OP_START_TIME;
//
//    @ExcelProperty("OP_END_TIME")
//    private Date OP_END_TIME;
//
//    @ExcelProperty("LEAVE_OR_TIME")
//    private Date LEAVE_OR_TIME;
//
//    @ExcelProperty("ENTER_ICU_TIME")
//    private Date ENTER_ICU_TIME;
//
//    @ExcelProperty("LEAVE_ICU_TIME")
//    private Date LEAVE_ICU_TIME;
//
//    @ExcelProperty("SPECIAL_TREATMENT")
//    private String SPECIAL_TREATMENT;
//
//    @ExcelProperty("TRANSFUSION_TIME")
//    private Date TRANSFUSION_TIME;
//
//    @ExcelProperty("DEPRESSURIZATION_TIME")
//    private Date DEPRESSURIZATION_TIME;
//
//    @ExcelProperty("IS_DELIVERY")
//    private String IS_DELIVERY;
//
//    @ExcelProperty("TP_WEEKS")
//    private String TP_WEEKS;
//
//    @ExcelProperty("TP_WEEKS_REMAIN")
//    private String TP_WEEKS_REMAIN;
//
//    @ExcelProperty("TP_TYPE_L1")
//    private String TP_TYPE_L1;
//
//    @ExcelProperty("TP_TYPE_L2")
//    private String TP_TYPE_L2;
//
//    @ExcelProperty("DELIVERY_TIME")
//    private Date DELIVERY_TIME;
//
//    @ExcelProperty("IS_LIVE_BIRTH")
//    private String IS_LIVE_BIRTH;
//
//    @ExcelProperty("LIVE_BIRTH")
//    private String LIVE_BIRTH;
//
//    @ExcelProperty("IS_LOW_WEIGHT")
//    private String IS_LOW_WEIGHT;
//
//    @ExcelProperty("IS_PERINATAL_DEATH")
//    private String IS_PERINATAL_DEATH;
//
//    @ExcelProperty("PERINATAL_DEATH")
//    private String PERINATAL_DEATH;
//
//    @ExcelProperty("IS_DOUBLE_FETUS")
//    private String IS_DOUBLE_FETUS;
//
//    @ExcelProperty("IS_GIANT_BABY")
//    private String IS_GIANT_BABY;
//
//    @ExcelProperty("IS_FETAL_DISTRESS")
//    private String IS_FETAL_DISTRESS;
//
//    @ExcelProperty("FETAL_DISTRESS_TIME")
//    private Date FETAL_DISTRESS_TIME;
//
//    @ExcelProperty("IS_FUD")
//    private String IS_FUD;
//
//    @ExcelProperty("IS_PARTURITION")
//    private String IS_PARTURITION;
//
//    @ExcelProperty("NEONATAL_ASPHYXIA")
//    private String NEONATAL_ASPHYXIA;
//
//    @ExcelProperty("IS_ENTER_NICU")
//    private String IS_ENTER_NICU;
//
//    @ExcelProperty("PUERPERA_TYPE")
//    private String PUERPERA_TYPE;
//
//    @ExcelProperty("IS_HYSTERECTOMY")
//    private String IS_HYSTERECTOMY;
//
//    @ExcelProperty("FLOODING_VALUE")
//    private String FLOODING_VALUE;
//
//    @ExcelProperty("COMPLICATIONS")
//    private String COMPLICATIONS;
//
//    @ExcelProperty("COMPLICATIONS_OTHER")
//    private String COMPLICATIONS_OTHER;
//
//    @ExcelProperty("IS_MATERNAL_DEATH")
//    private String IS_MATERNAL_DEATH;
//
//    @ExcelProperty("LEAVE_TIME")
//    private Date LEAVE_TIME;
//
//    @ExcelProperty("HOD")
//    private String HOD;
//
//    @ExcelProperty("COST")
//    private String COST;
//
//    @ExcelProperty("INSPECTION_COST")
//    private String INSPECTION_COST;
//
//    @ExcelProperty("DRUG_COST")
//    private String DRUG_COST;
//
//    @ExcelProperty("BLOOD_COST")
//    private String BLOOD_COST;
//
//    @ExcelProperty("TYPE")
//    private String TYPE;
//
//    @ExcelProperty("SUBTYPE")
//    private String SUBTYPE;
//
//    @ExcelProperty("DESCRIPTION")
//    private String DESCRIPTION;
}
