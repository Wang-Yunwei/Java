package com.mdsd.cloud.controller.groupevents.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 */
@Data
public class RegitionListsResultDto {

    /**
     * 分诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime triageTime;

    /**
     * 急诊编号
     */
    private String regId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 就诊时年龄
     */
    private BigDecimal regAge;

    /**
     * 就诊时年龄(补充)
     */
    private String regAgeCaption;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 住址
     */
    private String address;

    /**
     * 患者主诉
     */
    private String subjective;

    /**
     * 诊断
     */
    private String diagnoseDescribe;

    /**
     * 来院方式
     */
    private String arriveType;

    /**
     * 生命体征
     */
    private String mcvInfo;

    /**
     * 体温
     */
    private String temperature;

    /**
     * 脉搏(次/分钟)
     */
    private String plus;

    /**
     * 呼吸(次/分钟)
     */
    private String breath;

    /**
     * 血压
     */
    private String bp;

    /**
     * 意识
     */
    private String mind;

    /**
     * 分诊级别
     */
    private String resetLevel;

    /**
     * 分诊护士
     */
    private String nurseId;

    /**
     * 就诊科室
     */
    private String departmentId;

    /**
     * 就诊医师
     */
    private String checkDoctorId;

    /**
     * 去向
     */
    private String turnResult;
}
