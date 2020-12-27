package com.ss.nettyinboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ss
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /***
     *
     * @param ctx 上下文对象
     * @param in  入站的 ByteBuf
     * @param out List 集合 ，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder  被调用");
        // 因为long 是8个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
