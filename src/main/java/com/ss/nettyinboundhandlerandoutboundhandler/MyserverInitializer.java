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

        // 入栈的handler进行解码
        pipeline.addLast(new MyByteToLongDecoder2());

        // 出栈的handler进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        // 自定义的handler
        pipeline.addLast(new MyserverHandler());
    }
}
