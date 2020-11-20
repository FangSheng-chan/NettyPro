package com.ss.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

//文件拷贝
public class NioFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/ss/Documents/test/1.png");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/ss/Documents/test/2.png");

        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        long size = sourceCh.size();


        destCh.transferFrom(sourceCh,0,sourceCh.size());

        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
