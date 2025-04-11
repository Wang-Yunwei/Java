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
 * @author WangYunwei [2021-10-21]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_MODIFYREASON")
public class MedClinicModifyreasonEntity extends Model {

    /**
     * 修改分珍原因表_主键ID
     */
    @ApiModelProperty(value = "修改分诊原因表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 分诊表ID
     */
    @ApiModelProperty(value = "分诊表ID")
    @TableField(value = "REG_ID")
    private String regId;

    /**
     * 其他说明
     */
    @ApiModelProperty(value = "其他说明")
    @TableField(value = "OTHER")
    private String other;

    /**
     * 原因
     */
    @ApiModelProperty(value = "原因")
    @TableField(value = "REASONS")
    private String reasons;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_ID")
    private String createId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 删除标记(0-否,1-是)
     */
    @ApiModelProperty(value = "删除标记")
    @TableField(value = "DELETE_SIGN")
    private Integer deleteSign;

    @Override
    public Serializable pkVal() {

        return this.id;
    }

}
