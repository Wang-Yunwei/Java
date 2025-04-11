package com.mdsd.cloud.controller.cardnumbermanage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/10/11 11:41
 */
@Data
public class HisResultDto {

    /**
     * 患者编号
     */
    private String patientId;

    /**
     * 就诊编号（门诊）
     */
    private String visitId;

    /**
     * 门诊号
     */
    private String hisNo;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;

    /**
     * 性别(男为M女为F)
     */
    private String sex;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 民族【该字段为中文】
     */
    private String nation;

    /**
     * 婚姻
     */
    private String marriage;

    /**
     * 教育程度
     */
    private String education;

    /**
     * 职业
     */
    private String patJob;

    /**
     * 市民卡号(医保卡)
     */
    private String citizenCard;

    /**
     * 电子病历编号
     */
    private String mrNo;

    /**
     * 患者住址
     */
    private String mailAddress;

    /**
     * 常用联系电话
     */
    private String phoneNumberHome;

    /**
     * 单位联系电话
     */
    private String phoneNumberBusiness;

    /**
     * 联系人姓名
     */
    private String nextOfKin;

    /**
     * 联系人关系
     */
    private String relationship;

    /**
     * 联系人电话
     */
    private String nextOfKinPhone;

    /**
     * 挂号类别(急诊,门诊)
     */
    private String patientType;

    /**
     * 挂号时间(必填)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registTime;

    /**
     * 挂号科室编码
     */
    private String deptCode;

    /**
     * 挂号科室名称
     */
    private String deptName;

    /**
     * 接诊医生工号
     */
    private String doctorId;

    /**
     * 接诊医生姓名
     */
    private String doctorName;

    /**
     * 主诉
     */
    private String complaint;

    /**
     * 退费标志。1 有效数据 0 退费数据
     */
    private String isUsed;

    /**
     * 病人挂号类别(自费，医保等)
     */
    private String registerType;

    /**
     * 医院院区的属性（描述患者在某一院区挂号标志，可为空）
     */
    private String hospitalArea;
}
