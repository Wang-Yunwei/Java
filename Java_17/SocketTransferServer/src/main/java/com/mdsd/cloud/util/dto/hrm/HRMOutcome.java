package com.mdsd.cloud.util.dto.hrm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRMOutcome {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String DIAGNOSIS;

    private String DIAGNOSIS_OTHER;

    private Date LEAVE_TIME;

    private String HOD;

    private String COST;

    private String INSPECTION_COST;

    private String DRUG_COST;

    private String BLOOD_COST;

    private String TYPE;

    private String SUBTYPE;

    private String DESCRIPTION;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
