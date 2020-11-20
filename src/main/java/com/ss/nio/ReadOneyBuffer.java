package com.ss.nio;

import java.nio.ByteBuffer;

public class ReadOneyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
//
//        while (readOnlyBuffer.hasRemaining()){
//            System.out.println(readOnlyBuffer.get());
//        }

//        readOnlyBuffer.put((byte) 1);
    }
}
