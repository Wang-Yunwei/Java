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
@ApiModel(value = "GetDictCodePo对象", description = "数据字典 - 获取字典编码_入参")
public class GetDictCodePo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
}
