package com.mdsd.cloud.util.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STOutcome {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date ADMISSION_TIME;

    private Date DISCHARGE_TIME;

    private String DISCHARGE_DIAGNOSIS;

    private String DISCHARGE_TYPE;

    private java.math.BigDecimal COST;

    private java.math.BigDecimal MEDICAL_COST;

    private java.math.BigDecimal INSPECT_COST;

    private java.math.BigDecimal HOD;

    private String IS_DRUG_THERAPY;

    private String DRUG_TYPE;

    private String DRUG_CODES;

    private String NO_DRUG_REASON;

//    private org.tempuri.SDCCVADrugInfoView[] drugInfo;

    private String IS_ANTIPLATELET;

    private String IS_ANTICOAGULATION;

    private String IS_ANTIHYPERTENSIVE;

    private String IS_HYPOGLYCEMIC;

    private String IS_LIPIDMODULATION;

    private String NOT_USE_REASON_ANTIPLATELET;

    private String NOT_USE_REASON_ANTICOAGULATION;

    private String NOT_USE_REASON_ANTIHYPERTEN;

    private String NOT_USE_REASON_HYPOGLYCEMIC;

    private String NOT_USE_REASON_LIPIDMODULATION;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
