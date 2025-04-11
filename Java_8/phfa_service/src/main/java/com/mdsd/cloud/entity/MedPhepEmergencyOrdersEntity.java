package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 * @ClassName MedPhepEmergencyOrdersEntity 用药信息
 * @Date 2021/11/12 13:30
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_EMERGENCY_ORDERS")
public class MedPhepEmergencyOrdersEntity {

    /**
     * 主键
     */
    @TableId(value = "ORDER_ID", type = IdType.ASSIGN_UUID)
    private String orderId;

    /**
     * 所属患者
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 药品组套ID
     */
    @TableField(value = "STACK_ID")
    private String stackId;

    /**
     * 药品ID
     */
    @TableField(value = "DRUG_ID")
    private String drugId;

    /**
     * 用量
     */
    @TableField(value = "DOSE")
    private int dose;

    /**
     * 用法（数据字典表主键）
     */
    @TableField(value = "USAGE_ID")
    private String usageId;

    /**
     * 实际用法
     */
    @TableField(value = "USAGE_DESC")
    private String usageDesc;

    /**
     * 用药时间
     */
    @TableField(value = "CREATETIME")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "CREATEBY")
    private String createBy;

    /**
     * 规格
     */
    @TableField(value = "SPECIFICATIONS")
    private String specifications;

    /**
     * 是否用于交接
     */
    @TableField(value = "STATUS")
    private String status;

    /**
     * 状态
     */
    @TableField(value = "IS_HAND")
    private String isHand;
}
