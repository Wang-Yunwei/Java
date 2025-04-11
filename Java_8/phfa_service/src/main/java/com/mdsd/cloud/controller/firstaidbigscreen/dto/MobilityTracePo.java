package com.mdsd.cloud.controller.firstaidbigscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-16]
 */
@Getter
@Setter
@ApiModel(value = "MobilityTracePo对象", description = "急救公告大屏 - 移动轨迹_入参")
public class MobilityTracePo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    private String moduleName;

    /**
     * Key(可不传,默认值:inform)
     */
    private String key = "inform";
}
