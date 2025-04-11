package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-07-25]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
public class DasNotificationPo {

    @ApiModelProperty(value = "任务ID")
    private String missionId;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "项目标识")
    private String sysId;

    @ApiModelProperty(value = "事件标识")
    private String eventId;
}
