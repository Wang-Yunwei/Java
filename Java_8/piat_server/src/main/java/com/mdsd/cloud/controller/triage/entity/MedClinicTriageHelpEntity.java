package com.mdsd.cloud.controller.triage.entity;

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
import java.math.BigDecimal;

/**
 * @author WangYunwei [2021-10-21]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_TRIAGE_HELP")
public class MedClinicTriageHelpEntity extends Model {

    /**
     * 体征辅助分诊表_主键ID
     */
    @ApiModelProperty(value = "体征辅助分诊表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 体征类型
     */
    @ApiModelProperty(value = "体征类型")
    @TableField("VITAL_SIGN_TYPE")
    private String vitalSignType;

    /**
     * 体征简码
     */
    @ApiModelProperty(value = "体征简码")
    @TableField("VITAL_SIGN_CODE")
    private String vitalSignCode;

    /**
     * 患者类型(0:成人,1:儿童)
     */
    @ApiModelProperty(value = "患者类型(0:成人,1:儿童)")
    @TableField("PATIENT_TYPE")
    private String patientType;

    /**
     * 年龄分组(注: 只对儿童生效)
     */
    @ApiModelProperty(value = "年龄分组(注: 只对儿童生效)")
    @TableField("YEAR_GROUP")
    private String yearGroup;

    /**
     * 起始月数
     */
    @ApiModelProperty(value = "起始月数")
    @TableField("BEGIN_MONTH")
    private BigDecimal beginMonth;

    /**
     * 结束月数
     */
    @ApiModelProperty(value = "结束月数")
    @TableField("END_MONTH")
    private BigDecimal endMonth;

    /**
     * 起始值
     */
    @ApiModelProperty(value = "起始值")
    @TableField("LEVEL_BEGIN")
    private Double levelBegin;

    /**
     * 结束值
     */
    @ApiModelProperty(value = "结束值")
    @TableField("LEVEL_END")
    private Double levelEnd;

    /**
     * 体征分诊级别
     */
    @ApiModelProperty(value = "体征分诊级别")
    @TableField("LEVEL_CODE")
    private String levelCode;

    /**
     * 状态(0:是,1:否)
     */
    @ApiModelProperty(value = "状态(0:是,1:否)")
    @TableField("STATUS")
    private String status;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
