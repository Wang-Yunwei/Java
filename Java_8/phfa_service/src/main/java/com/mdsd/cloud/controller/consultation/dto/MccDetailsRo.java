package com.mdsd.cloud.controller.consultation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-01-14]
 */
@Getter
@Setter
@ApiModel(value = "移动会诊 - 患者详情_返参")
public class MccDetailsRo {

    @ApiModelProperty(value = "主键ID")
    private String patientId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "疾病类别")
    private String diseaseCategory;

    @ApiModelProperty(value = "主诉")
    private String chiefComplaint;

    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    @ApiModelProperty(value = "群组ID")
    private String groupId;
}
