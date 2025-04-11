package com.mdsd.cloud.controller.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2022-04-07]
 */
@Getter
@Setter
@Accessors(chain = true) //Lombok实现实体类链式赋值注解
@ApiModel(value = "数据字典 - 获取枚举值_入参")
public class GetDictByCodesPao {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private List<String> code;
}
