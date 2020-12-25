package com.ss.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        System.out.println("服务器读取线程："+Thread.currentThread().getName());
//        System.out.println("server ctx = "+ctx);
//
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户度地址："+ctx.channel().remoteAddress());


        //        比如这里我们有一个非常耗时长的业务 -> 异步执行 -> 提交该channel对应的 NIOEventLoop 的 taskQueue
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端2", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        });


        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端3", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端4", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        }, 5, TimeUnit.SECONDS);
        //  用户自定义定时任务 -> 该任务提交到scheduleTaskQueue
        System.out.println("go on...");
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端1", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
