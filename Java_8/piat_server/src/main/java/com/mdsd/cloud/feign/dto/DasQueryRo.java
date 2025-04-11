package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-07-13]
 */
@Getter
@Setter
public class DasQueryRo {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "患者姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "出生年月")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "患者主诉")
    private String chiefComplaint;

    @ApiModelProperty(value = "呼叫时间")
    private LocalDateTime callTime;

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "患者ID")
    private String patientId;

    @ApiModelProperty(value = "推送时间")
    private LocalDateTime pushTime;

    @ApiModelProperty(value = "专科类型")
    private String specialized;

    @ApiModelProperty(value = "送达医院")
    private String toHospital;

    @ApiModelProperty(value = "来院")
    private String fromHospital;

    @ApiModelProperty(value = "诊断")
    private String diagnosis;

    @ApiModelProperty(value = "来院方式")
    private String comingType;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "内容")
    private String content;
}
