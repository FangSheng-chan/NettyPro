package com.ss.nettyHttp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author ss
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型 = " + msg.getClass());

            System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

            HttpRequest request = (HttpRequest) msg;

            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico，不做响应");
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("HelloWorld", CharsetUtil.UTF_8);
            //构造一个http的响应，即HTTPResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

//            将 数 据 写 到 ChannelPipeline 中 当 前ChannelHandler 的下一个 ChannelHandler 开始处理（出站）
            //将构建好的 response 返回
            ctx.writeAndFlush(response);
        }
    }
}
