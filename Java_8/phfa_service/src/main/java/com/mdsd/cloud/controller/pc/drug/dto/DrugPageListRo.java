package com.mdsd.cloud.controller.pc.drug.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-06-02]
 */
@Getter
@Setter
@ApiModel(value = "DrugPageListRo对象", description = "药品 - 分页列表_返参")
public class DrugPageListRo {

    /**
     * 药品ID
     */
    @ApiModelProperty("药品ID")
    private String id;

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
     * 药品单位
     */
    @ApiModelProperty("药品单位")
    private String drugUnit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String specifications;

    /**
     * 生产企业
     */
    @ApiModelProperty("生产企业")
    private String production;

    /**
     * 默认用法
     */
    @ApiModelProperty("默认用法")
    private String defaultUsage;

    /**
     * 默认用量
     */
    @ApiModelProperty("默认用量")
    private BigDecimal defaultDose;

    /**
     * 字典值
     */
    @ApiModelProperty("字典值")
    private String dicValue;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @ApiModelProperty("删除标识(0:未删除,1:已删除)")
    private Integer deleteFlag;
}
