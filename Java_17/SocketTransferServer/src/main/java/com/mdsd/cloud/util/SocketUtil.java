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
                .option(ChannelOption.SO_KEEPALIVE,true)// 启用TCP保活机制,在长时间没有数据传输时,操作系统会发送保活探测包以确保连接有效
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

    public static Channel createUdpServer(ChannelHandler handler, int port) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 8 * 1024 * 1024) // 设置发送缓冲区
                .option(ChannelOption.SO_RCVBUF, 16 * 1024 * 1024) // 设置接收缓冲区
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 使用池化缓冲区分配器
                .handler(handler);
        try {
            log.info("启动 UDP 监听端口: {}", port);
            return bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
