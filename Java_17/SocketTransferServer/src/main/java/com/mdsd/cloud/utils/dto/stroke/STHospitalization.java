package com.mdsd.cloud.utils.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STHospitalization {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String OCSP;

    private String TOAST;

    private String CISS;

    private String COMPLICATIONS;

    private String IS_DRUG_THERAPY;

    private String DRUG_TYPE;

    private String DRUG_CODES;

    private String NO_DRUG_REASON;

//    private org.tempuri.SDCCVADrugInfoView[] drugInfo;

    private String IS_USE_AD;

    private String IS_USE_AFD;

    private String NOT_USE_AD_REASON;

    private String NOT_USE_AFD_REASON;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
