package com.mdsd.cloud.controller.other.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-08-12]
 */
@Getter
@Setter
public class UsingDrugsDto {

    /**
     * 药品名称
     */
    @ApiModelProperty("药品名称")
    private String name;

    /**
     * 默认用法
     */
    @ApiModelProperty("默认用法")
    private String defaultUsage;

    /**
     * 默认用量
     */
    @ApiModelProperty("默认用量")
    @TableField(value = "DEFAULT_DOSE")
    private BigDecimal defaultDose;

    /**
     * 药品单位
     */
    @ApiModelProperty("药品单位")
    private String drugUnit;

    /**
     * 用药时间
     */
    @ApiModelProperty("用药时间")
    private LocalDateTime createTime;
}
