package com.mdsd.cloud.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2025-03-05]
 */
@Slf4j
public class SocketUtil {

    /**
     * 线程组 (EventLoopGroup)
     * BossGroup: 负责监听客户端连接请求 (通常单线程即可)
     * WorkerGroup: 负责处理已建立连接的I/O操作和业务逻辑 (多线程)
     * <p>
     * 通道类型(Channel)
     * 使用 NioServerSocketChannel 作为服务器通道,支持非阻塞I/O
     * <p>
     * 网络参数配置
     * SO_BACKLOG: 控制操作系统连接队列大小,建议设为 1024 或更高(受系统限制)
     * SO_REUSEADDR: 允许地址复用,避免端口占用问题
     * SO_KEEPALIVE: 启用TCP保活机制,检测空闲连接
     * TCP_NODELAY: 禁用Nagle算法,减少小数据包的延迟(适合WebSocket实时性需求)
     * SO_RCVBUF/SO_SNDBUF: 调整接收/发送缓冲区大小,提升吞吐量
     * <p>
     * WebSocket 协议处理
     * HttpServerCodec: HTTP请求/响应的编解码
     * ChunkedWriteHandler: 支持分块写入 (适用于大文件传输)
     * HttpObjectAggregator: 聚合HTTP消息 (将多个 HttpContent 合并为 FullHttpRequest)
     * WebSocketServerProtocolHandler: 处理WebSocket握手和协议升级,支持自定义路径和最大帧大小
     * <p>
     * 业务处理器
     * 自定义 WebSocketBusinessHandler 实现 SimpleChannelInboundHandler<WebSocketFrame>,处理WebSocket消息(如 TextWebSocketFrame、BinaryWebSocketFrame)
     * <p>
     * 异常处理
     * 添加 ExceptionLogger 捕获未处理异常,防止服务崩溃
     * <p>
     * 性能优化
     * 合理设置 HttpObjectAggregator 的最大内容长度,避免过大导致内存溢出,过小导致请求被拒绝
     * 调整缓冲区大小：根据业务需求调整 SO_RCVBUF 和 SO_SNDBUF 提升吞吐量
     * 多线程处理：WorkerGroup 的线程数应根据CPU核心数和负载调整
     */
    public static void createWebSocketServer(ChannelHandler handler, int port) {
        // 创建服务端启动引导器, 配置线程模型  EventLoop 等于 Thread
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup()) // Boss线程组(监听连接), Worker线程组 (处理I/O)
                .channel(NioServerSocketChannel.class) // 使用NIO服务器通道
                .option(ChannelOption.SO_BACKLOG, 32) // 设置线程队列连接个数, 受Linux的 /proc/sys/net/core/somaxconn 影响
                .option(ChannelOption.SO_REUSEADDR, true) // 允许地址复用
//                .option(ChannelOption.SO_KEEPALIVE, true) // 启用TCP保活,在长时间没有数据传输时,操作系统会发送保活探测包以确保连接有效
//                .option(ChannelOption.TCP_NODELAY, true) // 禁用Nagle算法,减少小数据包的延迟
                .childOption(ChannelOption.SO_RCVBUF, 128 * 1024) // 接收缓冲区大小
                .childOption(ChannelOption.SO_SNDBUF, 128 * 1024) // 发送缓冲区大小
//                .handler(new LoggingHandler(LogLevel.INFO)) // 父Channel日志记录
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 添加一个 ChannelInitializer 来初始化每一个新的Channel
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline()
                                .addLast(new HttpServerCodec()) // 1.HTTP 编解码器
                                .addLast(new ChunkedWriteHandler()) // 2.支持大文件分块写入
                                .addLast(new HttpObjectAggregator(64 * 1024)) // 3.聚合HTTP消息(将多个 HttpContent 合并为 FullHttpRequest)
                                .addLast(new WebSocketServerProtocolHandler(
                                        "/websocket", // WebSocket路径
                                        null, // 子协议 (可选)
                                        true, // 是否允许复用 (允许多个WebSocket连接复用同一个Channel)
                                        65536 // 最大帧大小 (64KB)
                                )) //  4.WebSocket协议处理器 (处理握手、消息升级)
                                .addLast("heartbeat", new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS)) // 心跳检测
                                .addLast(handler); // 5.自定义WebSocket业务处理器
                    }
                });
        try {
            // 绑定端口并启动接收进来的连接
            serverBootstrap.bind(port).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    log.info("WEB SOCKET 服务启动成功, 监听端口: {}", port);
                } else {
                    log.error("WEB SOCKET 服务启动失败 -> {}", f.cause().getMessage());
//                    group.shutdownGracefully(); // 启动失败也要释放资源
                }
            }).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 什么是 Nagle 算法？
     * Nagle 算法是 TCP 的一种优化机制,其主要目的是减少小数据包的数量,提高网络利用率,它的基本规则是:
     * <p>
     * 如果有未确认的小数据包 (即还未收到 ACK),就将后续的小数据包缓存起来,直到前一个被确认再一并发送;
     * <p>
     * 优点:
     * 减少网络上过多的小数据包,降低网络拥塞风险
     * 提高带宽利用率
     * <p>
     * 缺点:
     * 增加了延迟 —— 因为数据可能会被缓存等待合并后再发送,这对实时性要求高的应用 (如游戏、聊天、WebSocket) 不利
     * <p>
     * 设置 .option(ChannelOption.TCP_NODELAY, true) 的作用:
     * - 启用 TCP_NODELAY 选项
     * - 禁用 Nagle 算法
     * - 允许立即发送小数据包,无需等待前面的数据被确认
     */
    public static ChannelFuture createTcpClient(ChannelHandler handler, String host, int port) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 64 * 1024)
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                .option(ChannelOption.TCP_NODELAY, true) // 禁用Nagle算法,减少小数据包的延迟
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(10 * 1024, 2, 2, 0, 0)) // 自定义协议解码器
                                .addLast("ping", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS)) // 心跳检测
                                .addLast(handler);
                    }
                });
        return bootstrap.connect(new InetSocketAddress(host, port)).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                log.info("TCP 客户端连接成功 -> {}:{}", host, port);
            } else {
                log.error("TCP 客户端连接失败 -> {}", f.cause().getMessage());
                f.channel().close();
            }
        }).syncUninterruptibly(); // 调用 syncUninterruptibly() 当前线程会阻塞,直到该异步操作完成
        /*
         * 1.同步等待异步操作完成
         * Netty 的 I/O 操作（如 connect()、bind()、close() 等）都是异步非阻塞的，返回的是一个 ChannelFuture 对象。你可以通过监听或同步等待的方式判断操作是否完成
         *
         * 2.不抛出 InterruptedException
         * - 与 sync() 不同 syncUninterruptibly() 在等待期间忽略中断信号,即使有其他线程调用了当前线程的 interrupt(),它也不会抛出 InterruptedException
         * - 更加 "安静" 地等待,适合你不关心中断信号的场景
         */
    }

    /**
     * .option(ChannelOption.SO_SNDBUF, 64 * 1024)
     * 释义: 设置操作系统发送缓冲区,它决定了操作系统在发送数据前可以缓冲的数据量
     * 默认: 65535 字节
     * <p>
     * .option(ChannelOption.SO_RCVBUF, 64 * 1024)
     * 释义: 设置操作系统接收缓冲区,它影响操作系统能够缓冲的传入数据量
     * 默认: 65535 字节
     * <p>
     * .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(4096))
     * 释义: 设置 Netty 固定接收缓冲区大小
     * 默认: 2048 字节
     * <p>
     * 注:
     * - SO_SNDBUF 和 SO_RCVBUF 控制的是操作系统级别的发送和接收缓冲区大小，并不直接限制单个 UDP 数据包的大小
     * - Netty 中的 DatagramPacket 默认有一个较小的缓冲区大小（如 2048 字节），这是由 ByteBufAllocator 配置决定的
     * - 如果你需要处理更大的 UDP 数据包,应该通过配置 RecvByteBufAllocator 来调整 Netty 的接收缓冲区大小,而不是仅仅依赖于 SO_RCVBUF 的设置
     */
    public static Channel createUdpServer(ChannelHandler handler, int port) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_SNDBUF, 128 * 1024)
                .option(ChannelOption.SO_RCVBUF, 128 * 1024)
                .option(ChannelOption.SO_REUSEADDR, true) // 启用地址重用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 使用池化缓冲区分配器
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(64 * 1024)) // 设置固定接收缓冲区大小
                .handler(handler);
        try {
            log.info("启动 UDP 监听端口: {}", port);
            return bootstrap.bind(port).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    log.info("UDP 服务启动成功, 监听端口: {}", port);
                } else {
                    log.error("UDP 服务启动失败 -> {}", f.cause().getMessage());
//                    group.shutdownGracefully(); // 启动失败也要释放资源
                    bootstrap.disableResolver();
                }
            }).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
