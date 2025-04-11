package com.mdsd.cloud.controller.registration.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author WangYunwei [2021-11-08]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 急诊明细_入参")
public class TriageDetailedParamDto {

    @ApiModelProperty(value = "分诊ID")
    private String regId;

    @ApiModelProperty(value = "接口返回(注:后端自处理)")
    private HttpServletResponse response;
}
