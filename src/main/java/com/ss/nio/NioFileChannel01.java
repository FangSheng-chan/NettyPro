package com.ss.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 * 写内容到文件中
 */
public class NioFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str = "hell,ss";

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/ss/Documents/test/file.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
