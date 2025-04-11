package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok实现实体类链式赋值注解
public class DasNotificationPo {

    @ApiModelProperty(value = "分诊号")
    private String hisNo;

    private final String mdtType = "2";

    private final String dataSource = "21";

    @ApiModelProperty(value = "院区")
    private String hospitalArea;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "系统编号")
    private String sysId = "piat";

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;
}
