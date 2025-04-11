package com.mdsd.cloud.controller.registration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-09-14]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_CARD_INFO")
public class MedClinicCardInfoEntity extends Model<MedClinicCardInfoEntity> {

    /**
     * 患者卡号信息表_主键ID
     */
    @ApiModelProperty(value = "患者卡号信息表_主键ID(新增: 后端自处理)")
    @TableId(value = "INFO_ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String infoId;

    /**
     * 就诊卡ID
     */
    @ApiModelProperty(value = "就诊卡ID")
    @TableField(value = "CARD_ID")
    private String cardId;

    /**
     * 患者档案号
     */
    @ApiModelProperty(value = "病历号")
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @TableField(value = "NAME")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别(01:男,02:女,03:未知)")
    @TableField(value = "GENDER")
    private String gender;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @TableField(value = "BIRTHDAY")
    private LocalDateTime birthday;

    /**
     * 证件类别
     */
    @ApiModelProperty(value = "证件类型")
    @TableField(value = "IDCARD_TYPE")
    private String idcardType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @TableField(value = "IDENTIFICATION_CARD")
    private String identificationCard;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    @TableField(value = "JOB")
    private String job;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField(value = "TEL")
    private String tel;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    @TableField(value = "NATION")
    private String nation;

    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    @TableField(value = "ADDRESS")
    private String address;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @TableField(value = "MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 医保卡号类别
     */
    @ApiModelProperty(value = "医保卡号类别")
    @TableField(value = "CARD_TYPE")
    private String cardType;

    /**
     * 社保卡号
     */
    @ApiModelProperty(value = "社保卡号")
    @TableField(value = "SOCIETY_CARD")
    private String societyCard;

    /**
     * 紧急联系人
     */
    @ApiModelProperty(value = "紧急联系人")
    @TableField(value = "LINK_MAN")
    private String linkMan;

    /**
     * 紧急联系人电话
     */
    @ApiModelProperty(value = "紧急联系人电话")
    @TableField(value = "LINK_MAN_TEL")
    private String linkManTel;

    @Override
    public Serializable pkVal() {

        return this.infoId;
    }
}
