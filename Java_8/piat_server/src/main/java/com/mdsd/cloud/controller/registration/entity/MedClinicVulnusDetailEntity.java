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

/**
 * @author WangYunwei [2021-09-16]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_VULNUS_DETAIL")
public class MedClinicVulnusDetailEntity extends Model<MedClinicVulnusDetailEntity> {

    /**
     * 评分明细表_主键ID
     */
    @ApiModelProperty(value = "评分明细表_主键ID(新增: 后端自处理)")
    @TableId(value = "DETAIL_ID", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private String detailId;

    /**
     * 评分主表ID
     */
    @ApiModelProperty(value = "评分主表ID")
    @TableField(value = "VD_ID")
    private String vdId;

    /**
     * 评分细项
     */
    @ApiModelProperty(value = "评分细项")
    @TableField(value = "CONTENT")
    private String content;

    @Override
    public Serializable pkVal() {

        return this.vdId;
    }
}
