package com.mdsd.cloud.utils;

import com.mdsd.cloud.controller.dji.dto.Mdsd;
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
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author WangYunwei [2024-09-14]
 */
@Slf4j
@Component
public class UdpSocket {

    @Value("${env.port.sts.udp}")
    private int port;

    @Getter
    private Channel channel;

    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

    Mdsd.SubscriptionTopic.Builder builder = Mdsd.SubscriptionTopic.newBuilder()
            .setTopic(Mdsd.SubscriptionTopicActionEnum.DJI_QUATERNION)
            .setFrequency(Mdsd.SubscriptionFreqActionEnum.DJI_400_HZ)
            .setPushFrequency(100);

    @PostConstruct
    public void createUdpSocket() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 4194304) // 4MB 发送缓冲区
                .option(ChannelOption.SO_RCVBUF, 4194304) // 4MB 接收缓冲区
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // // 使用池化缓冲区分配器
                .handler(new SimpleChannelInboundHandler<DatagramPacket>(false) {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket pak) throws Exception {
//                        pak.content().readableBytes();
//                        ArrayBlockingQueue<DatagramPacket> objects = new ArrayBlockingQueue<>(1024);
//                        objects.offer(pak);
                        ByteBuf content = pak.content();
                        byte[] bytes = new byte[content.readableBytes()];
                        content.readBytes(bytes);
                        System.out.println(ByteUtil.bytesToStringUTF8(bytes));
                        String str = "This is a Socket Transfer Server";
                        DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(str.getBytes()), new  InetSocketAddress("172.22.163.211",49152));
                        ctx.writeAndFlush(datagramPacket);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        log.error("UDP Socket Exception: {}", cause);
                        ctx.close(); // 关闭连接
                    }
                });
        try {
            log.info(">>> 启动 UDP Server, 端口: {}", port);
            ChannelFuture sync = bootstrap.bind(port).sync();
            channel = sync.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void destroy() {
        eventExecutors.shutdownGracefully();
    }
}
