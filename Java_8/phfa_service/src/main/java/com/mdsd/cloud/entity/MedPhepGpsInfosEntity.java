package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-11-15]
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_GPS_INFOS")
public class MedPhepGpsInfosEntity extends Model<MedPhepGpsInfosEntity> {

    /**
     * GPS定位信息表_主键ID
     */
    @ApiModelProperty(value = "GPS定位信息表_主键ID(新增: 后端自处理)")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 设备ID
     */
    @TableField("EQUIPMENT_ID")
    private String equipmentId;

    /**
     * 纬度
     */
    @TableField("LATITUDE")
    private BigDecimal latitude;

    /**
     * 经度
     */
    @TableField("LONGITUDE")
    private BigDecimal longitude;

    /**
     * 所属任务
     */
    @TableField("MISSION_ID")
    private String missionId;

    /**
     * 采集时间
     */
    @TableField("RECORDING_TIME")
    private LocalDateTime recordingTime;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
