package com.ss.niolearn.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ss
 */
public class SingleReactorThread {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int PORT = 7777;

    public SingleReactorThread() {
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


    public void listen() {
        while (true) {
            try {
//                监控所有注册的通道，当其中有IO操作可以进行时，将对应的SelectionKey加入到内部集合中并返回,
//                   返回的结果为Channel响应的事件总和，当结果为0时，表示本Selector监听的所有Channel中没有Channel产生事件。
                int count = selector.select();
//                表示有客户端产生事件
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    if (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        if (key.isAcceptable()) {

        }
    }

    private void accept(SelectionKey key) {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handler(SelectionKey key){

    }

}








