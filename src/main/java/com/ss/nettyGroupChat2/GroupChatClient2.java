package com.ss.nettyGroupChat2;

import com.ss.nio.NioServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class GroupChatClient2 {

    private String host;
    private int port;

    public GroupChatClient2(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new GroupChatClientInit());
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // 得到 channel
            Channel channel = channelFuture.channel();
            System.out.println("----" + channel.remoteAddress() + "------");
            // 客户端输入信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        GroupChatClient2 groupChatClient2 = new GroupChatClient2("127.0.0.1", 7000);
        groupChatClient2.run();
    }
}
