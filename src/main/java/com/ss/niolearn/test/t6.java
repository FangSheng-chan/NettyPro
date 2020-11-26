package com.ss.niolearn.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class t6 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileOutputStream = new FileInputStream("/Users/ss/Documents/test/2.png");

        FileOutputStream fileOutputStream2 = new FileOutputStream("/Users/ss/Documents/test/7.png");

        FileChannel source = fileOutputStream.getChannel();

        FileChannel dest = fileOutputStream2.getChannel();

        source.transferTo(0,source.size(),dest);

        source.close();
        dest.close();

        fileOutputStream.close();
        fileOutputStream2.close();


    }
}
