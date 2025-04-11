package com.mdsd.cloud.controller.diagnosis.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author huxiaoneng
 */
@Data
public class PhepMissionsHistoryListVo {

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "患者Id")
    private String patientId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "年龄拓展")
    private String ageExtend;

    @ApiModelProperty(value = "接收任务时间")

    private Date acceptMissionTime;

    @ApiModelProperty(value = "现场地址")
    @TableField("DESTINATION_ADDRESS")
    private String destinationAddress;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "任务类型")
    private String missionType;

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "交接时间")
    private LocalDateTime shiftTime;

    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;
}
