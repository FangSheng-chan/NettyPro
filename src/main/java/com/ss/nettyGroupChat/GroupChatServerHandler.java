package com.ss.nettyGroupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //    此方法表示连接建立，一旦建立连接，就第一个被执行

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端：" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天室\n");
        channelGroup.writeAndFlush("客户端：" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天室\n");
        channelGroup.add(channel);
    }

//    表示 channel 处于活动状态，提示 xxx 上线

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "上线了～");
    }

//    表示channel处于不活动状态，提示 xxx离线

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "离线了～");
    }
//      表示channel断开连接，将xx客户离开信息推送给当前在线用户

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端：" + channel.remoteAddress() + sdf.format(new Date()) + "离开了\n");
        channelGroup.writeAndFlush("客户端" + channel.remoteAddress() + " " + sdf.format(new Date()) + "离开了\n");
        System.out.println("当前channelGroup大小：" + channelGroup.size());
    }
//      读取消息，并进行消息转发

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(item -> {
            if (item != channel) {
                item.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息：" + msg + "\n");
            } else {
                item.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventTye = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventTye = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventTye = "写空闲";
                    break;
                case ALL_IDLE:
                    eventTye = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "---超时时间--" + eventTye);
            System.out.println("服务器做相应处理。。");
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
