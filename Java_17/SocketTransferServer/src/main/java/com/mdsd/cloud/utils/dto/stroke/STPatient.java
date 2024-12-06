package com.mdsd.cloud.utils.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STPatient {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String CITIZEN_CARD;

    private String IDCARD;

    private String NAME;

    private String GENDER;

    private String AGE;

    private String CONTACT_PHONE;

    private String OUTPATIENT_ID;

    private String INPATIENT_ID;

    private Date ATTACK_TIME;

    private String ATTACK_ADDRESS;

    private String COMMING_WAY;

    private String PROCESS_STATUS;

    private Date CREATE_DATE;

    private Date UPDATE_DATE;

    private Date ARCHIVE_DATE;

    private Date REGIST_TIME;

    private String GREEN_CHANNEL_TYPE;

    private String DIAGNOSIS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String STATUS;

    private String PATIENT_NO;

    private String PRIMARY_ID;
}
