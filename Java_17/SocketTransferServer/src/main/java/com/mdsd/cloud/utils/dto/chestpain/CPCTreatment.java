package com.mdsd.cloud.utils.dto.chestpain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class CPCTreatment {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String CP_DIAGNOSIS_CODE;

    private String CP_DIAGNOSIS_NAME;

    private Date FIRST_TREATMENT_TIME;

    private String IS_EMPCI;

    private String IS_THROMBOLYSIS_DT;

    private String IS_REPCI;

    private String IS_EMRADIOGRAPHY;

    private String IS_ELPCI;

    private String IS_ELRADIOGRAPHY;

    private String IS_CABG;

    private String IS_NOREPERFUSION;

    private String IS_REPOTHER;

    private String OTHER_TREATMENT_MEASURE;

    private String DOCTOR_NAME;

    private String INTERVENTION_PERSON;

    private Date DECISION_OPERATION_TIME;

    private Date START_CONDUIT_TIME;

    private Date START_AGREE_TIME;

    private Date SIGN_AGREE_TIME;

    private Date ACTIVATE_CONDUIT_TIME;

    private Date ARRIVE_CONDUIT_TIME;

    private Date START_PUNCTURE_TIME;

    private Date START_RADIOGRAPHY_TIME;

    private Date END_RADIOGRAPHY_TIME;

    private Date AGAIN_SIGN_AGREE_TIME;

    private Date BALLOON_EXPANSION_TIME;

    private Date START_OPERATION_TIME;

    private Date END_OPERATION_TIME;

    private String DTWOB_TIME;

    private String IS_DELAY;

    private String DIAGNOSIS_UNIT_CODE_DT;

    private Date THROM_START_TIME_DT;

    private Date THROM_END_TIME_DT;

    private String THROM_DRUG_TYPE_DT;

    private String THROM_DRUG_CODE_DT;

    private String IS_REPATENCY_DT;

    private String THROM_RESULT_DESC_DT;

    private String PREOPERATIVE_TIMI_LEVEL;

    private String POSTOPERATIVE_TIMI_LEVEL;

    private Date DECISION_CABG_TIME;

    private Date START_CABG_TIME;

    private Date END_CABG_TIME;

    private String PERFUSION_MEASURE_CODE;

    private String PERFUSION_MEASURE_DESC;

    private Date START_INTERVENTION_DATE;

    private Date END_INTERVENTION_DATE;

    private String DELAY_REASON;

    private String RISK_LAMINATION;

    private Date SIGN_OPERATE_AGREE_TIME;

    private String OPERATION_RESULT;

    private Date STAND_RID_DATE;

    private Date START_TREATE_DATE;

    private Date CCU_INTO_DATE;

    private String TREATMENT_STRATEGY_CODE;

    private String INTERLAYER_TYPE;

    private Date ECC_CONSULTATION_DATE;

    private Date IMCD_NOTICE_DATE;

    private Date IMCD_CONSULTATION_DATE;

    private Date CHECK_RESULT_DATE;

    private Date CDU_CHECK_DATE;

    private Date CDU_ARRIVE_DATE;

    private String IS_CDU;

    private Date CT_REPORT_DATE;

    private Date CT_SCAN_DATE;

    private Date CT_ARRIVE_DATE;

    private Date USER_ARRIVE_DATE;

    private Date CT_FINISH_DATE;

    private Date CT_NOTICE_DATE;

    private String IS_ECT;

    private Date ANTI_TREATMENT_DATE;

    private String NSTEMI_TYPE;

    private String THROM_CHECK;

    private String INTENSIFY_STATINS_TREAT;

    private String RECEPTOR_RETARDANT_USING;

    private Date NOTICE_CDU_TIME;

    private Date THROM_START_AGREE_TIME;

    private Date THROM_SIGN_AGREE_TIME;

    private String TIME_INTERVAL;

    private Date START_OPERATE_AGREE_TIME;

    private Date ACTUAL_INTERVENTION_DATE;

    private String PERFUSION_MEASURE_OTHER;

    private String IS_DIRECT;

    private String IS_MRI;

    private Date SUCCESS_PUNCTURE_TIME;

    private String IS_TPCI;

    private String STATUS;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
