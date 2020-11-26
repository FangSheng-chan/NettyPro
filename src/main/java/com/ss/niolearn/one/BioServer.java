package com.ss.niolearn.one;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ss
 */
public class BioServer {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            InetAddress inetAddress = serverSocket.getInetAddress();

            System.out.println("服务器启动了");

            while (true) {
                System.out.println("线程信息：id=" + Thread.currentThread().getId() + "：线程名字：" + Thread.currentThread().getName());
                System.out.println("等待连接 ");
                Socket socket = serverSocket.accept();
                System.out.println("连接到一个客户端");

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        handle(socket);
//                    }
//                }).start();

                executorService.execute(() -> {
                    handle(socket);
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handle(Socket socket) {
        System.out.println("线程信息：id= " + Thread.currentThread().getId() + "; 线程名字：" + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程信息：id= " + Thread.currentThread().getId() + "; 线程名字：" + Thread.currentThread().getName());
                System.out.println("read ....");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
