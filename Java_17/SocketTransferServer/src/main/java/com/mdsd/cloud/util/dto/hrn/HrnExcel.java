package com.mdsd.cloud.util.dto.hrn;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HrnExcel {

    @ExcelProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @ExcelProperty("REGISTER_ID")
    private String REGISTER_ID;

    @ExcelProperty("MCH_NO")
    private String MCH_NO;

    @ExcelProperty("NAME")
    private String NAME;

    @ExcelProperty("GENDER")
    private String GENDER;

    @ExcelProperty("AGE_VALUE")
    private String AGE_VALUE;

//    @ExcelProperty("BIRTHDAY")
//    private Date BIRTHDAY;
//
//    @ExcelProperty("BIRTH_DAYS")
//    private String BIRTH_DAYS;
//
//    @ExcelProperty("WEIGHT")
//    private String WEIGHT;
//
//    @ExcelProperty("MOTHER_NAME")
//    private String MOTHER_NAME;
//
//    @ExcelProperty("MOTHER_IDCARD")
//    private String MOTHER_IDCARD;
//
//    @ExcelProperty("MOTHER_AGE")
//    private String MOTHER_AGE;
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
//    @ExcelProperty("BIRTH_HOSPITAL")
//    private String BIRTH_HOSPITAL;
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
//    @ExcelProperty("MAIN_COMPLAINT")
//    private String MAIN_COMPLAINT;
//
//    @ExcelProperty("g_TIMES")
//    private String g_TIMES;
//
//    @ExcelProperty("d_TIMES")
//    private String d_TIMES;
//
//    @ExcelProperty("g_AGE_WEEK")
//    private String g_AGE_WEEK;
//
//    @ExcelProperty("g_AGE_DAY")
//    private String g_AGE_DAY;
//
//    @ExcelProperty("DELIVERY_TYPE")
//    private String DELIVERY_TYPE;
//
//    @ExcelProperty("BIRTH_WEIGHT")
//    private String BIRTH_WEIGHT;
//
//    @ExcelProperty("APGAR_1M")
//    private String APGAR_1M;
//
//    @ExcelProperty("APGAR_5M")
//    private String APGAR_5M;
//
//    @ExcelProperty("IS_APR")
//    private String IS_APR;
//
//    @ExcelProperty("AFV")
//    private String AFV;
//
//    @ExcelProperty("AFV_VALUE")
//    private String AFV_VALUE;
//
//    @ExcelProperty("PLACENTA")
//    private String PLACENTA;
//
//    @ExcelProperty("UMBILICAL_CORD")
//    private String UMBILICAL_CORD;
//
//    @ExcelProperty("NECK")
//    private String NECK;
//
//    @ExcelProperty("NECK_ABNORMAL")
//    private String NECK_ABNORMAL;
//
//    @ExcelProperty("CHEST")
//    private String CHEST;
//
//    @ExcelProperty("CHEST_ABNORMAL")
//    private String CHEST_ABNORMAL;
//
//    @ExcelProperty("ABDOMEN")
//    private String ABDOMEN;
//
//    @ExcelProperty("ABDOMEN_ABNORMAL")
//    private String ABDOMEN_ABNORMAL;
//
//    @ExcelProperty("SPINAL_LIMBS")
//    private String SPINAL_LIMBS;
//
//    @ExcelProperty("SPINAL_LIMBS_ABNORMAL")
//    private String SPINAL_LIMBS_ABNORMAL;
//
//    @ExcelProperty("AEDEA")
//    private String AEDEA;
//
//    @ExcelProperty("AEDEA_ABNORMAL")
//    private String AEDEA_ABNORMAL;
//
//    @ExcelProperty("ANUS")
//    private String ANUS;
//
//    @ExcelProperty("ANUS_ABNORMAL")
//    private String ANUS_ABNORMAL;
//
//    @ExcelProperty("NERVOUS")
//    private String NERVOUS;
//
//    @ExcelProperty("NERVOUS_ABNORMAL")
//    private String NERVOUS_ABNORMAL;
//
//    @ExcelProperty("DIAGNOSIS")
//    private String DIAGNOSIS;
//
//    @ExcelProperty("DIAGNOSIS_OTHER")
//    private String DIAGNOSIS_OTHER;
//
//    @ExcelProperty("BLOOD_GROUP")
//    private String BLOOD_GROUP;
//
//    @ExcelProperty("RH_BLOOD_GROUP")
//    private String RH_BLOOD_GROUP;
//
//    @ExcelProperty("DISEASE_HISTORY")
//    private String DISEASE_HISTORY;
//
//    @ExcelProperty("DISEASE_HISTORY_OTHER")
//    private String DISEASE_HISTORY_OTHER;
//
//    @ExcelProperty("DRUG_HISTORY")
//    private String DRUG_HISTORY;
//
//    @ExcelProperty("DRUG_HISTORY_OTHER")
//    private String DRUG_HISTORY_OTHER;
//
//    @ExcelProperty("CALL_TIME")
//    private Date CALL_TIME;
//
//    @ExcelProperty("ARRIVAL_TIME")
//    private Date ARRIVAL_TIME;
//
//    @ExcelProperty("DOCTOR_NAME")
//    private String DOCTOR_NAME;
//
//    @ExcelProperty("NURSE_NAME")
//    private String NURSE_NAME;
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
//    @ExcelProperty("BS")
//    private String BS;
//
//    @ExcelProperty("SENSE")
//    private String SENSE;
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
//    @ExcelProperty("ARRIVAL_HOS_TIME")
//    private Date ARRIVAL_HOS_TIME;
//
//    @ExcelProperty("RECEPTION_TIME")
//    private Date RECEPTION_TIME;
//
//    @ExcelProperty("SINGLE_INDICATOR")
//    private String SINGLE_INDICATOR;
//
//    @ExcelProperty("ADMISSION_TIME")
//    private Date ADMISSION_TIME;
//
//    @ExcelProperty("BIRTH_DEFECT")
//    private String BIRTH_DEFECT;
//
//    @ExcelProperty("AMNIOTIC_FLUID")
//    private String AMNIOTIC_FLUID;
//
//    @ExcelProperty("AMNIOTIC_FLUID_VALUE")
//    private String AMNIOTIC_FLUID_VALUE;
//
//    @ExcelProperty("STEROID_HORMONE")
//    private String STEROID_HORMONE;
//
//    @ExcelProperty("APGAR_10M")
//    private String APGAR_10M;
//
//    @ExcelProperty("HR_SCORE")
//    private String HR_SCORE;
//
//    @ExcelProperty("IS_HR_SCORE_SE")
//    private String IS_HR_SCORE_SE;
//
//    @ExcelProperty("MOTHER_BLOOD_TYPE")
//    private String MOTHER_BLOOD_TYPE;
//
//    @ExcelProperty("MOTHER_RH_BLOOD_TYPE")
//    private String MOTHER_RH_BLOOD_TYPE;
//
//    @ExcelProperty("MOTHER_SMOKING")
//    private String MOTHER_SMOKING;
//
//    @ExcelProperty("MOTHER_INFECTED")
//    private String MOTHER_INFECTED;
//
//    @ExcelProperty("MOTHER_DRUG")
//    private String MOTHER_DRUG;
//
//    @ExcelProperty("MOTHER_ALLERGY")
//    private String MOTHER_ALLERGY;
//
//    @ExcelProperty("CLINICAL")
//    private String CLINICAL;
//
//    @ExcelProperty("CLINICAL_OTHER")
//    private String CLINICAL_OTHER;
//
//    @ExcelProperty("FAMILY")
//    private String FAMILY;
//
//    @ExcelProperty("IS_ILLNESS")
//    private String IS_ILLNESS;
//
//    @ExcelProperty("TYPES")
//    private String TYPES;
//
//    @ExcelProperty("TYPE_OTHER")
//    private String TYPE_OTHER;
//
//    @ExcelProperty("TYPE")
//    private String TYPE;
//
//    @ExcelProperty("IS_USED")
//    private String IS_USED;
//
//    @ExcelProperty("DRUGS")
//    private String DRUGS;
//
//    @ExcelProperty("DRUGS_OTHER")
//    private String DRUGS_OTHER;
//
//    @ExcelProperty("IS_MV")
//    private String IS_MV;
//
//    @ExcelProperty("MV_DAYS")
//    private String MV_DAYS;
//
//    @ExcelProperty("IS_MV_CPAP")
//    private String IS_MV_CPAP;
//
//    @ExcelProperty("IS_OP")
//    private String IS_OP;
//
//    @ExcelProperty("OP_TYPE")
//    private String OP_TYPE;
//
//    @ExcelProperty("IS_CHECKED")
//    private String IS_CHECKED;
//
//    @ExcelProperty("CHECK_TYPE")
//    private String CHECK_TYPE;
//
//    @ExcelProperty("IS_RESCUE_SUCCESS")
//    private String IS_RESCUE_SUCCESS;
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
//    @ExcelProperty("SUBTYPE")
//    private String SUBTYPE;
//
//    @ExcelProperty("DESCRIPTION")
//    private String DESCRIPTION;
}
