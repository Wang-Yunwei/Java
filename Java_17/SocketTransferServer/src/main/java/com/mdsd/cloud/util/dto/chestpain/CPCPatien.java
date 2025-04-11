package com.mdsd.cloud.util.dto.chestpain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class CPCPatien {

    @JsonProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @JsonProperty("REGISTER_ID")
    private String REGISTER_ID;

    @JsonProperty("CITIZEN_CARD")
    private String CITIZEN_CARD;

    @JsonProperty("IDCARD")
    private String IDCARD;

    @JsonProperty("NAME")
    private String NAME;

    @JsonProperty("GENDER")
    private String GENDER;

    @JsonProperty("AGE_VALUE")
    private String AGE_VALUE;

    @JsonProperty("AGE_UNIT")
    private String AGE_UNIT;

    @JsonProperty("CONTACT_PHONE")
    private String CONTACT_PHONE;

    @JsonProperty("OUTPATIENT_ID")
    private String OUTPATIENT_ID;

    @JsonProperty("INPATIENT_ID")
    private String INPATIENT_ID;

    @JsonProperty("ATTACK_ADDRESS")
    private String ATTACK_ADDRESS;

    @JsonProperty("ATTACK_TIME")
    private Date ATTACK_TIME;

    @JsonProperty("IS_NULL_ATTACK_DETAIL_TIME")
    private String IS_NULL_ATTACK_DETAIL_TIME;

    @JsonProperty("ATTACK_ZONE")
    private String ATTACK_ZONE;

    @JsonProperty("IS_HELP")
    private String IS_HELP;

    @JsonProperty("IS_PERSISTENT")
    private String IS_PERSISTENT;

    @JsonProperty("IS_INTERMITTENT")
    private String IS_INTERMITTENT;

    @JsonProperty("IS_LAXATION")
    private String IS_LAXATION;

    @JsonProperty("IS_BELLYACHE")
    private String IS_BELLYACHE;

    @JsonProperty("IS_DYSPNEA")
    private String IS_DYSPNEA;

    @JsonProperty("IS_SHOCK")
    private String IS_SHOCK;

    @JsonProperty("IS_HEART_ATTACK")
    private String IS_HEART_ATTACK;

    @JsonProperty("IS_MALIGNANT_ARRHYTHMIA")
    private String IS_MALIGNANT_ARRHYTHMIA;

    @JsonProperty("IS_CPR")
    private String IS_CPR;

    @JsonProperty("IS_HEMORRHAGE")
    private String IS_HEMORRHAGE;

    @JsonProperty("IS_OTHER")
    private String IS_OTHER;

    @JsonProperty("COMING_WAY_CODE")
    private String COMING_WAY_CODE;

    @JsonProperty("CP_DIAGNOSIS_CODE")
    private String CP_DIAGNOSIS_CODE;

    @JsonProperty("HELP_DATE")
    private Date HELP_DATE;

    @JsonProperty("HELP_CODE")
    private String HELP_CODE;

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("PROCESS_STATUS")
    private String PROCESS_STATUS;

    @JsonProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @JsonProperty("UPDATE_DATE")
    private Date UPDATE_DATE;

    @JsonProperty("ARCHIVE_DATE")
    private Date ARCHIVE_DATE;

    @JsonProperty("REGIST_TIME")
    private Date REGIST_TIME;

    @JsonProperty("UPLOAD_ORGAN_CODE")
    private String UPLOAD_ORGAN_CODE;

    @JsonProperty("UPLOAD_TIME")
    private Date UPLOAD_TIME;

    @JsonProperty("UPLOAD_SYSTEM")
    private String UPLOAD_SYSTEM;

    @JsonProperty("CREATE_TIME")
    private Date CREATE_TIME;

    @JsonProperty("CLEAN_FLAG")
    private String CLEAN_FLAG;

    @JsonProperty("DATA_ORIGIN")
    private String DATA_ORIGIN;

    @JsonProperty("PATIENT_NO")
    private String PATIENT_NO;

    @JsonProperty("PRIMARY_ID")
    private String PRIMARY_ID;
}
