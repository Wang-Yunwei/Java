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
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-06-02]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok实现实体类链式赋值注解
@TableName("MED_PHEP_DRUG")
@ApiModel(value = "MedPhepDrug对象", description = "药品信息表")
public class MedPhepDrugEntity extends Model<MedPhepDrugEntity> {

    /**
     * 药品ID
     */
    @ApiModelProperty("药品ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 代码
     */
    @ApiModelProperty("代码")
    @TableField(value = "CODE")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @TableField(value = "NAME")
    private String name;

    /**
     * 类别
     */
    @ApiModelProperty("类别")
    @TableField(value = "CATEGORY")
    private String category;

    /**
     * 药品单位
     */
    @ApiModelProperty("药品单位")
    @TableField(value = "DRUG_UNIT")
    private String drugUnit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    @TableField(value = "SPECIFICATIONS")
    private String specifications;

    /**
     * 生产企业
     */
    @ApiModelProperty("生产企业")
    @TableField(value = "PRODUCTION")
    private String production;

    /**
     * 默认用法
     */
    @ApiModelProperty("默认用法")
    @TableField(value = "DEFAULT_USAGE")
    private String defaultUsage;

    /**
     * 默认用量
     */
    @ApiModelProperty("默认用量")
    @TableField(value = "DEFAULT_DOSE")
    private BigDecimal defaultDose;

    /**
     * 专科对应类别
     */
    @ApiModelProperty("专科对应类别")
    @TableField(value = "SPECIALIST_CATEGORY")
    private String specialistCategory;

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
