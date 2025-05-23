package com.mdsd.cloud.controller.dji.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.dji.dto.AircraftDto;
import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;
import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.websocket.service.IWebSocketService;
import com.mdsd.cloud.controller.websocket.service.impl.WebSocketServiceImpl;
import com.mdsd.cloud.enums.CommonEnum;
import com.mdsd.cloud.enums.TyjwEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.util.FfmpegUtil;
import com.mdsd.cloud.util.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-11-01]
 */
@Slf4j
@Service
public class DjiServiceImpl implements IDjiService {

    private final static String STREAM_PATH = "rtmp://192.168.0.221/live/%s";

    @Value("${env.port.sts.udp}")
    private int port;

    private final ConcurrentHashMap<String, AircraftDto> aircraftMap = new ConcurrentHashMap<>();
    private final ObjectMapper obm = new ObjectMapper();
    private final JsonFormat.Printer printer = JsonFormat.printer();

    private Channel udpChannel;
    private final ApplicationEventPublisher publisher;
    private final IWebSocketService webSocketService;

    public DjiServiceImpl(ApplicationEventPublisher publisher, IWebSocketService webSocketService) {
        this.publisher = publisher;
        this.webSocketService = webSocketService;
    }

    @ChannelHandler.Sharable
    class DjiChannelInboundHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket pak) {
            ByteBuf content = pak.content();
            System.out.println("接收的数据包大小为: " + content.readableBytes());
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);
            DjiProtoBuf.Payload payload;
            try {
                payload = DjiProtoBuf.Payload.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
            Optional.ofNullable(payload).ifPresent(el -> {
                // TODO 打印 Payload
                try {
                    log.info("Payload: {}", printer.print(payload));
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException(e);
                }
                // 当接收到心跳后解析地址和端口号
                if (payload.getModule() == DjiProtoBuf.ModuleEnum.M0_HEARTBEAT) {
                    if (aircraftMap.containsKey(el.getSerialNumber())) {
                        log.info("{} 心跳 {}", el.getSerialNumber(), payload.getMessage());
                    } else {
                        log.info("{} 注册到系统!", el.getSerialNumber());
                        AircraftDto aircraftDto = new AircraftDto();
                        aircraftDto.setInetSocketAddress(pak.sender());
                        aircraftMap.put(el.getSerialNumber(), aircraftDto);
                    }
                } else {
                    publisher.publishEvent(new CommonEvent(CommonEnum.UDP_SOCKET_DJI, payload));
                }
            });
        }
    }

    @Override
    public void startUdpListening() {
        udpChannel = SocketUtil.createUdpServer(new DjiChannelInboundHandler(), port);
    }

    /**
     * 处理 UDP SOCKET
     */
    @Override
    public void handleUdpSocket(DjiProtoBuf.Payload payload) {
        AircraftDto aircraftDto = aircraftMap.get(payload.getSerialNumber());
        switch (payload.getModule()) {
            case M2_FC_SUBSCRIPTION -> {
                log.info(payload.getBody().toStringUtf8());
                // 将str转为JsonNode
//                JsonNode jsonNode;
//                try {
//                    jsonNode = obm.readTree(payload.getBody().toStringUtf8());
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
                // 获取JsonNode中的值
//                Optional.ofNullable(jsonNode.path("dji")).filter(node -> !node.isMissingNode()).map(node -> node.path("aircraftSeries")).filter(node -> !node.isMissingNode()).ifPresent(node -> {
//                    System.out.println("aircraftSeries: " + node.asText());
//                });
            }
            case M5_POWER_MANAGEMENT -> {
                // 飞行器下电,关闭视频流管道
                try {
                    aircraftDto.getProcess().getOutputStream().close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                aircraftDto.getProcess().destroy();
                // 删除注册
                aircraftMap.remove(payload.getSerialNumber());
            }
            case M14_LIVE_VIEW -> {
                if ("Linux".equals(System.getProperties().getProperty("os.name"))) {
                    if (aircraftDto.getProcess() == null || aircraftDto.getProcess().isAlive()) {
                        aircraftDto.setProcess(FfmpegUtil.startProcess(String.format(STREAM_PATH, payload.getSerialNumber())));
                    }
                    // 将接收到的数据直接写入FFmpeg标准输入
                    try {
                        aircraftDto.getProcess().getOutputStream().write(payload.getBody().toByteArray());
                        aircraftDto.getProcess().getOutputStream().flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // 如果是 Windows 环境则将视频流写入本地
                    log.info("已接收 {} 字节", payload.getBody().size());
                    try (FileOutputStream fileOutputStream = new FileOutputStream(payload.getMessage(), true)) {
                        fileOutputStream.write(payload.getBody().toByteArray(), 0, payload.getBody().size());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            default -> log.info("================");
        }
    }

    /**
     * 处理 WEB SOCKET
     */
    @Override
    public void handleWebSocket(JsonNode jsonNode) {
        String serialNumber = jsonNode.get("云盒编号").asText();
        String instruct = jsonNode.get("指令编号").asText();
        String action = jsonNode.get("动作编号").asText();
        if (StringUtils.isEmpty(instruct) && StringUtils.isEmpty(action)) {
            webSocketService.sendMessage(serialNumber, String.format(WebSocketServiceImpl.errorMessage, "指令编号或动作编号不能为空!"));
        } else {
            if (udpChannel != null && udpChannel.isActive()) {
                TyjwEnum anEnum = TyjwEnum.getEnum(Integer.parseInt(instruct, 16), Integer.parseInt(action, 16));
                log.info(anEnum.name());
                // 设置序列号、指令编号、动作编号
                DjiProtoBuf.Payload.Builder payload = DjiProtoBuf.Payload.newBuilder().setSerialNumber(serialNumber).setModule(anEnum.getModelEnum());
                switch (anEnum) {
                    case 航线飞行_航线规划 -> {
                        payload.setActive(DjiProtoBuf.ActiveEnum.M15_F2_UPLOAD_MISSION);
                        try {
                            PlanLineDataDTO planLineDataDto = obm.readValue(jsonNode.get("航线数据").asText(), PlanLineDataDTO.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 航线飞行_开始航线 -> {
                        payload.setActive(DjiProtoBuf.ActiveEnum.M15_F3_START);
                    }
                    case 航线飞行_暂停航线 -> {
                        payload.setActive(DjiProtoBuf.ActiveEnum.M15_F5_PAUSE);
                    }
                    case 航线飞行_恢复航线 -> {
                        payload.setActive(DjiProtoBuf.ActiveEnum.M15_F6_RESUME);
                    }
                    case 航线飞行_结束航线 -> {
                        payload.setActive(DjiProtoBuf.ActiveEnum.M15_F4_STOP);
                    }
                    default -> {
                        log.error("未知指令!");
                    }
                }
                // TODO 打印 Payload
                try {
                    System.out.println("发送数据前打印Payload: " + printer.print(payload));
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException(e);
                }
                // 发送数据
                DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(payload.build().toByteArray()), aircraftMap.get(payload.getSerialNumber()).getInetSocketAddress());
                udpChannel.writeAndFlush(datagramPacket);
            }
        }
    }
}
