package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 */
@Data
@TableName(value = "MED_PHEP_MEASURES")
public class MedPhepMeasuresEntity {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 患者ID
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 措施标识
     */
    @TableField(value = "MEASURE_KEY")
    private String measureKey;

    /**
     * 措施名称
     */
    @TableField(value = "MEASURE_NAME")
    private String measureName;

    /**
     * 实施时间
     */
    @TableField(value = "OP_TIME")
    private LocalDateTime opTime;
}
