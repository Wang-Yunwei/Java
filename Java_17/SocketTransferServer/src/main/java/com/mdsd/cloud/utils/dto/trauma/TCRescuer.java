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
public class TCRescuer {
    private String PATIENT_ID;

    private String REGISTER_ID;

    private String HOSPITAL_ID;

    private String ISRECURE;

    private BigDecimal STILLTIME;

    private BigDecimal RECUREALLTIME;

    private BigDecimal RECUREALLCOST;

    private String DISABILITY_GRADE;

    private Date RECURE_START_TIME;

    private Date RECURE_DIAGNOSE_TIME;

    private Date RECURE_END_TIME;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
