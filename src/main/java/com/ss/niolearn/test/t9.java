package com.ss.niolearn.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class t9 {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/ss/Documents/test/1.txt");
        RandomAccessFile rw = new RandomAccessFile(file, "rw");
        byte[] bytes = new byte[(int) file.length()];
        rw.read(bytes);
        Socket socket = new ServerSocket(666).accept();
        socket.getOutputStream().write(bytes);


    }
}
