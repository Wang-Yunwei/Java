package com.mdsd.cloud.controller.web.dto;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author WangYunwei [2024-11-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class WsChannelDetails {

    private String taskId;

    private String controlPower;

    private Map<String, Channel> channels;
}
