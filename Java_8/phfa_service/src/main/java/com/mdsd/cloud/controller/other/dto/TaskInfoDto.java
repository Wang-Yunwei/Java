package com.mdsd.cloud.controller.other.dto;

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
@ApiModel(value = "任务信息")
public class TaskInfoDto {

    @ApiModelProperty(value = "任务类型")
    private String missionType;

    @ApiModelProperty(value = "接收任务时间")
    private LocalDateTime acceptMissionTime;

    @ApiModelProperty(value = "呼叫时间")
    private LocalDateTime callTime;

    @ApiModelProperty(value = "派车时间")
    private LocalDateTime planVehicleTime;

    @ApiModelProperty(value = "出车时间")
    private LocalDateTime dispatchVehicleTime;

    @ApiModelProperty(value = "到达现场时间")
    private LocalDateTime arrivedSceneTime;

    @ApiModelProperty(value = "离开现场时间")
    private LocalDateTime leftSceneTime;

    @ApiModelProperty(value = "出车分站-Code")
    private String dispatchVehicleSite;

    @ApiModelProperty(value = "出车分站-Name")
    private String dispatchVehicleSiteName;

    @ApiModelProperty(value = "现场地址")
    private String destinationAddress;

    @ApiModelProperty(value = "车载医生")
    private String doctorName;

    @ApiModelProperty(value = "车载护士")
    private String nurseName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "医疗接触时间")
    private LocalDateTime touchPatientTime;

    @ApiModelProperty(value = "到达医院时间")
    private LocalDateTime arrivedHospitalTime;

    @ApiModelProperty(value = "预警时间")
    private LocalDateTime noticeTime;
}
