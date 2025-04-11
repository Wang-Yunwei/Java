package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-10-26]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 新增or更新_修改分诊分级原因_入参")
public class ModSaveOrUpdateParamDto {

    @ApiModelProperty(value = "分诊表ID")
    private String regId;

    @ApiModelProperty(value = "其他说明")
    private String other;

    @ApiModelProperty(value = "原因")
    private String reasons;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
