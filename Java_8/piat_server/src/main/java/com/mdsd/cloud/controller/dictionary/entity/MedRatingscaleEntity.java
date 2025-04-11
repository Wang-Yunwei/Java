package com.mdsd.cloud.controller.dictionary.entity;

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

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-07-12]
 */
@Getter
@Setter
@TableName("MED_RATINGSCALE")
@ApiModel(value = "MedRatingscaleEntity对象", description = "评分模板主表")
public class MedRatingscaleEntity extends Model<MedRatingscaleEntity> {

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "评分模板名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "医院ID")
    @TableField("HOSPITAL_ID")
    private String hospitalId;

    @ApiModelProperty(value = "专科")
    @TableField("SPECIAL")
    private String special;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATE_ID")
    private String createId;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "评分模板标识")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "项目ID")
    @TableField("APPID")
    private String appid;
}
