package com.mdsd.cloud.util.dto.hrm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRMPatient {

    @JsonProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @JsonProperty("REGISTER_ID")
    private String REGISTER_ID;

    @JsonProperty("MCH_NO")
    private String MCH_NO;

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

    @JsonProperty("BIRTHDAY")
    private Date BIRTHDAY;

    @JsonProperty("CONTACT_PHONE")
    private String CONTACT_PHONE;

    @JsonProperty("OUTPATIENT_ID")
    private String OUTPATIENT_ID;

    @JsonProperty("INPATIENT_ID")
    private String INPATIENT_ID;

    @JsonProperty("ATTACK_TIME")
    private Date ATTACK_TIME;

    @JsonProperty("COMING_WAY_CODE")
    private String COMING_WAY_CODE;

    @JsonProperty("PROCESS_STATUS")
    private String PROCESS_STATUS;

    @JsonProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @JsonProperty("UPDATE_DATE")
    private Date UPDATE_DATE;

    @JsonProperty("ARCHIVE_TIME")
    private Date ARCHIVE_TIME;

    @JsonProperty("STATUS")
    private String STATUS;

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
