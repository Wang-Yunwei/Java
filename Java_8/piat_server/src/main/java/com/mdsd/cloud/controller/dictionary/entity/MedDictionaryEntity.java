package com.mdsd.cloud.controller.dictionary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author WangYunwei [2022-04-07]
 */
@Getter
@Setter
@TableName("MED_DICTIONARY")
@ApiModel(value = "MedDictionary对象", description = "字典表")
public class MedDictionaryEntity extends Model<MedDictionaryEntity> {

    @ApiModelProperty("主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("医院ID")
    @TableField("HOSPITAL_ID")
    private String hospitalId;

    @ApiModelProperty("编码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty("名称")
    @TableField("NAME")
    private String name;

    @JsonProperty(value = "value")
    @TableField("ITEMCODE")
    private String itemcode;
    
    @JsonProperty(value = "label")
    @TableField("ITEMNAME")
    private String itemname;

    @ApiModelProperty("备注")
    @TableField("DESCRIBE")
    private String describe;

    @ApiModelProperty("排序")
    @TableField("SORT")
    private BigDecimal sort;

    @ApiModelProperty("状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty("扩展（额外绑定关系）")
    @TableField("EXTEND")
    private String extend;

    @ApiModelProperty("项目ID")
    @TableField("APPID")
    private String appid;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
