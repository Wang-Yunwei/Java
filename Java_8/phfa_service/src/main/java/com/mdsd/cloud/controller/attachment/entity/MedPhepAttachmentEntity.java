package com.mdsd.cloud.controller.attachment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-04-11]
 */
@Getter
@Setter
@Accessors(chain = true) //Lombok实现实体类链式赋值注解
@TableName("MED_PHEP_ATTACHMENT")
@ApiModel(value = "MedPhepAttachment对象", description = "附件表")
public class MedPhepAttachmentEntity extends Model<MedPhepAttachmentEntity> {

    @ApiModelProperty("主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("业务ID")
    @TableField("BUSINESS_ID")
    private String businessId;

    @ApiModelProperty("文件名")
    @TableField("FILE_NAME")
    private String fileName;

    @ApiModelProperty("文件大小")
    @TableField("FILE_SIZE")
    private Long fileSize;

    @ApiModelProperty("文件类别(见库表备注)")
    @TableField("FILE_TYPE")
    private Integer fileType;

    @ApiModelProperty("物理路径")
    @TableField("PHYSICAL_PATH")
    private String physicalPath;

    @ApiModelProperty("请求路径")
    @TableField("REQUEST_PATH")
    private String requestPath;

    @ApiModelProperty("备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty("创建人ID")
    @TableField("CREATED_BY")
    private String createdBy;

    @ApiModelProperty("创建人名")
    @TableField("CREATED_NAME")
    private String createdName;

    @ApiModelProperty("创建时间")
    @TableField("CREATED_DATE")
    private LocalDateTime createdDate;

    @ApiModelProperty("更新人ID")
    @TableField("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty("更新人名")
    @TableField("UPDATE_NAME")
    private String updateName;

    @ApiModelProperty("更新时间")
    @TableField("UPDATE_DATE")
    private LocalDateTime updateDate;

    @ApiModelProperty("单位编码")
    @TableField("COMPANY_CODE")
    private String companyCode;

    @ApiModelProperty("单位简称")
    @TableField("COMPANY_NAME")
    private String companyName;

    @ApiModelProperty("组织编码")
    @TableField("ORG_CODE")
    private String orgCode;

    @ApiModelProperty("组织简称")
    @TableField("ORG_NAME")
    private String orgName;

    @ApiModelProperty("二级组织编码")
    @TableField("SECOND_ORG_CODE")
    private String secondOrgCode;

    @ApiModelProperty("二级组织简称")
    @TableField("SECOND_ORG_NAME")
    private String secondOrgName;

    @ApiModelProperty("删除标识(0:未删除,1:已删除)")
    @TableField("DELETE_FLAG")
    private Byte deleteFlag;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
