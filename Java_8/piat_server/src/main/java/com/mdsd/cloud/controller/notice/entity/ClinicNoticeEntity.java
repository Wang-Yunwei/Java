package com.mdsd.cloud.controller.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description :通知
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/

@Getter
@Setter
@TableName(value = "MED_CLINIC_NOTIC")
public class ClinicNoticeEntity extends Model {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "NOTICE_TYPE")
    private String noticeType;

    @TableField(value = "BUSINESS_ID")
    private String businessId;

    @TableField(value = "SEND_TIME")
    private LocalDateTime sendTime;

    @TableField(value = "CONTENT")
    private String content;
}
