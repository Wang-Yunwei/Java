package com.mdsd.cloud.utils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
public class WsServer {

    public static void createWebSocketServer(ChannelHandler var1, int inetPort) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        // 创建服务端启动引导器, 配置线程模型  EventLoop 等于 Thread
        ServerBootstrap serverBootstrap = new ServerBootstrap().group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 来作为服务器的通道实现
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
                .option(ChannelOption.SO_BACKLOG, 127) // 设置TCP连接数该连接数受 Linux 的 /proc/sys/net/core/somaxconn 影响
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 设置保持活动连接状态
        try {
            // 绑定端口并启动接收进来的连接
            log.info(">>> 启动 WEBSOCKET_SERVER 监听端口: {}", inetPort);
            serverBootstrap.bind(inetPort).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
