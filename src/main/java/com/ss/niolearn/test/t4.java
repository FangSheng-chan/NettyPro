package com.ss.niolearn.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 * 将 channel 数据放入到 bytebuffer
 */
public class t4 {
    public static void main(String[] args) throws IOException {

        File file = new File("/Users/ss/Documents/test/s.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        byte[] bytes ;


        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();

        }

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
