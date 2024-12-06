package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCIcu {
    private String ID;

    private String PATIENT_ID;

    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String CLINIC_NO;

    private String ISUSE_RESP;

    private String CRT;

    private Date ICU_TIME;

    private BigDecimal ALL_BLOOD;

    private BigDecimal PHEP_BLOOD;

    private String PHEP_TYPE;

    private BigDecimal CLINIC_BLOOD;

    private String CLINIC_TYPE;

    private Date INTO_ICU_TIME;

    private Date RESPIRATOR_START_TIME;

    private Date LEAVE_ICU_TIME;

    private Date RESPIRATOR_END_TIME;

    private Date ISS_TIME;

    private Date SELECTIVE_OPERATION_START_TIME;

    private Date SELECTIVE_OPERATION_END_TIME;

    private String IS_VAP;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
