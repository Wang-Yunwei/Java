package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huxiaoneng
 * @ClassName DrugsListVo
 * 
 * @Date 2021/11/18 9:18
 */
@Data
@ApiModel(value = "药品信息")
public class DrugsListVo implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String drugId;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String drugCode;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String drugName;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String category;

    /**
     * 生产企业
     */
    @ApiModelProperty(value = "生产企业")
    private String production;

    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    private String specifications;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String modifiedBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")

    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")

    private Date updateTime;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private int showOrder;

    /**
     * 药品单位
     */
    @ApiModelProperty(value = "药品单位")
    private String drugUnit;

    /**
     * 默认用量
     */
    @ApiModelProperty(value = "默认用量")
    private int drugDefaultDose;

    /**
     * 默认用法
     */
    @ApiModelProperty(value = "默认用法")
    private String drugDefaultUsage;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;
}
