package com.mdsd.cloud.controller.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */
@Getter
@Setter
@TableName("MED_CLINIC_KNOWLEDGE")
public class KnowledgeEntity extends Model {

    @TableId(value = "KNOWLEDGE_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String knowledgeId;

    @TableField("CATEGORY_ID")
    private String categoryId;

    @TableField("PATIENT_SAYID")
    private String patientSayid;

    @TableField("SYSTEM_LEVEL")
    private String systemLevel;

    @TableField("SYSTEM_AREA")
    private String systemArea;

    @TableField("KNOWLEDGE_DETAIL")
    private String knowledgeDetail;

    @TableField("SORT_VALUE")
    private BigDecimal sortValue;

    @TableField("IS_VALID")
    private String isValid;
    
    private String medicineType;
}