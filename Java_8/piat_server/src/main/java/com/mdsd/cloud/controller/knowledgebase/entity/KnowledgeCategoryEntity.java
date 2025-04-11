package com.mdsd.cloud.controller.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */

@Getter
@Setter
@TableName("MED_CLINIC_KNOWLEDGE_CATEGORY")
@JsonIgnoreProperties(value = {"handler"})
public class KnowledgeCategoryEntity extends Model {

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

    @TableField(exist = false)
    private List<PatientSayEntity> patientSayEntity;

}
