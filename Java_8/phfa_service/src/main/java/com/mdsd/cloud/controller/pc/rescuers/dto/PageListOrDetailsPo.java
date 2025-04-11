package com.mdsd.cloud.controller.pc.rescuers.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-12-06]
 */
@Getter
@Setter
@ApiModel(value = "PageListOrDetailsPo对象", description = "急救人员 - 分页or详情_入参")
public class PageListOrDetailsPo {

    @ApiModelProperty(value = "急救人员ID(注: ID不为空则查详情)")
    private String rescuerId;

    @ApiModelProperty(value = "姓名")
    private String rescuerName;

    @ApiModelProperty(value = "急救人员分类")
    private String rescuerType;

    @ApiModelProperty(value = "页码")
    private Long current = 0L;

    @ApiModelProperty(value = "页长")
    private Long size;
}
