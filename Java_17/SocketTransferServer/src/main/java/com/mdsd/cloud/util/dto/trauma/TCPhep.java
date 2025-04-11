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
public class TCPhep {
    @JsonProperty("REGISTER_ID")
    private String REGISTER_ID;

    @JsonProperty("HOSPITAL_ID")
    private String HOSPITAL_ID;

    @JsonProperty("BASIC_ILLNESS")
    private String BASIC_ILLNESS;

    @JsonProperty("BASIC_ILLNESS_OTHER")
    private String BASIC_ILLNESS_OTHER;

    @JsonProperty("DIAGNOSTIC_TIME")
    private Date DIAGNOSTIC_TIME;

    @JsonProperty("DIAGNOSTIC_DOCTOR")
    private String DIAGNOSTIC_DOCTOR;

    @JsonProperty("INITIAL_DIAGNOSIS_DETAIL")
    private String INITIAL_DIAGNOSIS_DETAIL;

    @JsonProperty("CONDITION_LEVEL")
    private String CONDITION_LEVEL;

    @JsonProperty("TREATMENT_MEASURES")
    private String TREATMENT_MEASURES;

    @JsonProperty("BLOOD_EXPANSION")
    private String BLOOD_EXPANSION;

    @JsonProperty("USE_VASOCONSTRICTORS")
    private String USE_VASOCONSTRICTORS;

    @JsonProperty("CALL_TIME")
    private Date CALL_TIME;

    @JsonProperty("ANSWERING_ALARM")
    private Date ANSWERING_ALARM;

    @JsonProperty("AMBULANCES_START")
    private Date AMBULANCES_START;

    @JsonProperty("AMBULANCES_ARRIVE")
    private Date AMBULANCES_ARRIVE;

    @JsonProperty("PHEPINFO_TRANSFER")
    private Date PHEPINFO_TRANSFER;

    @JsonProperty("    private Date AMBULANCES_LEAVE;\n")
    private Date AMBULANCES_LEAVE;

    @JsonProperty("AMBULANCES_ARRIVE_HOSPITAL")
    private Date AMBULANCES_ARRIVE_HOSPITAL;

    @JsonProperty("t")
    private String t;

    @JsonProperty("SBP")
    private String SBP;

    @JsonProperty("DBP")
    private String DBP;

    @JsonProperty("RR")
    private String RR;

    @JsonProperty("BREATH")
    private String BREATH;

    @JsonProperty("SPO2")
    private String SPO2;

    @JsonProperty("PLUSE")
    private String PLUSE;

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

    @JsonProperty("PRIMARY_ID")
    private String PRIMARY_ID;
}
