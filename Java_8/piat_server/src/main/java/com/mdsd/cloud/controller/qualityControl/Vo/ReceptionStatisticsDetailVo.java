package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @version 1.0
 * 特殊患者返回明细
 */
@Data
public class ReceptionStatisticsDetailVo {

    private String regId;

    /**
     * 分诊时间
     */
    private LocalDateTime triageTime;

    /**
     * 患者类型
     */
    private String patientType;

    /**
     * 姓名
     */
    private String name;

    /**
     * 病历号
     */
    private String patientId;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private int regAge;

    private String regAgeCaption;

    /**
     * 诊断
     */
    private String diagnoseDescribe;

    /**
     * 分诊级别
     */
    private String resetLevel;

    /**
     * 去向
     */
    private String turnResult;

}
