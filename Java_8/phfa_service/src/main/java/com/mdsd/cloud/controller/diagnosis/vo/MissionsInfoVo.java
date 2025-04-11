package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author huxiaoneng
 */
@Data
public class MissionsInfoVo {

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "呼叫时间")
    private Date callTime;

    @ApiModelProperty(value = "车载医生")
    private String doctorName;

    @ApiModelProperty(value = "车载护士")
    private String nurseName;

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "患者主诉")
    private String chiefComplaint;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "年龄拓展")
    private String ageExtend;

    @ApiModelProperty(value = "证件类型")
    private String cardType;

    @ApiModelProperty(value = "证件号码")
    private String cardNo;

    @ApiModelProperty(value = "出生年月")
    private Date birthday;
}
