package com.mdsd.cloud.util.dto.stroke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STPatient {
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

    @JsonProperty("AGE")
    private String AGE;

    @JsonProperty("CONTACT_PHONE")
    private String CONTACT_PHONE;

    @JsonProperty("OUTPATIENT_ID")
    private String OUTPATIENT_ID;

    @JsonProperty("INPATIENT_ID")
    private String INPATIENT_ID;

    @JsonProperty("ATTACK_TIME")
    private Date ATTACK_TIME;

    @JsonProperty("ATTACK_ADDRESS")
    private String ATTACK_ADDRESS;

    @JsonProperty("COMMING_WAY")
    private String COMMING_WAY;

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

    @JsonProperty("GREEN_CHANNEL_TYPE")
    private String GREEN_CHANNEL_TYPE;

    @JsonProperty("DIAGNOSIS")
    private String DIAGNOSIS;

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

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("PATIENT_NO")
    private String PATIENT_NO;

    @JsonProperty("PRIMARY_ID")
    private String PRIMARY_ID;
}
