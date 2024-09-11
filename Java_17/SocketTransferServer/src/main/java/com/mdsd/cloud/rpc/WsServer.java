package com.mdsd.cloud.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.event.CommonEvent;
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
import org.springframework.util.CollectionUtils;

import java.util.*;
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
    private final ConcurrentHashMap<String, List<Channel>> channels = new ConcurrentHashMap<>();

    private final HashMap<String, String> returnMap = new HashMap<>();

    private final ObjectMapper obm = new ObjectMapper();

    private final ApplicationEventPublisher publisher;

    public WsServer(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    @Async
    public void sendMessage(String key, Map<String, Object> resp) {

        List<Channel> channelList = channels.get(key);
        if (!CollectionUtils.isEmpty(channelList)) {
            channelList.stream().filter(Channel::isActive).forEach(el -> {
                String sendDate;
                try {
                    sendDate = obm.writeValueAsString(resp);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                if (null != sendDate) {
                    el.writeAndFlush(new TextWebSocketFrame(sendDate));
                }
            });
        }
    }

    private void publishEvent(Map<String, String> map) {

        publisher.publishEvent(new CommonEvent(this, map));
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
                                .addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                                             @Override
                                             protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame tws) throws JsonProcessingException {
                                                 System.out.println("channelRead0" + tws.text());
                                                 String text = tws.text();
                                                 if (StringUtils.isNoneBlank(text)) {
                                                     Map<String, String> ms = obm.readValue(text, new TypeReference<>() {
                                                     });
                                                     // 心跳数据直接回复
                                                     if ("PING_MESSAGE".equals(ms.get("action"))) {
                                                         returnMap.put("action", "PONG_MESSAGE");
                                                         ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(ms)));
                                                         return;
                                                     }
                                                     if (StringUtils.isNoneBlank(ms.get("云盒编号"))) {
                                                         log.info("<<< {}", text);
                                                         if (StringUtils.isNoneBlank(ms.get("指令编号"))) {
                                                             if (StringUtils.isNoneBlank(ms.get("控制权")) && "1".equals(ms.get("控制权"))) {
                                                                 publishEvent(ms);// 发出事件
                                                             }
                                                         } else {
                                                             // 注册: boxSn -> user -> connect (1:N:1)
                                                             synchronized (channels) {
                                                                 String boxSn = ms.get("云盒编号");
                                                                 if (channels.containsKey(boxSn)) {
                                                                     List<Channel> channelList = channels.get(boxSn);
                                                                     channelList.add(ctx.channel());
                                                                 } else {
                                                                     channels.put(boxSn, new ArrayList<>() {{
                                                                         add(ctx.channel());
                                                                     }});
                                                                 }
                                                             }
                                                         }
                                                     }
                                                 }
                                             }

                                             @Override
                                             public void handlerRemoved(ChannelHandlerContext ctx) {
                                                 synchronized (channels) {
                                                     ctx.channel().close();
                                                     channels.forEach((key, val) -> {
                                                         if (!val.isEmpty()) {
                                                             Iterator<Channel> iterator = val.iterator();
                                                             while (iterator.hasNext()) {
                                                                 if (!iterator.next().isActive()) {
                                                                     log.info("删除云盒 {} 下不活跃 Channel", key);
                                                                     iterator.remove();
                                                                 }
                                                             }
                                                         } else {
                                                             log.info("云盒 {} 下无连接, 执行删除!", key);
                                                             channels.remove(key);
                                                         }
                                                     });
                                                 }
                                             }

                                             @Override
                                             public void channelActive(ChannelHandlerContext ctx) {
                                                 ctx.executor().schedule(() -> {
                                                     returnMap.put("action", "READY_MESSAGE");
                                                     try {
                                                         ctx.writeAndFlush(new TextWebSocketFrame(obm.writeValueAsString(returnMap)));
                                                     } catch (JsonProcessingException e) {
                                                         throw new RuntimeException(e);
                                                     }
                                                 }, 1, TimeUnit.SECONDS);
                                             }
                                         }
                                );
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 127) // 设置TCP连接数该连接数受 Linux 的 /proc/sys/net/core/somaxconn 影响
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
        if (!channels.isEmpty()) {
            channels.values().forEach(el -> el.forEach(ChannelOutboundInvoker::close));
        }
    }
}
