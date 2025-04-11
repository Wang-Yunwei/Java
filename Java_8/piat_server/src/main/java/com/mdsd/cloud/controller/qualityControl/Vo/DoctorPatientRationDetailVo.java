package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
public class DoctorPatientRationDetailVo {

    private String id;
    /**
     * 年份
     */
    private String year;

    /**
     * 院区
     */
    private String hospitalArea;

    /**
     * 医生人数
     */
    private int doctorNumber;

    /**
     * 护士人数
     */
    private int nurseNumber;

    /**
     * 患者总数（万）
     */
    private float patientNumber;

    /**
     * 医患比
     */
    private String doctorPatientRatio;

    /**
     * 护患比
     */
    private String nursePatientRatio;

}
