package com.mdsd.cloud.controller.groupevents.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 模板对象
 */
@Data
@TableName(value = "MED_CLINIC_TEMPLATE")
public class TemplateEntity {


    /**
     * 模板ID
     */
    @TableField(value = "TEMPLATE_ID")
    private String templateId;

    /**
     * 内容
     */
    @TableField(value = "CONTENT")
    private String content;

    /**
     * x轴
     */
    @TableField(value = "X_AXIS")
    private int xAxis;

    /**
     * Y轴
     */
    @TableField(value = "Y_AXIS")
    private int yAxis;

    /**
     * 字体
     */
    @TableField(value = "TYPEFACE")
    private String typeface;

    /**
     * 字体样式
     */
    @TableField(value = "FONT_STYLE")
    private int fontStyle;

    /**
     * 字体尺寸
     */
    @TableField(value = "FONT_SIZE")
    private int fontSize;

    /**
     * 宽
     */
    @TableField(value = "IMAGE_WIDTH")
    private int imageWidth;

    /**
     * 高
     */
    @TableField(value = "IMAGE_HEIGHT")
    private int imageHeight;

    /**
     *
     */
    @TableField(value = "CONTENT_VALUE")
    private String contentValue;


}
