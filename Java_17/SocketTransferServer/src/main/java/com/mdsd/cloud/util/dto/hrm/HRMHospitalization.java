package com.mdsd.cloud.util.dto.hrm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRMHospitalization {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String TREATMENT_TYPE;

    private String HAS_UCS_INDICATION;

    private String UCS_INDICATION;

    private String UCS_INDICATION_PS;

    private String UCS_INDICATION_PC;

    private String UCS_INDICATION_OTHER;

    private String IS_UCS;

    private Date OP_DECISION_TIME;

    private Date ENTER_OR_TIME;

    private String OP_NAME;

    private String ANAESTHESIA_TYPE;

    private Date OP_START_TIME;

    private Date OP_END_TIME;

    private Date LEAVE_OR_TIME;

    private Date ENTER_ICU_TIME;

    private Date LEAVE_ICU_TIME;

    private String SPECIAL_TREATMENT;

    private Date TRANSFUSION_TIME;

    private Date DEPRESSURIZATION_TIME;

    private String IS_DELIVERY;

    private String TP_WEEKS;

    private String TP_WEEKS_REMAIN;

    private String TP_TYPE_L1;

    private String TP_TYPE_L2;

    private Date DELIVERY_TIME;

    private String IS_LIVE_BIRTH;

    private String LIVE_BIRTH;

    private String IS_LOW_WEIGHT;

    private String IS_PERINATAL_DEATH;

    private String PERINATAL_DEATH;

    private String IS_DOUBLE_FETUS;

    private String IS_GIANT_BABY;

    private String IS_FETAL_DISTRESS;

    private Date FETAL_DISTRESS_TIME;

    private String IS_FUD;

    private String IS_PARTURITION;

    private String NEONATAL_ASPHYXIA;

    private String IS_ENTER_NICU;

    private String PUERPERA_TYPE;

    private String IS_HYSTERECTOMY;

    private String FLOODING_VALUE;

    private String COMPLICATIONS;

    private String COMPLICATIONS_OTHER;

    private String IS_MATERNAL_DEATH;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
