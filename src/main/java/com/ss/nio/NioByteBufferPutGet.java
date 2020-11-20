package com.ss.nio;

import java.nio.ByteBuffer;

public class NioByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.putInt(190);
        byteBuffer.putLong(9);
        byteBuffer.putChar('胜');

        //取出
        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
    }
}
