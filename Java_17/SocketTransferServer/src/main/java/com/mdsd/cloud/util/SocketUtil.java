package com.mdsd.cloud.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2025-03-05]
 */
@Slf4j
public class SocketUtil {

    public static void createWebSocketServer(ChannelHandler var1, int port) {
        // 创建服务端启动引导器, 配置线程模型  EventLoop 等于 Thread
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 来作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 32) // 设置监听队列的最大长度 ,受Linux的 /proc/sys/net/core/somaxconn 影响
                .option(ChannelOption.SO_KEEPALIVE, true)// 启用TCP保活机制,在长时间没有数据传输时,操作系统会发送保活探测包以确保连接有效
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 添加一个 ChannelInitializer 来初始化每一个新的Channel
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new HttpServerCodec()) // HTTP 编解码器
                                .addLast(new ChunkedWriteHandler()) // 以块方式写的处理器
                                .addLast(new HttpObjectAggregator(8192)) // 聚合 HTTP 消息
                                .addLast(new WebSocketServerProtocolHandler("/websocket")) // 处理 WebSocket 握手
                                .addLast(var1);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 设置保持活动连接状态
        try {
            // 绑定端口并启动接收进来的连接
            log.info("启动 WEB SOCKET 监听端口: {}", port);
            serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChannelFuture createTcpClient(ChannelHandler var1, String host, int port) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(10240, 2, 2, 0, 0))
                                .addLast("ping", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                                .addLast(var1);
                    }
                });
        log.info("启动 TCP 客户端, 连接 -> {}:{}", host, port);
        return bootstrap.connect(host, port).syncUninterruptibly();
    }

    /**
     * .option(ChannelOption.SO_SNDBUF, 8 * 1024 * 1024)
     * 释义: 设置操作系统发送缓冲区,它决定了操作系统在发送数据前可以缓冲的数据量
     * 默认: 65535 字节
     * <p>
     * .option(ChannelOption.SO_RCVBUF, 8 * 1024 * 1024)
     * 释义: 设置操作系统接收缓冲区,它影响操作系统能够缓冲的传入数据量
     * 默认: 65535 字节
     * <p>
     * .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(4096))
     * 释义: 设置 Netty 固定接收缓冲区大小
     * 默认: 2048 字节
     * <p>
     * 注:
     * - SO_SNDBUF 和 SO_RCVBUF 控制的是操作系统级别的发送和接收缓冲区大小，并不直接限制单个 UDP 数据包的大小
     * - Netty 中的 DatagramPacket 默认有一个较小的缓冲区大小（如 2048 字节），这是由 ByteBufAllocator 配置决定的
     * - 如果你需要处理更大的 UDP 数据包,应该通过配置 RecvByteBufAllocator 来调整 Netty 的接收缓冲区大小,而不是仅仅依赖于 SO_RCVBUF 的设置
     */
    public static Channel createUdpServer(ChannelHandler handler, int port) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 8 * 1024 * 1024)
                .option(ChannelOption.SO_RCVBUF, 8 * 1024 * 1024)
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 使用池化缓冲区分配器
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(36 * 1024)) // 设置固定接收缓冲区大小
                .handler(handler);
        try {
            log.info("启动 UDP 监听端口: {}", port);
            Channel channel = bootstrap.bind(port).sync().channel();
            log.info("Send Buffer Size: {}", channel.config().getOption(ChannelOption.SO_SNDBUF));
            log.info("Receive Buffer Size: {}", channel.config().getOption(ChannelOption.SO_RCVBUF));
            return channel;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
