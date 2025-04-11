package com.mdsd.cloud.controller.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-04-07]
 */
@Getter
@Setter
@ApiModel(value = "数据字典 - 列表or详情_入参")
public class DdcListOrDetailsPo {

    @ApiModelProperty(value = "字典ID")
    private String dictId;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "页码")
    private Long current;

    @ApiModelProperty(value = "页码")
    private Long size;
}
