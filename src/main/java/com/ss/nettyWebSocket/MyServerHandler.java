package com.ss.nettyWebSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.channels.SocketChannel;

public class MyServerHandler extends SimpleChannelInboundHandler<SocketChannel> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SocketChannel msg) throws Exception {

    }
}
