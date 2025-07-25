package com.mdsd.cloud.controller.dji.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.dji.dto.AircraftDto;
import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;
import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.websocket.service.IWebSocketService;
import com.mdsd.cloud.controller.websocket.service.impl.WebSocketServiceImpl;
import com.mdsd.cloud.enums.CommonEnum;
import com.mdsd.cloud.enums.DjiEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.util.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author WangYunwei [2024-11-01]
 */
@Slf4j
@Service
public class DjiServiceImpl implements IDjiService {

    private final static String STREAM_PATH = "rtmp://192.168.0.221/live/%s";

    @Value("${env.port.sts.udp}")
    private int port;

    private int mountPosition;

    private final ConcurrentHashMap<String, AircraftDto> aircraftMap = new ConcurrentHashMap<>();
    private final ObjectMapper obm = new ObjectMapper();
    private final JsonFormat.Printer printer = JsonFormat.printer();
    private final StringBuilder stringBuilder = new StringBuilder();

    private final BlockingQueue<byte[]> videoQueue = new LinkedBlockingQueue<>();

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
            log.info("接收的数据包大小为: {}", content.readableBytes());
            ByteBuf buffer = Unpooled.buffer(content.readableBytes());
            content.readBytes(buffer);
            // 解析数据包
            DjiProtoBuf.Payload payload;
            try {
                payload = DjiProtoBuf.Payload.parseFrom(buffer.nioBuffer());
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
                        // 发送数据
                        udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(buffer.array()), aircraftMap.get(payload.getSerialNumber()).getInetSocketAddress()));
                    } else {
                        log.info("{} 注册到系统!", el.getSerialNumber());
                        AircraftDto aircraftDto = new AircraftDto();
                        aircraftDto.setInetSocketAddress(pak.sender());
//                        aircraftDto.setProcess(FFmpegUtil.startProcess(String.format(STREAM_PATH, payload.getSerialNumber())));
                        aircraftMap.put(el.getSerialNumber(), aircraftDto);

                        // 消费线程
//                        new Thread(() -> {
//                            try {
//                                while (!Thread.interrupted()) {
//                                    byte[] packet = videoQueue.take(); // 取出一个完整的UDP包（约33656字节）
//                                    aircraftDto.getProcess().getOutputStream().write(packet);
//                                    aircraftDto.getProcess().getOutputStream().flush();
//                                }
//                            } catch (IOException | InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }).start();
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
                if (payload.getPackageNum() == 1) {
                    log.info("M2_FC_SUBSCRIPTION ===> {}", payload.getBody().toStringUtf8());
                } else {
                    stringBuilder.append(payload.getBody().toStringUtf8());
                    if (payload.getPackageNum() == payload.getPackageIndex()) {
                        log.info("M2_FC_SUBSCRIPTION ===> {}", stringBuilder);
                        stringBuilder.setLength(0);
                    }
                }
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
                log.info("===> {}", payload.getMessage());
//                try {
//                    aircraftDto.getProcess().getOutputStream().close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                aircraftDto.getProcess().destroy();
                // 删除注册
                aircraftMap.remove(payload.getSerialNumber());
            }
            case M6_FLIGHT_CONTROLLER -> {
                log.info("M6_FLIGHT_CONTROLLER ===> {}", payload.getBody().toStringUtf8());
//                switch (payload.getDirective()){
//                    case M6_F3_GET_TRK_POSITION_ENABLE -> {
//                        log.info("M6_FLIGHT_CONTROLLER ===> {}", payload.getBody().toStringUtf8());
//                    }
//                    default -> log.info("M6");
//                }
            }
            case M8_HMS -> {
                log.info("M8_HMS ===> {}", payload.getBody().toStringUtf8());
            }
            case M14_LIVE_VIEW -> {
                // 如果是 Windows 环境则将视频流写入本地
                try (FileOutputStream fileOutputStream = new FileOutputStream("output.h264", true)) {
                    fileOutputStream.write(payload.getBody().toByteArray(), 0, payload.getBody().size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                if ("Linux".equals(System.getProperties().getProperty("os.name"))) {
//                    // 将接收到的数据直接写入FFmpeg标准输入
//                    try {
//                        aircraftDto.getProcess().getOutputStream().write(payload.getBody().toByteArray());
//                        aircraftDto.getProcess().getOutputStream().flush();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    // 将数据流写入队列
//                    byte[] byteArray = payload.getBody().toByteArray();
//                    if(!videoQueue.offer(byteArray)){
//                        log.error("Queue is full, dropping frame.");
//                    }
//                } else {
//                    // 如果是 Windows 环境则将视频流写入本地
//                    log.info("已接收 {} 字节", payload.getBody().size());
//                    try (FileOutputStream fileOutputStream = new FileOutputStream("output.h264", true)) {
//                        fileOutputStream.write(payload.getBody().toByteArray(), 0, payload.getBody().size());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
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
        int module = Integer.decode(jsonNode.get("模块").asText());
        int directive = Integer.decode(jsonNode.get("指令").asText());
        if (module == 0 && directive == 0) {
            webSocketService.sendMessage(serialNumber, String.format(WebSocketServiceImpl.errorMessage, "模块或指令不能为空!"));
        } else {
            if (udpChannel != null && udpChannel.isActive()) {
                DjiEnum anEnum = DjiEnum.getEnum(module, directive);
                log.info(anEnum.name());
                // 设置序列号、指令编号、动作编号
                DjiProtoBuf.Payload.Builder payload = DjiProtoBuf.Payload.newBuilder().setSerialNumber(serialNumber).setModule(DjiProtoBuf.ModuleEnum.forNumber(anEnum.getModule())).setDirective(DjiProtoBuf.DirectiveEnum.forNumber(anEnum.getDirective()));
                switch (anEnum) {
                    case 云台管理_设置工作模式 -> {
                        mountPosition = jsonNode.get("mountPosition") != null ? jsonNode.get("mountPosition").asInt() : 1;
                        int mode = jsonNode.get("mode") != null ? jsonNode.get("mode").asInt() : 0;
                        payload.setBody(ByteString.copyFrom(String.format(anEnum.getArguments(), mountPosition, mode), Charset.defaultCharset()));
                    }
                    case 云台管理_重置角度 -> {
                        int resetMode = jsonNode.get("resetMode") != null ? jsonNode.get("resetMode").asInt() : 0;
                        payload.setBody(ByteString.copyFrom(String.format(anEnum.getArguments(), mountPosition, resetMode), Charset.defaultCharset()));
                    }
                    case 云台管理_旋转角度 -> {
                        int rotationMode = jsonNode.get("rotationMode") != null ? jsonNode.get("rotationMode").asInt() : 0;
                        int pitch = jsonNode.get("pitch") != null ? jsonNode.get("pitch").asInt() : 0;
                        int roll = jsonNode.get("roll") != null ? jsonNode.get("roll").asInt() : 0;
                        int yaw = jsonNode.get("yaw") != null ? jsonNode.get("yaw").asInt() : 0;
                        double time = jsonNode.get("time") != null ? jsonNode.get("time").asDouble() : 0;
                        payload.setBody(ByteString.copyFrom(String.format(anEnum.getArguments(), mountPosition, rotationMode, pitch, roll, yaw, time), Charset.defaultCharset()));
                    }
                    case 飞行控制_执行摇杆动作 -> {
                        int north = jsonNode.get("north") != null ? jsonNode.get("north").asInt() : 0;
                        int east = jsonNode.get("east") != null ? jsonNode.get("east").asInt() : 0;
                        int down = jsonNode.get("down") != null ? jsonNode.get("down").asInt() : 0;
                        int yaw = jsonNode.get("yaw") != null ? jsonNode.get("yaw").asInt() : 0;
                        int down_speed = jsonNode.get("down_speed") != null ? jsonNode.get("down_speed").asInt() : 0;
                        payload.setBody(ByteString.copyFrom(String.format(anEnum.getArguments(), north, east, down, yaw, down_speed), Charset.defaultCharset()));
                    }
                    case 航点_上传任务 -> {
                        if (jsonNode.get("waypoint") != null) {
                            JsonNode waypoint = jsonNode.withArray("waypoint");
                            payload.setBody(ByteString.copyFrom(String.format(anEnum.getArguments(), waypoint.size(), waypoint.toString()), Charset.defaultCharset()));
                        }
                    }
                    default -> {

                        log.error("未知指令!");
                    }
                }
                // TODO 打印 Payload
//                try {
//                    System.out.println("发送数据前打印Payload: " + printer.print(payload));
//                } catch (InvalidProtocolBufferException e) {
//                    throw new RuntimeException(e);
//                }
                // 发送数据
                AircraftDto aircraftDto = aircraftMap.get(payload.getSerialNumber());
                if (aircraftMap.size() > 0 || null != aircraftDto) {
                    udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(payload.build().toByteArray()), aircraftDto.getInetSocketAddress()));
                }
            }
        }
    }
}
