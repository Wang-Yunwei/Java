package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-05-31]
 */
@Getter
@Setter
@TableName("MED_PHEP_STATION")
@ApiModel(value = "MedPhepStation对象", description = "急救站表")
public class MedPhepStationEntity extends Model<MedPhepStationEntity> {

    /**
     * 急救站ID
     */
    @ApiModelProperty("急救站ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 医院ID
     */
    @ApiModelProperty("医院ID")
    @TableField(value = "HOSPITAL_ID")
    private String hospitalId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    @TableField("HOSPITAL_AREA")
    private String hospitalArea;

    /**
     * 站点名称
     */
    @ApiModelProperty("站点名称")
    @TableField(value = "NAME")
    private String name;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    @TableField(value = "LONGITUDE")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    @TableField(value = "LATITUDE")
    private BigDecimal latitude;

    /**
     * 区域ID
     */
    @ApiModelProperty("区域ID")
    @TableField(value = "REGION_ID")
    private String regionId;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建人名
     */
    @ApiModelProperty("创建人名")
    @TableField("CREATED_NAME")
    private String createdName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("CREATED_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * 更新人ID
     */
    @ApiModelProperty("更新人ID")
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     * 更新人名
     */
    @ApiModelProperty("更新人名")
    @TableField("UPDATE_NAME")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField("UPDATE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 单位编码
     */
    @ApiModelProperty("单位编码")
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 单位简称
     */
    @ApiModelProperty("单位简称")
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织简称
     */
    @ApiModelProperty("组织简称")
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 二级组织编码
     */
    @ApiModelProperty("二级组织编码")
    @TableField("SECOND_ORG_CODE")
    private String secondOrgCode;

    /**
     * 二级组织简称
     */
    @ApiModelProperty("二级组织简称")
    @TableField("SECOND_ORG_NAME")
    private String secondOrgName;

    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @ApiModelProperty("删除标识(0:未删除,1:已删除)")
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
