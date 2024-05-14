package com.example.controller.netty.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author WangYunwei [2021-12-20]
 */
@Slf4j
@Component
public class NettyServer {

    WebSocketHandler webSocketHandler;

    EventLoopGroup bossGroup;

    EventLoopGroup workGroup;

    public NettyServer(final WebSocketHandler webSocketHandler) {

        this.webSocketHandler = webSocketHandler;
    }

    /**
     * 启动
     */
    private void start() throws InterruptedException {

        this.bossGroup = new NioEventLoopGroup();
        this.workGroup = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();
        // bossGroup辅助客户端的tcp连接请求, workGroup负责与客户端之前的读写操作
        bootstrap.group(this.bossGroup, this.workGroup);
        // 设置NIO类型的channel
        bootstrap.channel(NioServerSocketChannel.class);
        // 设置监听端口
        bootstrap.localAddress(new InetSocketAddress(49151));
        // 连接到达时会创建一个通道
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(final SocketChannel ch) throws Exception {
                // 流水线管理通道中的处理程序（Handler），用来处理业务
                // webSocket协议本身是基于http协议的，所以这边也要使用http编解码器
                ch.pipeline().addLast(new HttpServerCodec());
                ch.pipeline().addLast(new ObjectEncoder());
                // 以块的方式来写的处理器
                ch.pipeline().addLast(new ChunkedWriteHandler());
                /* 说明：
                 * 1、http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
                 * 2、这就是为什么，当浏览器发送大量数据时，就会发送多次http请求
                 */
                ch.pipeline().addLast(new HttpObjectAggregator(8192));
                //针对客户端，若10s内无读事件则触发心跳处理方法HeartBeatHandler#userEventTriggered
                ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                //自定义空闲状态检测(自定义心跳检测handler)
                ch.pipeline().addLast(new HeartBeatHandler());
                /* 说明：
                 *1、对应webSocket，它的数据是以帧（frame）的形式传递
                 *2、浏览器请求时 ws://localhost:49151/webSocket 表示请求的uri
                 *3、核心功能是将http协议升级为ws协议，保持长连接
                 */
                ch.pipeline().addLast(new WebSocketServerProtocolHandler("/webSocket", "WebSocket", true, 65536 * 10));
                // 自定义的handler，处理业务逻辑
                ch.pipeline().addLast(NettyServer.this.webSocketHandler);
            }
        });
        // 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
        final ChannelFuture channelFuture = bootstrap.bind().sync();
        log.info("Server started and listen on:{}", channelFuture.channel().localAddress());
        // 对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();
    }

    /**
     * 释放资源
     */
    @PreDestroy
    public void destroy() throws InterruptedException {

        if (this.bossGroup != null) {
            this.bossGroup.shutdownGracefully().sync();
        } else if (this.workGroup != null) {
            this.workGroup.shutdownGracefully().sync();
        }
    }

    /**
     * 初始化
     */
    @PostConstruct()
    public void init() {
        //需要开启一个新的线程来执行netty server 服务器
        new Thread(() -> {
            try {
                this.start();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
