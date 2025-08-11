package com.mdsd.cloud.controller.dji.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import com.mdsd.cloud.controller.dji.dto.AircraftDto;
import com.mdsd.cloud.controller.dji.service.IDjiService;
import com.mdsd.cloud.controller.websocket.service.IWebSocketService;
import com.mdsd.cloud.controller.websocket.service.impl.WebSocketServiceImpl;
import com.mdsd.cloud.enums.CommonEnum;
import com.mdsd.cloud.enums.DjiEnum;
import com.mdsd.cloud.event.CommonEvent;
import com.mdsd.cloud.util.ByteUtil;
import com.mdsd.cloud.util.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
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

    private final static String PAYLOAD = "{\"serialNumber\":%s,\"module\":%d,\"directive\":%d,\"body\":%s}";

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
            int i = content.readableBytes();
            ByteBuf buffer = Unpooled.buffer(i);
            content.readBytes(buffer);

            String str = buffer.toString(CharsetUtil.UTF_8);
            try {
                JsonNode jsonNode = obm.readTree(str);
                log.info("size: {}, body: {}", i, jsonNode);

                // 当接收到心跳后解析地址和端口号
                String serialNumber = jsonNode.get("serialNumber").asText();
                int module = jsonNode.get("module").asInt();

                if (module == DjiEnum.心跳.getModule()) {
                    if (aircraftMap.containsKey(serialNumber)) {
                        udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(buffer.array()), aircraftMap.get(serialNumber).getInetSocketAddress()));
                    } else {
                        log.info("{} 注册到系统!", serialNumber);
                        AircraftDto aircraftDto = new AircraftDto();
                        aircraftDto.setInetSocketAddress(pak.sender());
//                        aircraftDto.setProcess(FFmpegUtil.startProcess(String.format(STREAM_PATH, payload.getSerialNumber())));
                        aircraftMap.put(serialNumber, aircraftDto);

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
                    publisher.publishEvent(new CommonEvent(CommonEnum.UDP_SOCKET_DJI, jsonNode));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
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
    public void handleUdpSocket(JsonNode jsonNode) {
        String serialNumber = jsonNode.get("serialNumber").asText();
        AircraftDto aircraftDto = aircraftMap.get(jsonNode.get("serialNumber").asText());
        DjiEnum anEnum = DjiEnum.getEnum(jsonNode.get("module").asInt(), 0x00);
        switch (anEnum) {
//            case 消息订阅 -> {
//                if (payload.getPackageNum() == 1) {
//                    log.info("M2_FC_SUBSCRIPTION ===> {}", payload.getBody().toStringUtf8());
//                } else {
//                    stringBuilder.append(payload.getBody().toStringUtf8());
//                    if (payload.getPackageNum() == payload.getPackageIndex()) {
//                        log.info("M2_FC_SUBSCRIPTION ===> {}", stringBuilder);
//                        stringBuilder.setLength(0);
//                    }
//                }
//            }
            case 电源管理 -> {
                // 飞行器下电,关闭视频流管道
                log.info("===> {}", jsonNode.get("message").asText());
//                try {
//                    aircraftDto.getProcess().getOutputStream().close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                aircraftDto.getProcess().destroy();
                // 删除注册
                aircraftMap.remove(serialNumber);
                log.info("aircraftMap remove {}", aircraftMap);
            }
            case 相机码流 -> {
                // 如果是 Windows 环境则将视频流写入本地
                try (FileOutputStream fileOutputStream = new FileOutputStream("output.h264", true)) {

//                    fileOutputStream.write(payload.getBody().toByteArray(), 0, payload.getBody().size());

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
        AircraftDto aircraftDto = aircraftMap.get(jsonNode.get("serialNumber").asText());
        if (aircraftMap.size() > 0 || null != aircraftDto) {

            String serialNumber = jsonNode.get("serialNumber").asText();
            int module = Integer.decode(jsonNode.get("module").asText());
            int directive = Integer.decode(jsonNode.get("directive").asText());
            if (module == 0 && directive == 0) {
                webSocketService.sendMessage(serialNumber, String.format(WebSocketServiceImpl.errorMessage, "模块或指令不能为空!"));
            } else {
                if (udpChannel != null && udpChannel.isActive()) {
                    DjiEnum anEnum = DjiEnum.getEnum(module, directive);
                    log.info(anEnum.name());
                    // 设置序列号、指令编号、动作编号
                    String body = null;
                    switch (anEnum) {
                        case 云台管理_设置工作模式 -> {
                            mountPosition = jsonNode.get("mountPosition") != null ? jsonNode.get("mountPosition").asInt() : 1;
                            int mode = jsonNode.get("mode") != null ? jsonNode.get("mode").asInt() : 0;
                            body = String.format(anEnum.getArguments(), mountPosition, mode);
                        }
                        case 云台管理_重置角度 -> {
                            int resetMode = jsonNode.get("resetMode") != null ? jsonNode.get("resetMode").asInt() : 0;
                            body = String.format(anEnum.getArguments(), mountPosition, resetMode);
                        }
                        case 云台管理_旋转角度 -> {
                            int rotationMode = jsonNode.get("rotationMode") != null ? jsonNode.get("rotationMode").asInt() : 0;
                            int pitch = jsonNode.get("pitch") != null ? jsonNode.get("pitch").asInt() : 0;
                            int roll = jsonNode.get("roll") != null ? jsonNode.get("roll").asInt() : 0;
                            int yaw = jsonNode.get("yaw") != null ? jsonNode.get("yaw").asInt() : 0;
                            double time = jsonNode.get("time") != null ? jsonNode.get("time").asDouble() : 0;
                            body = String.format(anEnum.getArguments(), mountPosition, rotationMode, pitch, roll, yaw, time);

                        }
                        case 飞行控制_执行摇杆动作 -> {
                            int north = jsonNode.get("north") != null ? jsonNode.get("north").asInt() : 0;
                            int east = jsonNode.get("east") != null ? jsonNode.get("east").asInt() : 0;
                            int down = jsonNode.get("down") != null ? jsonNode.get("down").asInt() : 0;
                            int yaw = jsonNode.get("yaw") != null ? jsonNode.get("yaw").asInt() : 0;
                            int down_speed = jsonNode.get("down_speed") != null ? jsonNode.get("down_speed").asInt() : 0;
                            body = String.format(anEnum.getArguments(), north, east, down, yaw, down_speed);
                        }
                        case 航点_上传任务 -> {
                            if (jsonNode.get("waypoint") != null) {
                                JsonNode waypoint = jsonNode.withArray("waypoint");
                                body = String.format(anEnum.getArguments(), waypoint.size(), waypoint.toString());
                            }
                        }
                        default -> log.error("未知指令!");
                    }
                    String payload = String.format(PAYLOAD, serialNumber, module, directive, StringUtils.isEmpty(body) ? null : body);
                    log.info("发送数据: {}",payload);
                    // 发送数据
//                    udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(ByteUtil.stringToByte(payload)), aircraftDto.getInetSocketAddress()));
                }
            }
        }
    }
}
