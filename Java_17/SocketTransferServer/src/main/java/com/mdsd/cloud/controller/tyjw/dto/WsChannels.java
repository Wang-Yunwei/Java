package com.mdsd.cloud.controller.tyjw.dto;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author WangYunwei [2024-11-15]
 */
@Getter
@Setter
public class WsChannels {

    private String box;

    private String taskId;

    private Map<String, Channel> channels;
}
