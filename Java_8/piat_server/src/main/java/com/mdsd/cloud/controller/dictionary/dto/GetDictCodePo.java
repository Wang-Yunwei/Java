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

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典名称")
    private String dictName;
}
