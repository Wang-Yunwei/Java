package com.mdsd.cloud.utils.dto.trauma;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author WangYunwei [2024-12-05]
 */
@Getter
@Setter
public class TCEmergency {

    private String HOSPITAL_ID;

    private String REGISTER_ID;

    private String MEDICAL_HISTORY;

    private String MEDICAL_HISTORY_ETC;

    private String HISTORYDRUGUSE;

    private String ANAPHYLAXIS;

    private String HARM_REASON;

    private String HARM_CARS;

    private String WOUND_REASON;

    private String WOUND_CARS;

    private String WOUND_PARS;

    private String WOUND_TYPE;

    private String FALLING_INJURY;

    private String WEAPON_DAMAGE;

    private String OTHER_DAMAGE;

    private Date TEAM_START;

    private Date ENTER_RESCUE;

    private Date RECOVERY_START_TIME;

    private Date ARTIFICIAL_AIRWAY_TIME;

    private Date INFUSION_PATHWAY_TIME;

    private Date ACID_TIME;

    private Date LAB_TIME;

    private Date FIRST_BLOOD_TIME;

    private Date EMERGENCY_ECG_TIME;

    private Date x_RAY_TIME;

    private Date LEAVE_RECOVERY_ROOM_TIME;

    private Date UPLOAD_TIME;

    private String UPLOAD_SYSTEM;

    private Date CREATE_TIME;

    private String CLEAN_FLAG;

    private String DATA_ORIGIN;

    private String PRIMARY_ID;
}
