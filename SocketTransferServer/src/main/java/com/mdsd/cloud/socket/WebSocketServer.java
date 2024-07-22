package com.mdsd.cloud.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class WebSocketServer<T> {

    // 未连接队列(syn队列): 保存已经接收到SYN包但还未完成三次握手的连接请求
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();

    // 已连接队列(accept队列): 保存已完成三次握手,但服务器应用程序还未调用accept函数来处理的连接请求
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Getter
    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private final ObjectMapper om = new ObjectMapper();

    @Value("${env.port.web-socket-server}")
    private int port;

    private final ApplicationEventPublisher publisher;

    public WebSocketServer(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    public void sendMessage(String key, ResponseDto<T> resp) {

        Channel channel = channelMap.get(key);
        if (null != channel && channel.isActive()) {
            try {
                String result = om.writeValueAsString(resp);
                channel.writeAndFlush(new TextWebSocketFrame(result));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("数据转Json失败!");
            }
        }
    }

    private void publishEvent(Map<String, String> msgMap) {

        publisher.publishEvent(new SocketEvent<>(this, msgMap));
    }

    @PostConstruct
    public void startWebSocketServer() {

        // 创建服务端启动引导器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 配置线程模型  EventLoop 等于 Thread
        serverBootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 来作为服务器的通道实现
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 添加一个 ChannelInitializer 来初始化每一个新的Channel

                    @Override
                    protected void initChannel(NioSocketChannel ch) {

                        ch.pipeline().addLast(new HttpServerCodec()) // HTTP 编解码器
                                .addLast(new ChunkedWriteHandler()) // 以块方式写的处理器
                                .addLast(new HttpObjectAggregator(8192)) // 聚合 HTTP 消息
                                .addLast(new WebSocketServerProtocolHandler("/websocket")) // 处理 WebSocket 握手
                                .addLast(new ChannelInboundHandlerAdapter() {

                                             @Override
                                             public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                 // 连接已经建立，现在回复一个连接成功的信息
                                                 String response = "连接成功！";
                                                 ByteBuf buf = Unpooled.copiedBuffer(response.getBytes());

                                                 // 发送消息给客户端
                                                 ctx.writeAndFlush(buf);
                                                 super.channelActive(ctx);
                                             }

                                             @Override
                                             public void channelRead(ChannelHandlerContext ctx, Object msg) {

                                                 if (msg instanceof TextWebSocketFrame) {
                                                     TextWebSocketFrame textMsg = (TextWebSocketFrame) msg;
                                                     String text = textMsg.text();
                                                     Map<String, String> msgMap;
                                                     try {
                                                         msgMap = om.readValue(text, Map.class);
                                                         log.info("WebSocketServer 接收到 >>> {}", msgMap.toString());
                                                         String action = msgMap.get("action");
                                                         if (StringUtils.isNoneBlank(action)) {
                                                             // 心跳数据直接回复
                                                             ctx.writeAndFlush(new TextWebSocketFrame("心跳回复!"));
                                                             return;
                                                         }
                                                     } catch (JsonProcessingException e) {
                                                         throw new RuntimeException("数据解析失败");
                                                     }
                                                     // 保存连接信息
                                                     if (null != msgMap) {
                                                         Channel channel = channelMap.get(msgMap.get("云盒编号"));
                                                         if (null == channel) {
                                                             log.info("当前注册连接云盒号 >>> {}", msgMap.get("云盒编号"));
                                                             channelMap.put(msgMap.get("云盒编号"), ctx.channel());
                                                         }
                                                         publishEvent(msgMap);
                                                     }
                                                 } else if (msg instanceof BinaryWebSocketFrame) {
                                                     BinaryWebSocketFrame binaryMsg = (BinaryWebSocketFrame) msg;
                                                     ByteBuf cnt = binaryMsg.content();
                                                     byte[] array = cnt.array();
                                                     log.error("二进制数据 >>> {}", array.toString());
                                                     cnt.release();
                                                 } else {
                                                     throw new RuntimeException("未知数据类型!");
                                                 }
                                             }
                                         }
                                );
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // 设置TCP连接数该连接数受 Linux 的 /proc/sys/net/core/somaxconn 影响
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 设置保持活动连接状态
        try {
            // 绑定端口并启动接收进来的连接
            serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void destroy() {

        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
        if (!channelMap.isEmpty()) {
            channelMap.values().forEach(ChannelOutboundInvoker::close);
        }
    }
}
