package com.ss.nettyBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class test01 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        int capacity = buffer.capacity();
        for (int i = 0; i < capacity; i++) {
            System.out.println(buffer.getByte(i));
            System.out.println(buffer.readByte());
        }

        byte[] content = buffer.array();
        String s = new String(content, StandardCharsets.UTF_8);
        boolean empty = s.isEmpty();
        System.out.println("转字符串" + empty);

        int readerIndex = buffer.readerIndex();
        System.out.println(readerIndex);

    }
}
