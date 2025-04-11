package com.mdsd.cloud.controller.other.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-01-06]
 */
@Getter
@Setter
@ApiModel(value = "患者疾病")
public class MedicalRecordInfoDto {

    @ApiModelProperty(value = "疾病类别")
    private String diseaseCategory;

    @ApiModelProperty(value = "专科类别")
    private String category;

    @ApiModelProperty(value = "病史提供类型")
    private String medicalHisProType;

    @ApiModelProperty(value = "病史提供者")
    private String historyProvider;

    @ApiModelProperty(value = "发病时间")
    private String isMorb;

    @ApiModelProperty(value = "患者主诉")
    private String chiefComplaint;

    @ApiModelProperty(value = "现病史")
    private String presentIllness;

    @ApiModelProperty(value = "既往史")
    private String pastHistory;

    @ApiModelProperty(value = "过敏史")
    private String allergicHistory;

    @ApiModelProperty(value = "现场救治地点")
    private String address;

    @ApiModelProperty(value = "发病时间")
    private String morbtimearea;

    @ApiModelProperty(value = "病情判断")
    private String conditionGrade;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "出诊结果")
    private String treatmentResult;

    @ApiModelProperty(value = "出诊结果")
    private String treatmentResultName;

    @ApiModelProperty(value = "医院名称")
    private String toHospitalName;

    @ApiModelProperty(value = "是否通知医院")
    private int isNotifyEmergency;

    @ApiModelProperty(value = "通知急诊科时间")
    private LocalDateTime notifyEmergencyTime;

    @ApiModelProperty(value = "呼救原因")
    private String reasonType;

    @ApiModelProperty(value = "病情转归")
    private String rescueChangeResult;

    @ApiModelProperty(value = "病情转归")
    private String rescueChangeResultName;
}
