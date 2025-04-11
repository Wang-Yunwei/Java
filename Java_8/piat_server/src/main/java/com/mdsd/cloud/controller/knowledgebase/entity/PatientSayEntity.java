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
@TableName("MED_CLINIC_PATIENT_SAY")
@JsonIgnoreProperties(value = {"handler"})
public class PatientSayEntity extends Model {

    @TableId(value = "PATIENT_SAYID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String patientSayid;

    private String categoryId;

    private String description;

    private String isKey;

    private String pym;

    private BigDecimal sortValue;

    private String isValid;


    @TableField(exist = false)
    private List<KnowledgeEntity> knowledgeEntities;

}