package com.mdsd.cloud.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WangYunwei [2024-09-14]
 */
@Slf4j
public class UdpSocket {

    public static Channel createUdpSocket(ChannelHandler handler, int port) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventExecutors)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 4194304) // 4MB 发送缓冲区
                .option(ChannelOption.SO_RCVBUF, 4194304) // 4MB 接收缓冲区
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // // 使用池化缓冲区分配器
                .handler(handler);
        try {
            log.info(">>> 启动 UDP 监听端口: {}", port);
            ChannelFuture sync = bootstrap.bind(port).sync();
            return sync.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
