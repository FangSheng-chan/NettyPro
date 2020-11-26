package com.ss.niolearn.test;

import java.nio.ByteBuffer;

public class t2 {
    public static void main(String[] args) {
        readOnly();
    }
//      抽取方法 option command M
    private static void readOnly() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        readOnlyBuffer.put((byte) 1024);
    }
}
