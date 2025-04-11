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

    /**
     * 字典ID
     */
    @ApiModelProperty(value = "字典ID")
    private String dictId;

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Long current;

    /**
     * 页长
     */
    @ApiModelProperty(value = "页长")
    private Long size;
}
