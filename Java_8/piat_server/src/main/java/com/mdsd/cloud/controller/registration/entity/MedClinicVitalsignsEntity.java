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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-09-16]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_VITALSIGNS")
public class MedClinicVitalsignsEntity extends Model {

    /**
     * 生命体征表_主键ID
     */
    @ApiModelProperty(value = "生命体征表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 急诊编号
     */
    @ApiModelProperty(value = "就诊表ID(急诊编号)")
    @TableField(value = "REG_ID")
    private String regId;

    /**
     * 采集时间
     */
    @ApiModelProperty(value = "采集时间")
    @TableField(value = "ACQ_TIME")
    private LocalDateTime acqTime;

    /**
     * 采集区域
     */
    @ApiModelProperty(value = "采集区域")
    @TableField(value = "AREA")
    private String area;

    /**
     * 是否有生命迹象(0:否,1：是)
     */
    @ApiModelProperty(value = "是否有生命迹象(0:否,1：是)")
    @TableField(value = "HASLIFE")
    private String haslife;

    /**
     * 拒绝TPRBP(0:否,1：是)
     */
    @ApiModelProperty(value = "拒绝TPRBP(0:否,1：是)")
    @TableField(value = "NOTPRBP")
    private String notprbp;

    /**
     * 体温
     */
    @ApiModelProperty(value = "体温")
    @TableField(value = "TEMPERATURE")
    private Float temperature;

    /**
     * 脉搏(次/分钟)
     */
    @ApiModelProperty(value = "脉搏(次/分钟)")
    @TableField(value = "PLUS")
    private BigDecimal plus;

    /**
     * 呼吸(次/分钟)
     */
    @ApiModelProperty(value = "呼吸(次/分钟)")
    @TableField(value = "BREATH")
    private BigDecimal breath;

    /**
     * 收缩压(mmhg)
     */
    @ApiModelProperty(value = "收缩压(mmhg)")
    @TableField(value = "SBP")
    private Float sbp;

    /**
     * 舒张压(mmhg)
     */
    @ApiModelProperty(value = "舒张压(mmhg)")
    @TableField(value = "DBP")
    private Float dbp;

    /**
     * SPO₂ (%)
     */
    @ApiModelProperty(value = "SPO₂ (%)")
    @TableField(value = "SPO")
    private Float spo;

    /**
     * 意识
     */
    @ApiModelProperty(value = "意识")
    @TableField(value = "MIND")
    private String mind;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
