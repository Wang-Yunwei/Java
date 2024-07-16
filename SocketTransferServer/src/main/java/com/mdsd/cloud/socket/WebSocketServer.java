package com.mdsd.cloud.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.event.SocketEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class WebSocketServer {

    private final ApplicationEventPublisher publisher;

    // 未连接队列(syn队列): 保存已经接收到SYN包但还未完成三次握手的连接请求
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();

    // 已连接队列(accept队列): 保存已完成三次握手,但服务器应用程序还未调用accept函数来处理的连接请求
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${env.port.web-socket-server}")
    private int port;

    public WebSocketServer(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    public void sendMessage(String key, ResponseDto<Object> resp) {

        Channel channel = channelMap.get(key);
        if (null != channel && channel.isActive()) {
            try {
                String result = objectMapper.writeValueAsString(resp);
                channel.writeAndFlush(new TextWebSocketFrame(result));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("数据转Json失败!");
            }
        }
    }

    public void publishEvent(Object obj) {

        SocketEvent<Object> event = new SocketEvent<>(this, obj);
        publisher.publishEvent(event);
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
                    protected void initChannel(NioSocketChannel ch)  {

                        ch.pipeline().addLast(new HttpServerCodec()) // HTTP 编解码器
                                .addLast(new ChunkedWriteHandler()) // 以块方式写的处理器
                                .addLast(new HttpObjectAggregator(8192)) // 聚合 HTTP 消息
                                .addLast(new WebSocketServerProtocolHandler("/websocket")) // 处理 WebSocket 握手
                                .addLast(new ChannelInboundHandlerAdapter() {

                                             @Override
                                             public void channelRegistered(ChannelHandlerContext ctx)  {

                                                 InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                                                 String hostAddress = remoteAddress.getAddress().getHostAddress();
                                                 Channel channel = channelMap.get(hostAddress);
                                                 if (null == channel) {
                                                     channelMap.put(hostAddress, ctx.channel());
                                                 }
                                             }

                                             @Override
                                             public void channelRead(ChannelHandlerContext ctx, Object msg) {

                                                 publishEvent(msg);
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
