package com.example.controller.netty.service.impl;

import com.example.controller.netty.config.NettyConfig;
import com.example.controller.netty.service.PushService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2021-12-20]
 */
@Service
public class PushServiceImpl implements PushService {

    /**
     * 推送给指定用户
     */
    @Override
    public void pushMsgToOne(String userId, String msg) {

        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        channel.writeAndFlush(new TextWebSocketFrame(msg));

    }

    /**
     * 推送给所有用户
     */
    @Override
    public void pushMsgToAll(String msg) {

        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
