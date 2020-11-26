package com.ss.niolearn.test;

public class t12 {
    public static void main(String[] args){
        t12 testFinal = new t12();
        StringBuffer buffer = new StringBuffer("hello");
        testFinal.changeValue(buffer);
        System.out.println(buffer);

    }

    public void changeValue(final StringBuffer buffer){
        //final修饰引用类型的参数，不能再让其指向其他对象，但是对其所指向的内容是可以更改的。
        //buffer = new StringBuffer("hi");
        buffer.append("world");
    }
}
