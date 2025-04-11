package com.mdsd.cloud.controller.consultation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-06-15]
 */
@Getter
@Setter
@ApiModel(value = "MccListRo对象", description = "移动会诊 - 列表_返参")
public class MccListRo {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private String age;

    /**
     * 年龄拓展
     */
    @ApiModelProperty(value = "年龄拓展")
    private String ageExtend;

    /**
     * 患者数据创建时间
     */
    @ApiModelProperty(value = "患者数据创建时间")
    private LocalDateTime createTime;

    /**
     * 疾病类别
     */
    @ApiModelProperty(value = "疾病类别")
    private String diseaseCategory;

    /**
     * 疾病类别名称
     */
    @ApiModelProperty(value = "疾病类别名称")
    private String diseaseCategoryStr;

    /**
     * 现场救治地址
     */
    @ApiModelProperty(value = "现场救治地址")
    private String address;

    /**
     * 主诉
     */
    @ApiModelProperty(value = "主诉")
    private String chiefComplaint;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    /**
     * 参与者ID
     */
    @ApiModelProperty(value = "参与者ID")
    private String imUserids;

    /**
     * 申请会诊原因
     */
    @ApiModelProperty(value = "申请会诊原因")
    private String reason;

    /**
     * IM群组ID
     */
    @ApiModelProperty(value = "IM群组ID")
    private String groupId;

    @ApiModelProperty(value = "打开IM地址")
    private String imUrl;
}
