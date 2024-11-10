package com.mdsd.cloud.utils;

import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.dji.dto.MdsdProtoBuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author WangYunwei [2024-09-14]
 */
@Slf4j
public class UdpSocket {
    public Channel createUdpSocket(ChannelHandler handler, int port) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventExecutors)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 4194304) // 4MB 发送缓冲区
                .option(ChannelOption.SO_RCVBUF, 4194304) // 4MB 接收缓冲区
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // // 使用池化缓冲区分配器
                .handler(handler);
        try {
            log.info(">>> 启动 UDP ,监听端口: {}", port);
            ChannelFuture sync = bootstrap.bind(port).sync();
            return sync.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
