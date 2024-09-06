package com.mdsd.cloud.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.event.SocketEvent;
import com.mdsd.cloud.response.BusinessException;
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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-07-10]
 */
@Slf4j
@Component
public class WsServer {

    @Value("${env.port.sts.web_socket_server}")
    private int port;

    // 未连接队列(syn队列): 保存已经接收到SYN包但还未完成三次握手的连接请求
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();

    // 已连接队列(accept队列): 保存已完成三次握手,但服务器应用程序还未调用accept函数来处理的连接请求
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Getter
    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private final HashMap<String, String> map = new HashMap<>();

    private final ObjectMapper obm = new ObjectMapper();

    private final ApplicationEventPublisher publisher;

    public WsServer(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    @Async
    public void sendMessage(String key, Map<String, Object> resp) {

        Channel channel = channelMap.get(key);
        if (null != channel && channel.isActive()) {
            try {
                String sendDate = obm.writeValueAsString(resp);
                channel.writeAndFlush(new TextWebSocketFrame(sendDate));
            } catch (JsonProcessingException e) {
                throw new BusinessException("数据转Json失败!");
            }
        }
    }

    private void publishEvent(Map<String, String> map) {

        publisher.publishEvent(new SocketEvent(this, map));
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
                                .addLast(

                                        new ChannelInboundHandlerAdapter() {

                                             @Override
                                             public void channelRead(ChannelHandlerContext ctx, Object msg) {

                                                 map.clear();
                                                 if (msg instanceof TextWebSocketFrame textMsg) {
                                                     String text = textMsg.text();
                                                     log.info("<<< {}", text);
                                                     if (StringUtils.isEmpty(text)) {
                                                         return;
                                                     }
                                                     Map<String, String> msgMap;
                                                     try {
                                                         msgMap = obm.readValue(text, new TypeReference<>() {

                                                         });
                                                         if ("PING_MESSAGE".equals(msgMap.get("action"))) {
                                                             // 心跳数据直接回复
                                                             map.put("action", "PONG_MESSAGE");
                                                             ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(map)));
                                                             log.info(">>> {}", map);
                                                             return;
                                                         }
                                                     } catch (JsonProcessingException e) {
                                                         map.put("action", "ERROR_MESSAGE");
                                                         map.put("error", "数据解析失败!");
                                                         try {
                                                             ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(map)));
                                                         } catch (JsonProcessingException ex) {
                                                             throw new RuntimeException(ex);
                                                         }
                                                         throw new BusinessException("数据解析失败");
                                                     }
                                                     // 保存连接信息并发送事件
                                                     Assert.notNull(msgMap.get("云盒编号"), "云盒号为: NULL");
                                                     if (channelMap.containsKey(msgMap.get("云盒编号"))) {
                                                         if (null != msgMap.get("指令编号")) {
                                                             publishEvent(msgMap);// 发出事件
                                                         } else {
                                                             // 替换为当前连接
                                                             log.info("云盒替换当前连接: {}", msgMap.get("云盒编号"));
                                                             channelMap.put(msgMap.get("云盒编号"), ctx.channel());
                                                         }
                                                     } else {
                                                         // 注册云盒编号
                                                         log.info("注册云盒: {}", msgMap.get("云盒编号"));
                                                         channelMap.put(msgMap.get("云盒编号"), ctx.channel());
                                                     }
                                                 } else {
                                                     map.put("action", "ERROR_MESSAGE");
                                                     map.put("error", "未知数据类型!");
                                                     try {
                                                         ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(map)));
                                                     } catch (JsonProcessingException e) {
                                                         throw new RuntimeException(e);
                                                     }
                                                     throw new BusinessException("未知数据类型!");
                                                 }
                                             }

                                             @Override
                                             public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

                                                 synchronized (channelMap) {
                                                     Iterator<Map.Entry<String, Channel>> iterator = channelMap.entrySet().iterator();
                                                     while (iterator.hasNext()) {
                                                         Map.Entry<String, Channel> next = iterator.next();
                                                         if (!next.getValue().isActive()) {
                                                             log.info("移除: {}", next.getKey());
                                                             iterator.remove();
                                                         }
                                                     }
                                                 }

                                             }

                                             @Override
                                             public void channelActive(ChannelHandlerContext ctx) {

                                                 map.clear();
                                                 ctx.executor().schedule(() -> {
                                                     map.put("action", "READY_MESSAGE");
                                                     try {
                                                         ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(map)));
                                                     } catch (JsonProcessingException e) {
                                                         throw new RuntimeException(e);
                                                     }
                                                 }, 1, TimeUnit.SECONDS);
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
