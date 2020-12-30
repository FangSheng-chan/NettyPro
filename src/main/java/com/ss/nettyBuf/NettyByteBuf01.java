package com.ss.nettyBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {

        // 1、在 netty 的buffer中，不需要使用flip进行反转
        // 因为底层维护了  readedIndex 和 writerIndex

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity =" + buffer.capacity());

//        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));
//        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("执行完毕");
    }
}
