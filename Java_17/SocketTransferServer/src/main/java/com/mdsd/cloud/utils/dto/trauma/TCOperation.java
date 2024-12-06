package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCOperation {
    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String OPERATION_ID;

    private String BLOOD_TYPE;

    private String ANAESTYPE;

    private String ANAES_DOCTOR_ID;

    private String ANAES_DOCTOR_NAME;

    private String OPERATION_SEC;

    private String DOCTOR_ID;

    private String DOCTOR_NAME;

    private String OPERATION_TYPE;

    private String BRACKET_TYPE;

    private String OPERATION_PROGRAM;

    private String CONTRAST_DOSE;

    private String DIAGNOSIS;

    private String OPINION;

    private String BLOOD_VOLUME;

    private String CONTRAST_NAME;

    private Date INTO_OPERATION_ROOM_TIME;

    private Date OPERATION_START_TIME;

    private Date OPERATION_END_TIME;

    private Date LEAVE_OPERATION_ROOM_TIME;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
