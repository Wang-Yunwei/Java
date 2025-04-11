package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-10-12]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 到院信息")
public class ArriveInfoDto {

    @ApiModelProperty(value = "到院时间")
    private LocalDateTime arriveTime;

    @ApiModelProperty(value = "发病时间")
    private LocalDateTime diseaseTime;

    @ApiModelProperty(value = "120接诊时间")
    private LocalDateTime acceptTime;

    @ApiModelProperty(value = "120接诊地址")
    private String acceptAddress;

    @ApiModelProperty(value = "发病地址")
    private String address;

    @ApiModelProperty(value = "陪送/110/120/相关人员地址")
    private String arrivewithAddress;

    @ApiModelProperty(value = "陪送/110/120/相关人员姓名")
    private String arrivewithName;

    @ApiModelProperty(value = "陪送/110/120/相关人员电话")
    private String arrivewithTel;
}
