package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNFirstAid {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date CALL_TIME;

    private Date ARRIVAL_TIME;

    private String DOCTOR_NAME;

    private String NURSE_NAME;

    private String t;

    private String p;

    private String r;

    private String BP;

    private String SPO;

    private String BS;

    private String SENSE;

    private String DIAGNOSIS_DESC;

    private String ILLNESS_STATE;

    private String THERAPEUTIC_MEASURE;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
