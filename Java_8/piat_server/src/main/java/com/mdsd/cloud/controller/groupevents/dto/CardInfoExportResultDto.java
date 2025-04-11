package com.mdsd.cloud.controller.groupevents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/10/19 13:47
 */
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class CardInfoExportResultDto {
    /**
     * 患者档案号
     */
    private String patientId;

    /**
     * 急诊编号
     */
    private String regId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 就诊时年龄
     */
    private int regAge;

    /**
     * 就诊时年龄（补充）
     */
    private String regAgeCaption;

    /**
     * 性别
     */
    private String gender;

    /**
     * 系统分级
     */
    private String resetLevel;

    /**
     * 诊断
     */
    private String diagnoseDescribe;

    /**
     * 去向
     */
    private String turnResult;


}
