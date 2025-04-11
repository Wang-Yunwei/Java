package com.mdsd.cloud.controller.pc.drug.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author WangYunwei [2022-06-24]
 */
@Getter
@Setter
@ApiModel(value = "DrugGroupByListRo对象", description = "药品 - 分组列表_返参")
public class DrugGroupByListRo {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("名称")
    @TableField(value = "NAME")
    private String name;

    @ApiModelProperty("类别")
    @TableField(value = "CATEGORY")
    private String category;

    @ApiModelProperty("药品单位")
    @TableField(value = "DRUG_UNIT")
    private String drugUnit;

    @ApiModelProperty("默认用法")
    @TableField(value = "DEFAULT_USAGE")
    private String defaultUsage;

    @ApiModelProperty("默认用量")
    @TableField(value = "DEFAULT_DOSE")
    private BigDecimal defaultDose;
}
