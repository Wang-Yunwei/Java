package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNSynergetic {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String HOSPITAL_NAME;

    private Date APPLY_TIME;

    private String MAIN_COMPLAINT;

    private String g_TIMES;

    private String d_TIMES;

    private String g_AGE_WEEK;

    private String g_AGE_DAY;

    private String DELIVERY_TYPE;

    private String BIRTH_WEIGHT;

    private String APGAR_1M;

    private String APGAR_5M;

    private String IS_APR;

    private String AFV;

    private String AFV_VALUE;

    private String PLACENTA;

    private String UMBILICAL_CORD;

    private String NECK;

    private String NECK_ABNORMAL;

    private String CHEST;

    private String CHEST_ABNORMAL;

    private String ABDOMEN;

    private String ABDOMEN_ABNORMAL;

    private String SPINAL_LIMBS;

    private String SPINAL_LIMBS_ABNORMAL;

    private String AEDEA;

    private String AEDEA_ABNORMAL;

    private String ANUS;

    private String ANUS_ABNORMAL;

    private String NERVOUS;

    private String NERVOUS_ABNORMAL;

    private String DIAGNOSIS;

    private String DIAGNOSIS_OTHER;

    private String BLOOD_GROUP;

    private String RH_BLOOD_GROUP;

    private String DISEASE_HISTORY;

    private String DISEASE_HISTORY_OTHER;

    private String DRUG_HISTORY;

    private String DRUG_HISTORY_OTHER;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
