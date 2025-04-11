package com.mdsd.cloud.controller.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-05-17]
 */
@Getter
@Setter
@ApiModel(value = "DdcListOrDetailsRo对象", description = "数据字典 - 列表or详情_返参")
public class DdcListOrDetailsRo {

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;

    private String key;

    private String value;
}
