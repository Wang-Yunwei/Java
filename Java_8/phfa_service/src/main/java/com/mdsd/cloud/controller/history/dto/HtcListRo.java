package com.mdsd.cloud.controller.history.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-28]
 */
@Getter
@Setter
@ApiModel(value = "HtcListPo对象", description = "历史任务 - 列表_返参")
public class HtcListRo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;

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
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间")
    private String callTime;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")
    private String status;

    /**
     * 现场救治地点
     */
    @ApiModelProperty(value = "现场救治地点")
    private String address;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private String submitTime;

    /**
     * 病历状态
     */
    @ApiModelProperty(value = "病历状态")
    private String medicalStatus;
}
