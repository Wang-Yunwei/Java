package com.mdsd.cloud.controller.firstaidbigscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-04-06]
 */
@Getter
@Setter
@ApiModel(value = "急救公告大屏 - 时间轴_返参")
public class TimeAxisReo {

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

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
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间")
    private LocalDateTime callTime;

    /**
     * 派车时间
     */
    @ApiModelProperty(value = "派车时间")
    private LocalDateTime planVehicleTime;

    /**
     * 出车时间
     */
    @ApiModelProperty(value = "出车时间")
    private LocalDateTime dispatchVehicleTime;

    /**
     * 到达现场时间
     */
    @ApiModelProperty(value = "到达现场时间")
    private LocalDateTime arrivedSceneTime;

    /**
     * 送往医院时间
     */
    @ApiModelProperty(value = "送往医院时间")
    private LocalDateTime leftSceneTime;

    /**
     * 预计到医院时间
     */
    @ApiModelProperty(value = "预计到医院时间")
    private LocalDateTime expectHospitalTime;

    /**
     * 到达医院时间
     */
    @ApiModelProperty(value = "到达医院时间")
    private LocalDateTime arrivedHospitalTime;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分钟")
    private String dispatchVehicleName;
}
