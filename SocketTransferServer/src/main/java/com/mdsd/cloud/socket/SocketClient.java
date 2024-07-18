package com.mdsd.cloud.socket;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.transfer.dto.BaseInp;
import com.mdsd.cloud.controller.transfer.dto.HeartbeatInp;
import com.mdsd.cloud.controller.transfer.dto.RegisterInp;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.ResponseDto;
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class SocketClient {

    @Setter
    @Value("${env.ip.socket_client}")
    private String host;

    @Setter
    @Value("${env.port.socket_client}")
    private int port;

    private final EventLoopGroup group = new NioEventLoopGroup();

    private final Bootstrap bootstrap = new Bootstrap();

    private Channel channel;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public SocketClient(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    public void sendMessage(ByteBuf buf) {

        channel.writeAndFlush(buf);
    }

    public void sendMessage(byte[] data) {

        channel.writeAndFlush(Unpooled.wrappedBuffer(data));
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
                                                 SocketEvent<ByteBuf> event = new SocketEvent<>(this, msg);
                                                 publisher.publishEvent(event);
                                             }

                                             @Override
                                             public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                                                 // 发送心跳
                                                 if (evt instanceof IdleStateEvent) {
                                                     ByteBuf buf = Unpooled.buffer();
                                                     buf.writeShort(0x7479);
                                                     buf.writeShort(0x09);
                                                     buf.writeByte(0x02);
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

        ChannelFuture channelFuture = bootstrap.connect(host, port).syncUninterruptibly();
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if (future.isSuccess()) {
                    // 连接成功后,发送注册请求
                    log.info("连接成功,并发送注册请求!");
                    channel = future.channel();
                    ByteBuf buf = Unpooled.buffer();
                    byte[] bytes = AuthSingleton.getInstance().getAccessToken().getBytes();
                    buf.writeShort(0x7479);
                    buf.writeShort(bytes.length + 5);
                    buf.writeByte(InstructEnum.注册.getInstruct());
                    buf.writeInt(AuthSingleton.getInstance().getCompanyId());
                    buf.writeBytes(bytes);
                    channel.writeAndFlush(buf);
                } else {
                    // 重新连接
                    int connectCount = 0;
                    while (connectCount < 3) {
                        log.info("正在尝试连接 >>> {}");
                        Thread.sleep(1000 * 3);
                        connectCount++;
                        connect();
                    }
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {

        group.shutdownGracefully();
        if (null != channel) {

            channel.close();
        }
    }
}
