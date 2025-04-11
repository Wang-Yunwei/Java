package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-11-12]
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_ESTIMATE")
@ApiModel(value = "MedPhepEstimateEntity对象", description = "评分主表")
public class MedPhepEstimateEntity extends Model<MedPhepEstimateEntity> {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 评分代码
     */
    @ApiModelProperty(value = "评分代码")
    @TableField("CODE")
    private String code;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_NAME")
    private String createName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 评分医生
     */
    @ApiModelProperty(value = "评分医生")
    @TableField("DOCTOR")
    private String doctor;

    /**
     * 评分名
     */
    @ApiModelProperty(value = "评分名")
    @TableField("NAME")
    private String name;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    @TableField("PATIENT_ID")
    private String patientId;

    /**
     * 急救人员ID
     */
    @ApiModelProperty(value = "急救人员ID")
    @TableField("RELATION_ID")
    private String relationId;

    /**
     * 评分时间
     */
    @ApiModelProperty(value = "评分时间")
    @TableField("TIME")
    private LocalDateTime time;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("TITLE")
    private String title;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("UPDATE_DATE")
    private LocalDateTime updateDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @TableField("UPDATE_NAME")
    private String updateName;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值")
    @TableField("VALUE")
    private Float value;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
