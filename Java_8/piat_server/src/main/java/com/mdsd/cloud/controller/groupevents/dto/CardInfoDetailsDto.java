package com.mdsd.cloud.controller.groupevents.dto;

import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import lombok.Data;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/27 14:04
 */
@Data
public class CardInfoDetailsDto extends MedClinicCardInfoEntity {

    /**
     * 急诊编号
     */
    private String regId;

    /**
     * 就诊年龄
     */
    private Integer regAge;

    /**
     * 年龄补充
     */
    private String regAgeCaption;

    /**
     * 分诊级别
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
