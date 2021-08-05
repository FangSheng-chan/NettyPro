package com.ss.nettyBuf;

import com.sun.xml.internal.ws.api.client.WSPortInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(1024);
        System.out.println(buffer.capacity());

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        byte[] array = byteBuf.array();
        System.out.println(array);

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();

            //将 content 转成字符串
            System.err.println(new String(content, Charset.forName("utf-8")));

            System.err.println("byteBuf= " + byteBuf);

            System.err.println(byteBuf.readerIndex());

            //可读的字节数
            int len = byteBuf.readableBytes();
            System.out.println("len" + len);

            //使用for取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char)byteBuf.getByte(i));
            }
        }
    }
}
