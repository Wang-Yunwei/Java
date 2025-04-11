package com.mdsd.cloud.controller.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-08-15]
 */
@Getter
@Setter
@ApiModel(value = "RedisSaveToListPo对象", description = "Redis - 保存or更新_入参")
public class RedisSaveToListPo {

    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    private String moduleName;

    /**
     * 键
     */
    @ApiModelProperty(value = "键")
    private String key;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;
}
