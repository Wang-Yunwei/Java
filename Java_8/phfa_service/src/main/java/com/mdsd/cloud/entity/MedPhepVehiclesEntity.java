package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆信息
 *
 * @author wcq
 */
@Data
@TableName("MED_PHEP_VEHICLES")
public class MedPhepVehiclesEntity {

    /**
     * 车辆ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 车辆代码
     */
    @ApiModelProperty(value = "车辆代码")
    @TableField("CODE")
    private String code;

    /**
     * 车牌
     */
    @ApiModelProperty(value = "车牌")
    @TableField("LICENSE_PLATE")
    private String licensePlate;

    /**
     * 车载电话
     */
    @ApiModelProperty(value = "车载电话")
    @TableField("CAR_PHONE")
    private String carPhone;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("UPDATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     * 归属医院
     */
    @ApiModelProperty(value = "归属医院")
    @TableField("HOSPITAL_ID")
    private String hospitalId;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    @TableField("HOSPITAL_AREA")
    private String hospitalArea;

    /**
     * 行政区域ID
     */
    @ApiModelProperty(value = "行政区域ID")
    @TableField("REGION_ID")
    private String regionId;
}
