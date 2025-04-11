package com.mdsd.cloud.controller.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-05-17]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok实现实体类链式赋值注解
@ApiModel(value = "GetDictCodePo对象", description = "数据字典 - 获取字典编码_返参")
public class GetDictCodeRo {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;
}
