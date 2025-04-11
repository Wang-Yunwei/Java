package com.mdsd.cloud.util.dto.trauma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCRegister {

    @JsonProperty("REGISTER_ID")
    private String REGISTER_ID;

    @JsonProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @JsonProperty("PATIENT_NAME")
    private String PATIENT_NAME;

    @JsonProperty("VISIT_TYPE")
    private String VISIT_TYPE;

    @JsonProperty("GENDER")
    private Integer GENDER;

    @JsonProperty("BIRTHDAY")
    private Date BIRTHDAY;

    @JsonProperty("JOB")
    private String JOB;

    @JsonProperty("PHONE_NO")
    private String PHONE_NO;

    @JsonProperty("ID_NO")
    private String ID_NO;

    @JsonProperty("INCIDENCE_ADDRESS")
    private String INCIDENCE_ADDRESS;

    @JsonProperty("REGISTER_NO")
    private String REGISTER_NO;

    @JsonProperty("OUTPATIENT_ID")
    private String OUTPATIENT_ID;

    @JsonProperty("INPATIENT_ID")
    private String INPATIENT_ID;

    @JsonProperty("CREATE_DATE")
    private Date CREATE_DATE;

    @JsonProperty("UPDATE_DATE")
    private Date UPDATE_DATE;

    @JsonProperty("ARCHIVE_DATE")
    private Date ARCHIVE_DATE;

    @JsonProperty("PROCESS_STATUS")
    private Integer PROCESS_STATUS;

    @JsonProperty("STATUS")
    private Integer STATUS;

    @JsonProperty("ISS_SCORE")
    private Integer ISS_SCORE;

    @JsonProperty("DIAGNOSE_FLAG")
    private Integer DIAGNOSE_FLAG;

    @JsonProperty("REGISTER_TIME")
    private Date REGISTER_TIME;

    @JsonProperty("IS_SICU")
    private String IS_SICU;

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
