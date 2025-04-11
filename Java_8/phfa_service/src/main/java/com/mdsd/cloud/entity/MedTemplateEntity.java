package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huxiaoneng
 */
@Data
@TableName(value = "MED_TEMPLATE")
@ApiModel(value = "MedTemplate对象", description = "模板表")
public class MedTemplateEntity extends Model<MedTemplateEntity> {

    @ApiModelProperty("主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("模板ID")
    private String templateId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("X轴坐标")
    private int xAxis;

    @ApiModelProperty("Y轴坐标")
    private int yAxis;

    @ApiModelProperty("字体")
    private String typeface;

    @ApiModelProperty("字体样式(0:默认，1:加粗，2:斜体)")
    private int fontStyle;

    @ApiModelProperty("尺寸")
    private int fontSize;

    @ApiModelProperty("宽")
    private int imageWidth;

    @ApiModelProperty("高")
    private int imageHeight;

    @ApiModelProperty("字段名称")
    private String contentValue;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
