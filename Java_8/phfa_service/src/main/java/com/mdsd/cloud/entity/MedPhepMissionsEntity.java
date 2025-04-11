package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务信息
 *
 * @author wcq
 */
@Data
@TableName("MED_PHEP_MISSIONS")
public class MedPhepMissionsEntity {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    @TableId(value = "MISSION_ID", type = IdType.ASSIGN_UUID)
    private String missionId;

    /**
     * 事件ID
     */
    @ApiModelProperty(value = "事件ID")
    @TableField("NCIDENT_ID")
    private String ncidentId;

    /**
     * 任务类型
     */
    @ApiModelProperty(value = "任务类型")
    @TableField("MISSION_TYPE")
    private String missionType;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @TableField("CONTACT")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField("CONTACT_PHONE")
    private String contactPhone;

    /**
     * 呼叫时间
     */
    @ApiModelProperty(value = "呼叫时间")
    @TableField("CALL_TIME")
    private LocalDateTime callTime;

    /**
     * 接收任务时间
     */
    @ApiModelProperty(value = "接收任务时间")
    @TableField("ACCEPT_MISSION_TIME")
    private LocalDateTime acceptMissionTime;

    /**
     * 派车时间
     */
    @ApiModelProperty(value = "派车时间")
    @TableField("PLAN_VEHICLE_TIME")
    private LocalDateTime planVehicleTime;

    /**
     * 出车时间
     */
    @ApiModelProperty(value = "出车时间")
    @TableField("DISPATCH_VEHICLE_TIME")
    private LocalDateTime dispatchVehicleTime;

    /**
     * 到达现场时间
     */
    @ApiModelProperty(value = "到达现场时间")
    @TableField("ARRIVED_SCENE_TIME")
    private LocalDateTime arrivedSceneTime;

    /**
     * 接触患者时间
     */
    @ApiModelProperty(value = "接触患者时间")
    @TableField("TOUCH_PATIENT_TIME")
    private LocalDateTime touchPatientTime;

    /**
     * 送往医院时间
     */
    @ApiModelProperty(value = "送往医院时间")
    @TableField("LEFT_SCENE_TIME")
    private LocalDateTime leftSceneTime;

    /**
     * 到达医院时间
     */
    @ApiModelProperty(value = "到达医院时间")
    @TableField("ARRIVED_HOSPITAL_TIME")
    private LocalDateTime arrivedHospitalTime;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @TableField("COMPLETE_TIME")
    private LocalDateTime completeTime;

    /**
     * 呼救原因
     */
    @ApiModelProperty(value = "呼救原因")
    @TableField("REASON_TYPE")
    private String reasonType;

    /**
     * MPDS
     */
    @ApiModelProperty(value = "MPDS")
    @TableField("MPDS")
    private String mpds;

    /**
     * 是否需要担架
     */
    @ApiModelProperty(value = "是否需要担架")
    @TableField("NEED_STRETCHER")
    private String needStretcher;

    /**
     * ID120
     */
    @ApiModelProperty(value = "ID120")
    @TableField("ID_120")
    private String id_120;

    /**
     * 来源类型
     */
    @ApiModelProperty(value = "来源类型")
    @TableField("FROM_TYPE")
    private String fromType;

    /**
     * 车载司机
     */
    @ApiModelProperty(value = "车载司机")
    @TableField("DRIVER_NAME")
    private String driverName;

    /**
     * 车载医生
     */
    @ApiModelProperty(value = "车载医生")
    @TableField("DOCTOR_NAME")
    private String doctorName;

    /**
     * 车载护士
     */
    @ApiModelProperty(value = "车载护士")
    @TableField("NURSE_NAME")
    private String nurseName;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分站")
    @TableField("DISPATCH_VEHICLE_SITE")
    private String dispatchVehicleSite;

    /**
     * 现场地址
     */
    @ApiModelProperty(value = "现场地址")
    @TableField("DESTINATION_ADDRESS")
    private String destinationAddress;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 途中待命时间
     */
    @ApiModelProperty(value = "途中待命时间")
    @TableField("TASK_STANDBY_TIME")
    private LocalDateTime taskStandbyTime;

    /**
     * 站内待命时间
     */
    @ApiModelProperty(value = "站内待命时间")
    @TableField("SITE_STANDBY_TIME")
    private LocalDateTime siteStandbyTime;

    /**
     * 任务终止类型
     */
    @ApiModelProperty(value = "任务终止类型")
    @TableField("SUSPEND_TYPE")
    private String suspendType;

    /**
     * 备忘
     */
    @ApiModelProperty(value = "备忘")
    @TableField("MEMO")
    private String memo;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    @TableField("LICENSE_PLATE")
    private String licensePlate;

    /**
     * 任务终止原因
     */
    @ApiModelProperty(value = "任务终止原因")
    @TableField("SUSPEND_REASON")
    private String suspendReason;
}
