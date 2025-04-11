package com.mdsd.cloud.controller.pc.emergencystation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-31]
 */
@Getter
@Setter
@ApiModel(value = "EscPageListRo对象", description = "急救站 - 分页列表_返参")
public class EscPageListRo {

    /**
     * 急救站ID
     */
    @ApiModelProperty("急救站ID")
    private String id;

    /**
     * 医院ID
     */
    @ApiModelProperty("医院ID")
    private String hospitalId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    private String hospitalArea;

    /**
     * 站点名称
     */
    @ApiModelProperty("站点名称")
    private String name;

    /**
     * 区域ID
     */
    @ApiModelProperty("区域ID")
    private String regionId;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal latitude;

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
