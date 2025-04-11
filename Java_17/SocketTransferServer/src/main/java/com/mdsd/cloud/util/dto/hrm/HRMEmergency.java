package com.mdsd.cloud.util.dto.hrm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRMEmergency {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date ARRIVAL_HOS_TIME;

    private Date RECEPTION_TIME;

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

    private String IS_GREEN_CHANNEL;

    private Date ENTER_RR_TIME;

    private Date LEAVE_RR_TIME;

    private Date VEIN_OPENING_TIME;

    private Date MDT_RECEPTION_TIME;

    private String MDT_DEPT;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
