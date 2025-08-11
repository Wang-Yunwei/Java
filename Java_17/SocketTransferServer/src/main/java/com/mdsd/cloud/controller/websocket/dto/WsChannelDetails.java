package com.mdsd.cloud.controller.websocket.dto;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author WangYunwei [2024-11-15]
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class WsChannelDetails {

    private String taskId;

    private String controlPower;

    private Map<String, Channel> channels;
}
