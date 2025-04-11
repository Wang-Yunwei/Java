package com.mdsd.cloud.controller.cardnumbermanage.dto;

import lombok.Data;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/17 14:07
 */
@Data
public class CardInfoResultDto {

    /**
     * 急诊编号
     */
    private String regId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 分诊时间
     */
    private String triageTime;

    /**
     * 挂号编号
     */
    private String registerNo;
    /**
     * 性别
     */
    private String gender;
    /**
     * 卡号
     */
    private String cardId;
    /**
     * 病历号
     */
    private String patientId;
    /**
     * 年龄
     */
    private String regAge;

    /**
     * 年龄(补充)
     */
    private String regAgeCaption;

}
