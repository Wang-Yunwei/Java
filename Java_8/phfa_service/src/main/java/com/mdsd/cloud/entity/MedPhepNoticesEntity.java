package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2022-08-05]
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("MED_PHEP_NOTICES")
@ApiModel(value = "MedPhepNoticesEntity对象", description = "预警消息表")
public class MedPhepNoticesEntity extends Model<MedPhepNoticesEntity> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "患者ID")
    @TableField("PATIENT_ID")
    private String patientId;

    @ApiModelProperty(value = "任务ID")
    @TableField("MISSION_ID")
    private String missionId;

    @ApiModelProperty(value = "消息ID")
    @TableField("MESSAGE_ID")
    private String messageId;

    @ApiModelProperty(value = "消息发送范围")
    @TableField("MESSAGE_SCOPE")
    private String messageScope;

    @ApiModelProperty(value = "消息类型")
    @TableField("MESSAGE_TYPE")
    private String messageType;

    @ApiModelProperty(value = "发送消息用户ID")
    @TableField("FROM_USER_ID")
    private String fromUserId;

    @ApiModelProperty(value = "发送消息用户姓名")
    @TableField("FROM_USER_NAME")
    private String fromUserName;

    @ApiModelProperty(value = "发送到医院ID")
    @TableField("SEND_TO_HOSPITAL_ID")
    private String sendToHospitalId;

    @ApiModelProperty(value = "发送到医院名称")
    @TableField("SEND_TO_HOSPITAL_NAME")
    private String sendToHospitalName;

    @ApiModelProperty(value = "消息内容")
    @TableField("MESSAGE_CONTENT")
    private String messageContent;

    @ApiModelProperty(value = "是否需要回复")
    @TableField("IS_NEED_REPLY")
    private String isNeedReply;

    @ApiModelProperty(value = "通知时间")
    @TableField("NOTICE_TIME")
    private LocalDateTime noticeTime;

    @ApiModelProperty(value = "是否回复")
    @TableField("IS_REPLAY")
    private String isReplay;

    @ApiModelProperty(value = "回复时间")
    @TableField("REPLY_TIME")
    private LocalDateTime replyTime;

    @ApiModelProperty(value = "拒绝理由")
    @TableField("REJECT_REASON")
    private String rejectReason;

    @Override
    public Serializable pkVal() {

        return this.id;
    }
}
