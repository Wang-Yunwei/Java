package com.mdsd.cloud.util.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STFirstAid {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private Date HELP_TIME;

    private Date ARRIVED_SCENE_TIME;

    private String FIRST_DOCTOR_NAME;

    private Date FIRST_MC_TIME;

    private Date PHEP_ECG_TIME;

    private Date ECG_DIAGNOSE_TIME;

    private String SENSE;

    private BigDecimal T;

    private int R;

    private int P;

    private int HR;

    private int SP;

    private int DP;

    private java.math.BigDecimal SPO2;

    private String FAST;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
