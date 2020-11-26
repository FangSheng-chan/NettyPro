package com.ss.niolearn.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class t7 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

//        serverSocketChannel.bind()

        serverSocketChannel.configureBlocking(false);

//        serverSocketChannel.register()
    }

    @Test
    public void test() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLength) {
                long l = socketChannel.read(byteBuffers);
                //累计读取的字节数
                byteRead += l;
                System.out.println("byteRead= " + byteRead);
                //使用流打印，看看当前这个buffer的position和limit
                Arrays.stream(byteBuffers)
                        .map(buffer -> "position=" + buffer.position() + ", limit = " + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            //将所有的 buffer 进行clear操作
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite
                    + ", msgLength=" + msgLength);
        }
    }
}
