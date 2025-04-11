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
 * @author WangYunwei [2021-09-16]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_VULNUS_DEGREE")
public class MedClinicVulnusDegreeEntity extends Model {

    /**
     * 评分主表_主键ID
     */
    @ApiModelProperty(value = "评分主表_主键ID(新增: 后端自处理)")
    @TableId(value = "VD_ID", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private String vdId;

    /**
     * 就诊表_主键ID
     */
    @ApiModelProperty(value = "就诊表ID(急诊编号)")
    @TableField(value = "REG_ID")
    private String regId;

    /**
     * 病人卡号
     */
    @ApiModelProperty(value = "病人卡号")
    @TableField(value = "CARD_ID")
    private String cardId;

    /**
     * 评分结果
     */
    @ApiModelProperty(value = "评分结果")
    @TableField(value = "DEGREE_RESULT")
    private Float degreeResult;

    /**
     * 评分医师
     */
    @ApiModelProperty(value = "评分医师")
    @TableField(value = "DOCTOR_ID")
    private String doctorId;

    /**
     * 评分日期
     */
    @ApiModelProperty(value = "评分日期")
    @TableField(value = "OPERATOR_TIME")
    private LocalDateTime operatorTime;

    /**
     * 评分类别
     */
    @ApiModelProperty(value = "评分类别")
    @TableField(value = "VULNUS_TYPE")
    private String vulnusType;

    /**
     * 删除标记(0-否,1-是)
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "DELETE_SIGN")
    private Integer deleteSign;

    @Override
    public Serializable pkVal() {

        return this.vdId;
    }
}
