package com.mdsd.cloud.controller.pc.emergencystation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-05-31]
 */
@Getter
@Setter
@ApiModel(value = "EscPageListPo对象", description = "急救站 - 分页列表_入参")
public class EscPageListPo {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    private String hospitalArea;

    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    private String name;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private String regionId;

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
