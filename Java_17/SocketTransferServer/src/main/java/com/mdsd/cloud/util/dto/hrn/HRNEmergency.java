package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNEmergency {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date ARRIVAL_HOS_TIME;

    private Date RECEPTION_TIME;

    private String SINGLE_INDICATOR;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
