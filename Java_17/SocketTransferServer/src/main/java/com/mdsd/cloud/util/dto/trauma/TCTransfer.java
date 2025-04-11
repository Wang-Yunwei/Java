package com.mdsd.cloud.util.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCTransfer {
    private String PATIENT_ID;

    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String INPHOSPITAL_SITUATION;

    private Date INPHOSPITAL_TIME;

    private Date OUTPHOSPITAL_TIME;

    private BigDecimal OUTPHOSPITAL_DAY;

    private String OUTEMERGENCYDIAGNOSIS;

    private String INHOSPITALDIAGNOSIS;

    private String OUTHOSPITAL_DIAGNOSIS;

    private String PROGNOSIS;

    private BigDecimal ALL_COST;

    private BigDecimal DRUG_COST;

    private BigDecimal EXAM_COST;

    private String PATIENT_SITUATION;

    private String EMR_REMARK;

    private BigDecimal IS_ACCORD;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
