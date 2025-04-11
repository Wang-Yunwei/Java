package com.mdsd.cloud.util.dto.stroke;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class STTreatment {
    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String DIAGNOSTICIAN;

    private Date DIAGNOSIS_TIME;

    private String DIAGNOSIS;

    private String IS_THROMBOLYSIS;

    private String UNTHROMBOLYTIC_REASONS;

    private Date THROM_AGREE_SIGN_TIME;

    private Date THROM_ORDER_TIME;

    private Date THROM_VEIN_TIME;

    private String WEIGHT;

    private String RT_PA_DOSE;

    private Date THROM_END_TIME;

    private String THROM_DRUGS;

    private String IS_THROM_DRUG_ALLERGY;

    private String THROM_UNTOWARD_EFFECT;

    private Date THROM_ABORD_TIME;

    private String IS_MULTIMODAL_CHECK;

    private String IS_CTA;

    private String IS_CTP;

    private String IS_MRI_MRA;

    private String IS_PWI;

    private String IS_INDICATION;

    private String IS_ARTERIAL_THROM;

    private String IS_OTHER_ANTITHROMBOTICS;

    private String OTHER_ANTITHROMBOTICS;

    private String ARTERIAL_THROM_TYPE;

    private Date CONDUIT_ROOM_START_TIME;

    private Date CONDUIT_ROOM_ACTIVATE_TIME;

    private Date CONDUIT_ROOM_ARRIVE_TIME;

    private Date PUNCTURE_START_TIME;

    private Date PUNCTURE_SUCCESS_TIME;

    private Date FEMORAL_SHEATHING_END_TIME;

    private Date ARCH_ANGIOGRAPHY_TIME;

    private Date ARCH_ANGIOGRAPHY_END_TIME;

    private Date VASCULAR_RECANALIZATION_TIME;

    private Date OPERATION_END_TIME;

    private String THROMBECTOMY_EQUIPMENT;

    private String IS_STENT;

    private String THROMBECTOMY_SUCCESS;

    private String THROMBECTOMY_TIMES;

    private Date THROMBECTOMY_END_TIME;

    private String mTICI;

    private String IS_HEMORRHAGIC;

    private String PATIENT_TURN;

    private String UPLOAD_ORGAN_CODE;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
