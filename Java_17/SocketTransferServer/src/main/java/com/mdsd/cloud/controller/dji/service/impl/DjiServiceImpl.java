package com.mdsd.cloud.controller.dji.service.impl;

import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.dji.dto.MdsdProtoBuf;
import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.utils.UdpSocket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @author WangYunwei [2024-11-01]
 */
@Slf4j
@Service
public class DjiServiceImpl implements IDjiService {

    JsonFormat.Printer printer = JsonFormat.printer();

    MdsdProtoBuf.SubscriptionTopic subscriptionTopic = MdsdProtoBuf.SubscriptionTopic.newBuilder()
            .setTopic(MdsdProtoBuf.FcSubscriptionTopicEnum.QUATERNION)
            .setFrequency(MdsdProtoBuf.SubscriptionFreqEnum.HZ_1)
            .setPushFrequency(100).build();
    MdsdProtoBuf.Payload payload = MdsdProtoBuf.Payload.newBuilder().setHardwareCode("683fdd3936614637bb588712e652677f")
            .setCommand(MdsdProtoBuf.CommandEnum.FC_SUBSCRIPTION)
            .setAction(0x123)
            .setBody(subscriptionTopic.toByteString()).build();

    class DjiChannelInboundHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket pak) throws Exception {
//                        pak.content().readableBytes();
//                        ArrayBlockingQueue<DatagramPacket> objects = new ArrayBlockingQueue<>(1024);
//                        objects.offer(pak);
            // 获取发送方的InetSocketAddress
            InetSocketAddress senderAddress = pak.sender();
            // 如果需要的话，可以进一步解析地址和端口号
            String senderIp = senderAddress.getAddress().getHostAddress(); // 发送方IP地址
            int senderPort = senderAddress.getPort(); // 发送方端口
            // 打印信息
            System.out.println("Received packet from: " + senderIp + ":" + senderPort);

            ByteBuf content = pak.content();
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            MdsdProtoBuf.Payload payload = MdsdProtoBuf.Payload.parseFrom(bytes);
            System.out.println(printer.print(payload));
            MdsdProtoBuf.SubscriptionTopic subscriptionTopic = MdsdProtoBuf.SubscriptionTopic.parseFrom(payload.getBody());
            System.out.println(printer.print(subscriptionTopic));
            DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(payload.toByteArray()), new InetSocketAddress(senderIp, senderPort));
            ctx.writeAndFlush(datagramPacket);
        }
    }

    @Override
    public void startUdp() {

        Channel udpSocket = UdpSocket.createUdpSocket(new DjiChannelInboundHandler(), 20242);
        if (udpSocket != null && udpSocket.isActive()) {

        }
    }
}
