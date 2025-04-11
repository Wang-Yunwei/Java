package com.mdsd.cloud.controller.secondarytriage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/
@Getter
@Setter
@TableName(value = "MED_CLINIC_VITALSIGNS")
@JsonIgnoreProperties(value = {"handler"})
public class ClinicVitalsignsEntity extends Model {
    private String id;

    private String regid;

    private String area;

    private String haslife;

    private String notprbp;

    private Double temperature;

    private BigDecimal plus;

    private BigDecimal breath;

    private Double sbp;

    private Double dbp;

    private Double spo;

    private String mind;

    private LocalDateTime acqTime;
}
