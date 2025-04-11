package com.mdsd.cloud.util.dto.hrm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRMAdmissionAssessment {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date ADMISSION_TIME;

    private String HOPI;

    private String PH;

    private String AH;

    private String g_WEEKS;

    private String g_WEEKS_REMAIN;

    private String g_TIMES;

    private String d_TIMES;

    private String MAIN_COMPLAINT;

    private String t;

    private String p;

    private String r;

    private String BP;

    private String SPO;

    private String BS;

    private String FHR;

    private String FUH;

    private String AC;

    private String FM;

    private String SENSE;

    private String PRC;

    private String IS_FAMILY_HISTORY;

    private String FAMILY_HISTORY;

    private String FAMILY_HISTORY_OTHER;

    private String IS_AP_HISTORY;

    private String AP_HISTORY;

    private String AP_HISTORY_OTHER;

    private String IS_EP_STAGE;

    private String EP_STAGE;

    private String EP_STAGE_OTHER;

    private String MENARCHE_AGE;

    private String MENSTRUAL_PERIOD;

    private String MENSTRUAL_CYCLE;

    private String MENSTRUAL_VOLUME;

    private String DYSMENORRHEA;

    private String DIAGNOSIS;

    private String DIAGNOSIS_OTHER;

    private Date DIAGNOSIS_TIME;

    private String DIAGNOSIS_DOCTOR;

    private String DIAGNOSIS_DOCTOR_LEVEL;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
