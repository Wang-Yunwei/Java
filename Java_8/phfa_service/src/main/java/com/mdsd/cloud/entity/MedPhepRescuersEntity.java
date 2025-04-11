package com.mdsd.cloud.entity;

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

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-12-06]
 */
@Getter
@Setter
@TableName("MED_PHEP_RESCUERS")
@ApiModel(value = "MedPhepRescuers对象", description = "急救人员表")
public class MedPhepRescuersEntity extends Model<MedPhepPoctEntity> {

    @ApiModelProperty(value = "主键")
    @TableId(value = "RESCUER_ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String rescuerId;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "RESCUER_NAME")
    private String rescuerName;

    @ApiModelProperty(value = "急救人员分类")
    @TableField(value = "RESCUER_TYPE")
    private String rescuerType;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_BY")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "MODIFIED_BY")
    private String modifiedBy;

    @ApiModelProperty(value = "医院ID")
    @TableField(value = "HOSPITAL_ID")
    private String hospitalId;

    @ApiModelProperty(value = "状态")
    @TableField(value = "STATUS")
    private String status;

    @ApiModelProperty(value = "院区")
    @TableField(value = "HOSPITAL_AREA")
    private String hospitalArea;

    @Override
    public Serializable pkVal() {

        return this.rescuerId;
    }
}
