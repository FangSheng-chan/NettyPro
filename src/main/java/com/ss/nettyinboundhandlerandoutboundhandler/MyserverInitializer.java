package com.ss.nettyinboundhandlerandoutboundhandler;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author ss
 */
public class MyserverInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 入栈
        pipeline.addLast(new MyByteToLongDecoder());

        // 自定义的handler
        pipeline.addLast(new MyserverHandler());
    }
}
