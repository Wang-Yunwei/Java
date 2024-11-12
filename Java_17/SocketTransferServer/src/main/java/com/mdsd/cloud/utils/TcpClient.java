package com.mdsd.cloud.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
public class TcpClient {

    public static ChannelFuture createTcpClient(ChannelHandler var1, String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(10240, 2, 2, 0, 0))
                                .addLast("ping", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                                .addLast(var1);
                    }
                });
        log.info(">>> 启动 TCP 客户端, 连接 -> {}:{}", host, port);
        return bootstrap.connect(host, port).syncUninterruptibly();
    }
}
