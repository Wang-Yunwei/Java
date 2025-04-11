package com.mdsd.cloud.util.dto.chestpain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class CPCOutcome {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String CP_DIAGNOSIS_CODE;

    private String CP_DIAGNOSIS_NAME;

    private Date DIAGNOSIS_TIME;

    private String IS_HEART_FAILURE;

    private String HOD;

    private String TOTAL_COST;

    private String OUTCOME_CODE;

    private String OUTCOME_NAME;

    private Date LEAVE_TIME;

    private String TREATMENT_RESULT_CODE;

    private String TREATMENT_RESULT_NAME;

    private Date HAND_TIME;

    private String HAND_HOSPITAL_NAME;

    private Date DEATH_TIME;

    private String DEATH_CAUSE_CODE;

    private String DEATH_CAUSE_DESC;

    private String REMARK;

    private String MEDICAL_DESC;

    private Date TRANSFER_DATE;

    private String ADMISSION_DEPT;

    private String TRANSFER_REASON;

    private String IS_NET_HOSPITAL;

    private String IS_TRANS_PCI;

    private String NO_TRANS_PCI_REASON;

    private String IS_DIRECT_CATHETER;

    private String OUT_GRUG_DAPT;

    private String OUT_GRUG_ACEIORARB;

    private String OUT_DRUG_STATINS;

    private String OUT_DRUG_RETARDANT;

    private String STATUS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
