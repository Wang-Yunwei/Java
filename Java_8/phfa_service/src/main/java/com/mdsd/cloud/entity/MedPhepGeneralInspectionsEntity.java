package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
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
@TableName(value = "MED_PHEP_GENERAL_INSPECTIONS")
@ApiModel(value = "MedPhepGeneralInspectionsEntity对象", description = "体格检查表")
public class MedPhepGeneralInspectionsEntity extends Model<MedPhepGeneralInspectionsEntity> {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 意识
     */
    @ApiModelProperty(value = "意识")
    @TableField(value = "CONSCIOUSNESS", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String consciousness;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_BY", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String createBy;

    /**
     * 检查时间
     */

    @ApiModelProperty(value = "检查时间")
    @TableField(value = "CREATE_TIME", jdbcType = JdbcType.DATE, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;

    /**
     * 舒张压
     */
    @ApiModelProperty(value = "舒张压")
    @TableField(value = "DBP", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float dbp;

    /**
     * 收缩压
     */
    @ApiModelProperty(value = "收缩压")
    @TableField(value = "SBP", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float sbp;

    /**
     * 是否用于交接(0-否,1-是)
     */
    @ApiModelProperty(value = "是否用于交接(0-否,1-是)")
    @TableField(value = "IS_HAND", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String isHand;

    /**
     * 是否正常采集(0-否,1-是)
     */
    @ApiModelProperty(value = "是否正常采集(0-否,1-是)")
    @TableField(value = "IS_NORMAL", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String isNormal;

    /**
     * 对光反应
     */
    @ApiModelProperty(value = "对光反应")
    @TableField(value = "LIGHT_REFLECTION", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String lightReflection;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    @TableField(value = "PATIENT_ID", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String patientId;

    /**
     * 脉搏(次/分)
     */
    @ApiModelProperty(value = "脉搏(次/分)")
    @TableField(value = "PULSE", jdbcType = JdbcType.INTEGER, updateStrategy = FieldStrategy.IGNORED)
    private Integer pulse;

    /**
     * 瞳孔
     */
    @ApiModelProperty(value = "瞳孔")
    @TableField(value = "PUPIL_RESPONSE", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String pupilResponse;

    /**
     * 呼吸频率(次/分)
     */
    @ApiModelProperty(value = "呼吸频率(次/分)")
    @TableField(value = "RR", jdbcType = JdbcType.INTEGER, updateStrategy = FieldStrategy.IGNORED)
    private Integer rr;

    /**
     * 皮肤
     */
    @ApiModelProperty(value = "皮肤")
    @TableField(value = "SKIN", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String skin;

    /**
     * 血氧
     */
    @ApiModelProperty(value = "血氧")
    @TableField(value = "SPO2", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String spo2;

    /**
     * 状态(0-否,1-是)
     */
    @ApiModelProperty(value = "状态(删除标记:0-否,1-是)")
    @TableField(value = "STATUS", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String status;

    /**
     * 体温
     */
    @ApiModelProperty(value = "体温")
    @TableField(value = "T", jdbcType = JdbcType.FLOAT, updateStrategy = FieldStrategy.IGNORED)
    private Float t;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
