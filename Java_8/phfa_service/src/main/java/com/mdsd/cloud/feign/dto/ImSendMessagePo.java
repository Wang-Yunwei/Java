package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-14]
 */
@Getter
@Setter
@ApiModel(value = "ImSendMessagePo对象", description = "IM - 给群发送消息_入参")
public class ImSendMessagePo {

    @ApiModelProperty(value = "群组ID")
    private String channelId;

    @ApiModelProperty(value = "w文字 i文件 m媒体 sl超链")
    private String msgType;

    @ApiModelProperty(value = "发送者ID")
    private String senderId;

    @ApiModelProperty(value = "内容")
    private String content;
}
