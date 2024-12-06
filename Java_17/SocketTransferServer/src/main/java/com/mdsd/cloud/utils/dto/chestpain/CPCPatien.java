package com.mdsd.cloud.utils.dto.chestpain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class CPCPatien {

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
}
