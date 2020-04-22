package top.itlq.thinkingInJava.io_18.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * blocking IO 实现的网络服务
 */
public class ServerBIO {

    public static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            Socket socket = serverSocket.accept();
            // 一个请求连接后，既创建一个线程
            threadPoolExecutor.submit(()->{
                byte[] bytes = new byte[1024];
                try {
                    //没数据，阻塞导致线程浪费；
                    socket.getInputStream().read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(new String(bytes));
            });
        }
    }
}
