package com.ss.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class NioFileChannel03 {
    public static void main(String[] args) throws IOException {
        //FileInputStream读取本地文件
        FileInputStream fileInputStream = new FileInputStream("/Users/ss/Documents/test/1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        //FileOutputStream
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/ss/Documents/test/2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            //从通道读取数据并放在缓冲区中
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            //把缓冲区的数据写入到通道中
            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
