package com.mdsd.cloud.controller.pc.drug.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-06]
 */
@Getter
@Setter
@ApiModel(value = "SpecialistCategoryPo对象", description = "药品 - 专科对应类别_入参")
public class SpecialistCategoryPo {

    /**
     * 操作(0-保存or更新;1-删除;2-列表)
     */
    @ApiModelProperty(value = "操作(0-保存or更新;1-删除;2-列表)")
    private Integer operation;

    /**
     * 药品ID
     */
    @ApiModelProperty(value = "药品ID")
    private String id;

    /**
     * 专科名称
     */
    @ApiModelProperty(value = "专科名称")
    private String name;

    /**
     * 对应类别
     */
    @ApiModelProperty(value = "对应类别")
    private String value;
}
