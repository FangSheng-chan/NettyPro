package com.ss.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 * 从文件中读取内容
 */
public class NioFileChannel02 {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/ss/Documents/test/file.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(byteBuffer);


        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();


    }

}
