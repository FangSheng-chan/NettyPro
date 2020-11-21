package com.ss.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个Selector对象
        Selector selector = Selector.open();
        //绑定66666接口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println();
        //循环等待客户端款
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
//            如果返回的 > 0 ,表示已经获取到关注的事件
//            就获取到相关 selectionKey 集合，反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

//            遍历Set<SelectionKey>
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
//                获取到SelectionKey
                SelectionKey key = keyIterator.next();
//                根据 key 对应的通道发生的事件，做相应的处理
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个SocketChannel：" + socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer attachment = (ByteBuffer) key.attachment();
                    channel.read(attachment);
                    System.out.println("from 客户端：" + new String(attachment.array()));
                }
                keyIterator.remove();
            }
        }
    }

    @Test
    public void Client() throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的IP和端口
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        //如果不成功
        if (!socketChannel.connect(socketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }
        //如果连接成功，就发送数据
        String str = "hello, ss";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,实际上就是将buffer数据写入到channel
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}


















