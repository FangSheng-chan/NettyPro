package com.ss.niolearn.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class t8 {
    public static void main(String[] args) {
//        Selector.open()

    }

    @Test
    public void Server() throws IOException {
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //把 serverSocketChannel 注册到 selector ，关心事件为：OP_ACCEPT，有新的客户端连接
        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println();
        //循环等待客户端连接
        while (true) {
            //等待1秒，如果没有事件发生，就返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回的 > 0,表示已经获取到关注的事件
            // 就获取到相关的 selectionKey 集合，反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历 Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据 key 对应的通道发生的事件，做相应的处理
                if (key.isAcceptable()) {//如果是 OP_ACCEPT，有新的客户端连接
                    //该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个SocketChannel：" + socketChannel.hashCode());
                    //将SocketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector，关注事件为 OP_READ，同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    //通过key，反向获取到对应的Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端：" + new String(buffer.array()));
                }
                //手动从集合中移除当前的 selectionKey，防止重复操作
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
        if (!socketChannel.connect(socketAddress)){ //如果不成功
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        //如果连接成功，就发送数据
        String str = "hello, 尚硅谷";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,实际上就是将buffer数据写入到channel
        socketChannel.write(byteBuffer);
        System.in.read();
    }

}
