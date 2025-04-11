package com.mdsd.cloud.controller.consultation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-01-05]
 */
@Getter
@Setter
public class MccSaveOrUpdateParDto {

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID")
    private String patientId;

    /**
     * 患者姓名
     */
    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 群组成员
     */
    @ApiModelProperty(value = "群组成员")
    private String imUserids;

    /**
     * 申请会诊原因
     */
    @ApiModelProperty(value = "申请会诊原因")
    private String reason;

    /**
     * 当前登录用户ID
     */
    @ApiModelProperty(value = "当前登录用户ID")
    private String userId;

    /**
     * 业务token
     */
    @ApiModelProperty(value = "业务token")
    private String jwtToken;
}
