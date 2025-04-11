package com.mdsd.cloud.controller.pc.drug.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-02]
 */
@Getter
@Setter
@ApiModel(value = "DrugPageListPo对象", description = "药品 - 分页列表_入参")
public class DrugPageListPo {

    /**
     * 代码
     */
    @ApiModelProperty("代码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 类别
     */
    @ApiModelProperty("类别")
    private String category;

    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @ApiModelProperty("删除标识(0:未删除,1:已删除)")
    private Integer deleteFlag;

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
