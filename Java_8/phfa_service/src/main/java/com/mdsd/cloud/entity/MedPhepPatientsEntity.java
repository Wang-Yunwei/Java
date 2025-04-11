package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author hxn
 * <p>
 * 2021/11/12 09点26分
 * 患者信息表
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_PATIENTS")
public class MedPhepPatientsEntity {

    /**
     * 患者Id
     */
    @ApiModelProperty(value = "患者ID")
    @TableId(value = "PATIENT_ID", type = IdType.ASSIGN_UUID)
    private String patientId;

    /**
     * 所属任务Id
     */
    @ApiModelProperty(value = "任务ID")
    @TableField(value = "MISSION_ID")
    private String missionId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @TableField(value = "NAME")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField(value = "GENDER")
    private String gender;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    @TableField(value = "NATION")
    private String nation;

    /**
     * 国籍
     */
    @ApiModelProperty(value = "国籍")
    @TableField(value = "STATE")
    private String state;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    @TableField(value = "AGE")
    private int age;

    /**
     * 年龄拓展
     */
    @ApiModelProperty(value = "年龄拓展")
    @TableField(value = "AGE_EXTEND")
    private String ageExtend;

    /**
     * 腕带编号
     */
    @ApiModelProperty(value = "腕带编号")
    @TableField(value = "TAG_ID")
    private String tagId;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    @TableField(value = "CARD_TYPE")
    private String cardType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @TableField(value = "CARD_NO")
    private String cardNo;

    /**
     * 健康档案号
     */
    @ApiModelProperty(value = "健康档案号")
    @TableField(value = "HEALTH_RECORD_ID")
    private String healthRecordId;

    /**
     * 医保号
     */
    @ApiModelProperty(value = "医保号")
    @TableField(value = "HEALTH_INSURANCE_ID")
    private String healthInsuranceId;

    /**
     * 出生年月
     */
    @ApiModelProperty(value = "出生年月")
    @TableField(value = "BIRTHDAY")
    private LocalDateTime birthday;

    /**
     * 急诊Id
     */
    @ApiModelProperty(value = "急诊Id")
    @TableField(value = "FROM_EMERGENCY_ID")
    private String fromEmergencyId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField(value = "STATUS")
    private String status;
}
