package com.ss.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
//        创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
//        配置 bootstrap
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)             //使用NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)       // 服务器给BossGroup设置
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast()
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("....服务器 is ready");
            ChannelFuture cf = bootstrap.bind(6668).sync();
//            future.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture future) throws Exception {
//                    if (future.isSuccess()) {
//                        System.out.println(new Date() + ": 端口[" + 6668 + "]绑定成功");
//                    }else {
//                        System.out.println(new Date() + ": 端口[" + 6668 + "]绑定失败");
//                    }
//                }
//            });

            cf.addListener((future) -> {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口[" + 6668 + "]绑定成功");
                } else {
                    System.out.println(new Date() + ": 端口[" + 6668 + "]绑定失败");
                }
            });
            cf.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
