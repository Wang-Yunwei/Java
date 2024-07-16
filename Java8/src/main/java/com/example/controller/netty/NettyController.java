package com.example.controller.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @author WangYunwei [2024-06-07]
 */
public class NettyController {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();// 用于接收进来的连接
        // 创建服务端启动引导器
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 配置线程模型  EventLoop 等于 Thread
        bootstrap.group(eventExecutors)
                .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 来作为服务器的通道实现
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 添加一个 ChannelInitializer 来初始化每一个新的Channel
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 解码器,将接收到的字节数据解码成字符串
                        ch.pipeline().addLast(new StringDecoder());
                        // 编码器,将发送的字符串数据编码成字节数据
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {// 处理业务 handler
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 管道激活显示
                                System.out.println(ctx.channel() + ",hello world");
                            }
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 将接收到的消息回显（发送）回去
                                ctx.write(msg);
                            }
                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                // 消息发送完毕后处理
                                ctx.flush();
                            }
                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                // 出现异常关闭连接
                                cause.printStackTrace();
                                ctx.close();
                            }
                        });
                    }
                }).option(ChannelOption.SO_BACKLOG, 128) // 设置TCP连接的缓冲区大小
                .option(ChannelOption.SO_KEEPALIVE, true); // 设置保持活动连接状态
        // 绑定端口并启动接收进来的连接
        ChannelFuture sync = bootstrap.bind(8081).sync();
        // 等待 socket
        sync.channel().closeFuture().sync();
    }
}
