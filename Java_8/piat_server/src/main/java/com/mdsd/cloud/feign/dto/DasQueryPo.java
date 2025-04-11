package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-07-13]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok实现实体类链式赋值注解
public class DasQueryPo {

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "患者姓名")
    private String name;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    private final boolean includeEmpty = true;

    @ApiModelProperty(value = "状态")
    private final String status = "1";

    @ApiModelProperty(value = "系统ID")
    private final String sysId = "piat";

    private final Integer dateType = 1;
}
