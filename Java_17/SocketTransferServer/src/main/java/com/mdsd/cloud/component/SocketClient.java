package com.mdsd.cloud.component;

import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.enums.InstructEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.BusinessException;
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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class SocketClient {

    @Setter
    @Value("${env.ip.tyjw_server}")
    private String host;

    @Setter
    @Value("${env.port.tyjw_socket}")
    private int port;

    private final EventLoopGroup group = new NioEventLoopGroup();

    private final Bootstrap bootstrap = new Bootstrap();

    private Channel channel;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SocketClient(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    public boolean isActiveChannel() {
        return channel != null && channel.isActive();
    }

    @Async
    public void sendMessage(ByteBuf byteBuf) {

        if (channel == null || !channel.isActive()) {
            throw new BusinessException("SocketClient 连接不存在!");
        }
        log.info(">>> {}", byteBuf);
        channel.writeAndFlush(byteBuf);
    }

    private void publishEvent(ByteBuf byteBuf) {

        publisher.publishEvent(new SocketEvent(this, byteBuf));
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

                                                 // 判断是否是心跳信息
                                                 if (2 == msg.getByte(4)) {
                                                     log.info("<<< {}", String.format("0x%02X", msg.getByte(4)));
                                                     return;
                                                 }
                                                 // 收到信息后发布事件
                                                 publishEvent(msg);
                                             }

                                             @Override
                                             public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

                                                 // 发送心跳
                                                 if (evt instanceof IdleStateEvent) {
                                                     ByteBuf buf = Unpooled.buffer();
                                                     buf.writeShort(InstructEnum.请求帧头.getInstruct());
                                                     buf.writeShort(0x09);
                                                     buf.writeByte(InstructEnum.心跳.getInstruct());
                                                     buf.writeLong(System.currentTimeMillis());
                                                     ctx.writeAndFlush(buf);
                                                 }
                                             }
                                         }
                                );
                    }
                });
    }

    public void connect() {

        if(!isActiveChannel()){
            ChannelFuture channelFuture = bootstrap.connect(host, port).syncUninterruptibly();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // 连接成功后,发送注册请求
                    log.info("连接 {} 成功,开始发送注册请求!", String.format("%s:%s", host, port));
                    channel = future.channel();
                    ByteBuf buf = Unpooled.buffer();
                    if (StringUtils.isEmpty(AuthSingleton.getInstance().getAccessToken())) {
                        throw new BusinessException("未找到 AccessToken,请确认已经调过 getToken 接口!");
                    }
                    byte[] bytes = AuthSingleton.getInstance().getAccessToken().getBytes();
                    buf.writeShort(InstructEnum.请求帧头.getInstruct());
                    buf.writeShort(bytes.length + 5);
                    buf.writeByte(InstructEnum.注册.getInstruct());
                    buf.writeInt(AuthSingleton.getInstance().getCompanyId());
                    buf.writeBytes(bytes);
                    channel.writeAndFlush(buf);
                } else {
                    // 重新连接
                    int connectCount = 0;
                    while (connectCount < 3) {
                        log.info("...正在第 {} 次尝试重新连接!", connectCount + 1);
                        Thread.sleep(1000 * 3);
                        connectCount++;
                        connect();
                    }
                }
            });
        }
    }

    @PreDestroy
    public void destroy() {

        group.shutdownGracefully();
        if (null != channel) {
            channel.close();
        }
    }
}
