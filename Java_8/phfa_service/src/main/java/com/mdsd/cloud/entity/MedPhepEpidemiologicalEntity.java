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
 * @ClassName MedPhepEpidemiologicalEntity 新冠肺炎流调管理
 * @Date 2021/11/12 15:17
 */
@Getter
@Setter
@TableName(value = "MED_PHEP_EPIDEMIOLOGICAL")
public class MedPhepEpidemiologicalEntity {

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
     * 电话号码
     */
    @TableField(value = "TEL")
    private String tel;

    /**
     * 省（现住址）
     */
    @TableField(value = "PROVINCE")
    private String province;

    /**
     * 市县（现住址）
     */
    @TableField(value = "CITY")
    private String city;

    /**
     * 镇（现住址）
     */
    @TableField(value = "TOWN")
    private String town;

    /**
     * 最近14天是否有发热（是、否）
     */
    @TableField(value = "IS_FEVER")
    private String isFever;

    /**
     * 14天内是否接触新冠确诊者（是、否）
     */
    @TableField(value = "IS_CONTACT")
    private String isContact;

    /**
     * 是否有中高风险旅行史（是、否）
     */
    @TableField(value = "IS_TRAVEL")
    private String isTravel;

    /**
     * 是否有境外中高风险旅行史（是、否）
     */
    @TableField(value = "IS_FTRAVEL")
    private String isFtravel;

    /**
     * 健康码状态（红，黄，绿）
     */
    @TableField(value = "HEALTH_CODE")
    private String healthCode;

    /**
     * 行程码状态（红，橙，黄，绿）
     */
    @TableField(value = "TOUR_CODE")
    private String tourCode;

    /**
     * 已知情同意
     */
    @TableField(value = "IS_INFORMED")
    private String isInformed;

    /**
     * 流调人员签名
     */
    @TableField(value = "ERESERVERS")
    private String ereservers;

    /**
     * 医护签名
     */
    @TableField(value = "MEDICAL_WORKERS")
    private String medicalWorkers;

    /**
     * 提交人
     */
    @TableField(value = "CREATE_ID")
    private String createId;

    /**
     * 提交时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 详细地址
     */
    @TableField(value = "FULLADDRESS")
    private String fullAddress;

    /**
     * 体温
     */
    @TableField(value = "T")
    private float t;
}
