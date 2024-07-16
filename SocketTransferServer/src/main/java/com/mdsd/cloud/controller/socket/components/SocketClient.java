package com.mdsd.cloud.controller.socket.components;

import com.mdsd.cloud.controller.socket.dto.HeartbeatInp;
import com.mdsd.cloud.controller.socket.dto.RegisterInp;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class SocketClient {

    private Channel channel;

    private final EventLoopGroup group = new NioEventLoopGroup();

    private final Bootstrap bootstrap = new Bootstrap();

    @Setter
    @Value("${env.ip.socket_client}")
    private String host;

    @Setter
    @Value("${env.port.socket_client}")
    private int port;

    @Setter
    private byte connectCount;

    @Getter
    private final RegisterInp registerInp = new RegisterInp();

    private final HeartbeatInp heartbeatInp = new HeartbeatInp();

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SocketClient(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    public void sendMessage(byte[] data) {

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(Unpooled.wrappedBuffer(data));

        }
    }

    private void publishEvent(ByteBuf message) {

        SocketEvent<ByteBuf> event = new SocketEvent<>(this, message);
        publisher.publishEvent(event);
    }

    @PostConstruct
    private void create() {

        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) {

                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240, 2, 2, 0, 0))
                                .addLast("ping", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                                             @Override
                                             protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

                                                 // 收到信息后发布事件
                                                 publishEvent(msg);
                                             }

                                             @Override
                                             public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                                                 // 发送心跳
                                                 if (evt instanceof IdleStateEvent) {
                                                     ByteBuf buf = Unpooled.buffer();
                                                     buf.writeShort(heartbeatInp.getFrameHeader());
                                                     buf.writeShort(heartbeatInp.getDataLength());
                                                     buf.writeByte(heartbeatInp.getInstructNum());
                                                     buf.writeLong(System.currentTimeMillis());
                                                     channel.writeAndFlush(buf);
                                                     buf.release();
                                                 }
                                             }
                                         }

                                );
                    }
                });
    }

    public void connect() {

        if (12 <= connectCount) {
            log.error("已经达到最大尝试连接次数,若要再次连接请重置连接次数!");
            return;
        }
        log.warn("正在尝试建立连接 >>> {}:{}", host, port);
        ChannelFuture channelFuture = bootstrap.connect(host, port).syncUninterruptibly();

        channelFuture.addListener(future -> {

            if (!future.isSuccess()) {
                log.warn("连接失败,正在尝试第 {} 次重连...", connectCount);
                connectCount++;
                // 等待 5 秒后重连
                group.schedule(this::connect, 5, TimeUnit.SECONDS);
            } else {
                // 连接成功后,发送注册请求
                log.info("连接成功!");
                channel = channelFuture.channel();
                ByteBuf buf = Unpooled.buffer();
                buf.writeShort(registerInp.getFrameHeader());
                buf.writeShort(registerInp.getDataLength());
                buf.writeByte(registerInp.getInstructNum());
                buf.writeInt(registerInp.getUserNum());
                buf.writeBytes(registerInp.getAccessToken());
                channel.writeAndFlush(buf);
                buf.release();
            }
        });
    }

    @PreDestroy
    private void destroy() {

        group.shutdownGracefully();
        if (channel != null) {
            channel.close();
        }
    }
}
