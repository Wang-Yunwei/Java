package com.mdsd.cloud.controller.dji.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @author WangYunwei [2024-11-01]
 */
@Slf4j
@Service
public class DjiServiceImpl implements IDjiService {

    @Value("${env.port.sts.udp}")
    private int port;

    private final ObjectMapper obm = new ObjectMapper();
    JsonFormat.Printer printer = JsonFormat.printer();

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

            // 接收的数据
            ByteBuf content = pak.content();
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            // 打印 Payload
            MdsdProtoBuf.Payload payload = MdsdProtoBuf.Payload.parseFrom(bytes);
            System.out.println(printer.print(payload));

            JsonNode jsonNode = obm.readTree(payload.getBody());
            System.out.println(jsonNode.toString());

            // 发送数据
            MdsdProtoBuf.Payload payload1 = MdsdProtoBuf.Payload.newBuilder().setMachineId("683fdd3936614637bb588712e652677f")
                    .setCommand(MdsdProtoBuf.CommandEnum.FC_SUBSCRIPTION)
                    .setBody(jsonNode.toString()).build();

            System.out.println("payload1_size: "+payload1.getSerializedSize());

            DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(payload1.toByteArray()), new InetSocketAddress(senderIp, senderPort));
            ctx.writeAndFlush(datagramPacket);
        }
    }

    @Override
    public void startUdp() {

        Channel udpSocket = UdpSocket.createUdpSocket(new DjiChannelInboundHandler(), port);
        if (udpSocket != null && udpSocket.isActive()) {
            log.info(">>> UDP Socket is active");
        }
    }
}
