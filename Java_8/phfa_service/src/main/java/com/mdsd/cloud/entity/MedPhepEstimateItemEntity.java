package com.mdsd.cloud.entity;

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
 * @author WangYunwei [2021-11-12]
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_ESTIMATE_ITEM")
public class MedPhepEstimateItemEntity extends Model<MedPhepEstimateItemEntity> {

    /**
     * 评分项目明细表_主键ID
     */
    @ApiModelProperty(value = "评分项目明细表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 评分细项目
     */
    @ApiModelProperty(value = "评分细项目")
    @TableField("CONTENT")
    private String content;

    /**
     * 评分主表ID
     */
    @ApiModelProperty(value = "评分主表ID")
    @TableField("ESTIMATE_ID")
    private String estimateId;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
