package com.ss.niolearn.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 * 把缓冲区的数据写到通道中
 */
public class t3 {
    public static void main(String[] args) throws IOException {
        String string = "hello，ss";

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/ss/Documents/test/s.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(string.getBytes());

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
