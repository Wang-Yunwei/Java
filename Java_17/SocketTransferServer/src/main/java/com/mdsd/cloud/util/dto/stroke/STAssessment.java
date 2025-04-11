package com.mdsd.cloud.util.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STAssessment {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date ARRIVED_HOSPITAL_TIME;

    private Date VISIT_TIME;

    private Date ST_RECEPTION_TIME;

    private Date RUSH_IN_TIME;

    private Date OPEN_VEIN_TIME;

    private Date RUSH_OUT_TIME;

    private Date INHOSPITAL_ECG_TIME;

    private Date ECG_DIAGNOSIS_TIME;

    private String NOTICE_CT;

    private Date CT_OPEN_TIME;

    private Date CT_START_TIME;

    private Date CT_END_TIME;

    private Date CT_RESULT_TIME;

    private String CT_IS_HEMORRHAGE;

    private String HEMORRHAGE_PART;

    private String PATIENT_TURN;

    private String NOTICE_NEUROLOGY;

    private Date BRT_APPLY_TIME;

    private Date BRT_REPORT_TIME;

    private Date BCRT_APPLY_TIME;

    private Date BCRT_REPORT_TIME;

    private String PLT;

    private String PT;

    private String APTT;

    private String INR;

    private String GLU;

    private String MAIN_COMPLAINT;

    private String MEDICAL_HISTORY;

    private String PAST_HISTORY;

    private String PERSONAL_HISTORY;

    private String IS_DRUG_THERAPY;

    private String DRUG_TYPE;

    private String DRUG_CODES;

    private java.math.BigDecimal MRS_VALUE;

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

    private String NO_DRUG_REASON;

    private String IS_NIHSS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
