package com.mdsd.cloud.controller.secondarytriage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/

@Getter
@Setter
@TableName(value = "MED_CLINIC_SECONDCHECK")
@JsonIgnoreProperties(value = {"handler"})
public class ClinicSecondcheckEntity extends Model {
    private String id;

    private String regid;

    private String vitalsignsId;

    private LocalDateTime visitTime;

    private String resetLevel;

    private String resetArea;

    private String turnResult;

    private String departmentId;

    private String describe;

    private String nurseId;

    private LocalDateTime createTime;

    @TableField(exist = false)
    public ClinicVitalsignsEntity clinicvitalsignsentity;

    private String type;
}
