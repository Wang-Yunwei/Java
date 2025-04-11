package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNAdmissionAssessment {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private java.util.Date ADMISSION_TIME;

    private String MAIN_COMPLAINT;

    private String g_TIMES;

    private String d_TIMES;

    private String g_AGE_WEEK;

    private String g_AGE_DAY;

    private String DELIVERY_TYPE;

    private String BIRTH_WEIGHT;

    private String BIRTH_DEFECT;

    private String AMNIOTIC_FLUID;

    private String AMNIOTIC_FLUID_VALUE;

    private String PLACENTA;

    private String UMBILICAL_CORD;

    private String STEROID_HORMONE;

    private String APGAR_1M;

    private String APGAR_5M;

    private String APGAR_10M;

    private String SINGLE_INDICATOR;

    private String HR_SCORE;

    private String IS_HR_SCORE_SE;

    private String DIAGNOSIS;

    private String DIAGNOSIS_OTHER;

    private String MOTHER_BLOOD_TYPE;

    private String MOTHER_RH_BLOOD_TYPE;

    private String MOTHER_SMOKING;

    private String MOTHER_INFECTED;

    private String MOTHER_DRUG;

    private String MOTHER_ALLERGY;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
