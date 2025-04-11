package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-12-31]
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_MOBILE_CONSULTATION")
public class MedPhepMobileConsultationEntity extends Model<MedPhepMobileConsultationEntity> {

    /**
     * 移动会诊表_主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 群组ID
     */
    @TableField("GROUP_ID")
    private String groupId;

    /**
     * 群组成员
     */
    @TableField("IM_USERIDS")
    private String imUserids;

    /**
     * 车牌
     */
    @TableField("LICENSE_PLATE")
    private String licensePlate;

    /**
     * 患者ID
     */
    @TableField("PATIENT_ID")
    private String patientId;

    /**
     * 申请会诊原因
     */
    @TableField("REASON")
    private String reason;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
