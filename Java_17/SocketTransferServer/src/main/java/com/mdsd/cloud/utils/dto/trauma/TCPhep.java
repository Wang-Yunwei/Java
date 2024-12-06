package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCPhep {
    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String BASIC_ILLNESS;

    private String BASIC_ILLNESS_OTHER;

    private Date DIAGNOSTIC_TIME;

    private String DIAGNOSTIC_DOCTOR;

    private String INITIAL_DIAGNOSIS_DETAIL;

    private String CONDITION_LEVEL;

    private String TREATMENT_MEASURES;

    private String BLOOD_EXPANSION;

    private String USE_VASOCONSTRICTORS;

    private Date CALL_TIME;

    private Date ANSWERING_ALARM;

    private Date AMBULANCES_START;

    private Date AMBULANCES_ARRIVE;

    private Date PHEPINFO_TRANSFER;

    private Date AMBULANCES_LEAVE;

    private Date AMBULANCES_ARRIVE_HOSPITAL;

    private String t;

    private String SBP;

    private String DBP;

    private String RR;

    private String BREATH;

    private String SPO2;

    private String PLUSE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
