package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCRegister {
    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String PATIENT_NAME;

    private String VISIT_TYPE;

    private Integer GENDER;

    private Date BIRTHDAY;

    private String JOB;

    private String PHONE_NO;

    private String ID_NO;

    private String INCIDENCE_ADDRESS;

    private String REGISTER_NO;

    private String OUTPATIENT_ID;

    private String INPATIENT_ID;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private Date ARCHIVE_DATE;

    private Integer PROCESS_STATUS;

    private Integer STATUS;

    private Integer ISS_SCORE;

    private Integer DIAGNOSE_FLAG;

    private Date REGISTER_TIME;

    private String IS_SICU;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PATIENT_NO;

    private String PRIMARY_ID;
}
