package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-11-12]
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_POCT")
public class MedPhepPoctEntity extends Model<MedPhepPoctEntity> {

    /**
     * POCT表_主键ID
     */
    @ApiModelProperty(value = "POCT表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * TnT(ng/L)
     */
    @ApiModelProperty(value = "TnT(ng/L)")
    @TableField(value = "TNT", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float tnt;

    /**
     * TnT单位
     */
    @ApiModelProperty(value = "TnT单位")
    @TableField(value = "TNT_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String tntUnit;

    /**
     * Tnl(ng/L)
     */
    @ApiModelProperty(value = "Tnl(ng/L)")
    @TableField(value = "TNL", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float tnl;

    /**
     * Tnl单位
     */
    @ApiModelProperty(value = "Tnl单位")
    @TableField(value = "TNL_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String tnlUnit;

    /**
     * BNP(pg/L)
     */
    @ApiModelProperty(value = "BNP(pg/L)")
    @TableField(value = "BNP", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float bnp;

    /**
     * BNP单位
     */
    @ApiModelProperty(value = "BNP单位")
    @TableField(value = "BNP_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String bnpUnit;

    /**
     * 检查时间
     */
    @ApiModelProperty(value = "检查时间")
    @TableField(value = "CHECK_TIME", jdbcType = JdbcType.DATE, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime checkTime;

    /**
     * CKMB
     */
    @ApiModelProperty(value = "CKMB")
    @TableField(value = "CKMB", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float ckmb;

    /**
     * CKMB单位
     */
    @ApiModelProperty(value = "CKMB单位")
    @TableField(value = "CKMB_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String ckmbUnit;

    /**
     * D-二聚体(ng/mL)
     */
    @ApiModelProperty(value = "D-二聚体(ng/mL)")
    @TableField(value = "D_DIMER", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float ddimer;

    /**
     * D-二聚体单位
     */
    @ApiModelProperty(value = "D-二聚体单位")
    @TableField(value = "D_DIMER_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String ddimerUnit;

    /**
     * Myo(ng/L)
     */
    @ApiModelProperty(value = "Myo(ng/L)")
    @TableField(value = "MYO", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float myo;

    /**
     * Myo单位
     */
    @ApiModelProperty(value = "Myo单位")
    @TableField(value = "MYO_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String myoUnit;

    /**
     * NT-proBNP(pg/mL)
     */
    @ApiModelProperty(value = "NT-proBNP(pg/mL)")
    @TableField(value = "NT_PROBNP", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float ntProbnp;

    /**
     * NT-proBNP单位
     */
    @ApiModelProperty(value = "NT-proBNP单位")
    @TableField(value = "NT_PROBNP_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String ntProbnpUnit;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    @TableField(value = "PATIENT_ID", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String patientId;

    /**
     * 报告时间
     */
    @ApiModelProperty(value = "报告时间")
    @TableField(value = "REPORT_TIME", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime reportTime;

    /**
     * 血清肌酐(umol/mL)
     */
    @ApiModelProperty(value = "血清肌酐(umol/mL)")
    @TableField(value = "SERUM", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float serum;

    /**
     * 血清肌酐单位
     */
    @ApiModelProperty(value = "血清肌酐单位")
    @TableField(value = "SERUM_UNIT", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String serumUnit;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
