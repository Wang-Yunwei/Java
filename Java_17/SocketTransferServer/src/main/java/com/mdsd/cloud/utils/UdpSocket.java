package com.mdsd.cloud.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author WangYunwei [2024-09-14]
 */
@Component
public class UdpSocket {
    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

    @PostConstruct
    public void createUdpSocket() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF,4194304) // 4MB 发送缓冲区
                .option(ChannelOption.SO_RCVBUF, 4194304) // 4MB 接收缓冲区
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // // 使用池化缓冲区分配器
                .handler(new SimpleChannelInboundHandler<DatagramPacket>(false) {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket pak) throws Exception {
                        pak.content().readableBytes();
                        ArrayBlockingQueue<DatagramPacket> objects = new ArrayBlockingQueue<>(1024);
                        objects.offer(pak);
                    }
                });

    }

    @PreDestroy
    public void destroy() {
        eventExecutors.shutdownGracefully();
    }
}
