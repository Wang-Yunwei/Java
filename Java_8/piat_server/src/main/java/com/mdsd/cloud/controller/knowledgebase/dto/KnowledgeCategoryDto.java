package com.mdsd.cloud.controller.knowledgebase.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */

@Data
public class KnowledgeCategoryDto {
    @TableId(value = "CATEGORY_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String categoryId;

    @TableField("CATEGORY_NAME")
    private String categoryName;

    @TableField("MEDICINE_TYPE")
    private String medicineType;

    @TableField("SORT_VALUE")
    private BigDecimal sortValue;

    @TableField("PYM")
    private String pym;

    @TableField("IS_USED")
    private String isUsed;

}
