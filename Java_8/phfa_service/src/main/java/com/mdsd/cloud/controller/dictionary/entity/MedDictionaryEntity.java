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

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 医院ID
     */
    @ApiModelProperty("医院ID")
    @TableField("HOSPITAL_ID")
    private String hospitalId;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    @TableField("CODE")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @TableField("NAME")
    private String name;

    /**
     * value
     */
    @JsonProperty(value = "value")
    @TableField("ITEMCODE")
    private String itemcode;

    /**
     * label
     */
    @JsonProperty(value = "label")
    @TableField("ITEMNAME")
    private String itemname;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @TableField("DESCRIBE")
    private String describe;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @TableField("SORT")
    private BigDecimal sort;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @TableField("STATUS")
    private String status;

    /**
     * 扩展(额外绑定关系)
     */
    @ApiModelProperty("扩展（额外绑定关系）")
    @TableField("EXTEND")
    private String extend;

    /**
     * 项目ID
     */
    @ApiModelProperty("项目ID")
    @TableField("APPID")
    private String appid;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
