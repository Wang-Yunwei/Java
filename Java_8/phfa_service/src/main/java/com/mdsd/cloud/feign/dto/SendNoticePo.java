package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-07-21]
 */
@Getter
@Setter
public class SendNoticePo {

    /**
     * 接受消息的用户id列表
     */
    @ApiModelProperty(value = "接受消息的用户id列表")
    private List<String> userIds;

    /**
     * 发送的通知内容
     */
    @ApiModelProperty(value = "发送的通知内容")
    private String message;

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID")
    private String missionId;
}
