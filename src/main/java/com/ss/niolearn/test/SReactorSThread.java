package com.ss.niolearn.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SReactorSThread {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int PORT = 6666;

    public SReactorSThread() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对客户端进行监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                //表示有客户端产生事件
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();//取出产生事件的Channel
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();//准备对其进行遍历
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //将key交给dispatch去处理
                        dispatch(key);
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dispatch
    private void dispatch(SelectionKey key) {
        if (key.isAcceptable()) {
            accept(key);
        } else {
            handler(key);
        }
    }

    //建立新的连接
    private void accept(SelectionKey key) {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对请求进行处理，接收消息---业务处理---返回消息
    private void handler(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(3);
            StringBuilder msg = new StringBuilder();
            while (channel.read(buffer) > 0) {
                msg.append(new String(buffer.array()));
                buffer.clear();
            }
            System.out.println("接收到消息：" + msg.toString());
            //发送消息
            String ok = "OK";
            buffer.put(ok.getBytes());
            //这个flip非常重要哦，是将position置0，limit置于position的位置，以便下面代码进行写入操作能够正确写入buffer中的所有数据
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消该通道的注册并关闭通道，这里非常重要，没有这一步的话当客户端断开连接就会不断抛出IOException
                //是因为，select会一直产生该事件。
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /********调用**************/

    public static void main(String[] args) {
        SReactorSThread sReactorSThread = new SReactorSThread();
        sReactorSThread.listen();
    }
}



