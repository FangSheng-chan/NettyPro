package com.ss.nettyHttp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class TestHttpServerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入 netty 提供的 httpServerCodec codec => [coder . decoder]
        // HttpServerCodec说明
        // 1、HttpServerCodec 是 netty 提供的处理 http 的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

    }
}
