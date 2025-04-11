package com.mdsd.cloud.controller.knowledgebase.entity;

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
@TableName("MED_CLINIC_TRIAGE_FAST")
public class OftenChiefComplaintEntity extends Model {

    @TableId(value = "FAST_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String fastId;

    private String triageFastName;

    private String triageFastPym;

    private String medicineType;

    private String categoryId;

    private String patientSayid;

    private String knowledgeId;

    private String triageLevel;

    private BigDecimal sortNo;

    private String isUsed;


}