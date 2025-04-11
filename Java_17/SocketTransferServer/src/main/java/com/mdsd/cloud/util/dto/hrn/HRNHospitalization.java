package com.mdsd.cloud.util.dto.hrn;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-06]
 */
@Getter
@Setter
public class HRNHospitalization {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String CLINICAL;

    private String CLINICAL_OTHER;

    private String FAMILY;

    private String IS_ILLNESS;

    private String TYPES;

    private String TYPE_OTHER;

    private String TYPE;

    private String IS_USED;

    private String DRUGS;

    private String DRUGS_OTHER;

    private String IS_MV;

    private String MV_DAYS;

    private String IS_MV_CPAP;

    private String IS_OP;

    private String OP_TYPE;

    private String IS_CHECKED;

    private String CHECK_TYPE;

    private String IS_RESCUE_SUCCESS;

//    private org.tempuri.SDCHrnDrugInfo[] drugsInfo;

//    private org.tempuri.SDCHrnDiseaseInfo[] diseasesInfo;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
