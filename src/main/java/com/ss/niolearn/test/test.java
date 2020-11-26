package com.ss.niolearn.test;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class test {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        intBuffer.flip();
//       hasRemaining: 告知当前位置和限制之间是否有元素
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
