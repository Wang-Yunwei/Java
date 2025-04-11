package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNPatient {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String MCH_NO;

    private String NAME;

    private String GENDER;

    private String AGE_VALUE;

    private Date BIRTHDAY;

    private String BIRTH_DAYS;

    private String WEIGHT;

    private String MOTHER_NAME;

    private String MOTHER_IDCARD;

    private String MOTHER_AGE;

    private String CONTACT_PHONE;

    private String OUTPATIENT_ID;

    private String INPATIENT_ID;

    private String BIRTH_HOSPITAL;

    private String COMING_WAY_CODE;

    private String PROCESS_STATUS;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private Date ARCHIVE_TIME;

    private String STATUS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PATIENT_NO;

    private String PRIMARY_ID;
}
