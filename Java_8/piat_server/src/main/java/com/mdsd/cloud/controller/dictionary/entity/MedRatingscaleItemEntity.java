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
@TableName("MED_RATINGSCALE_ITEM")
@ApiModel(value = "MedRatingscaleItemEntity对象", description = "评分模板明细表")
public class MedRatingscaleItemEntity extends Model<MedRatingscaleItemEntity> {

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "所属模板")
    @TableField("RATING_ID")
    private String ratingId;

    @ApiModelProperty(value = "明细标题")
    @TableField("LABEL")
    private String label;

    @ApiModelProperty(value = "项配置")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_ID")
    private String createId;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "分类")
    @TableField("CLASSIFY")
    private String classify;
}
