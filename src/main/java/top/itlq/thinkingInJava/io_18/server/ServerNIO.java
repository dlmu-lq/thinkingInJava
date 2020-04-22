package top.itlq.thinkingInJava.io_18.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerNIO {

    public static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));

        // 底层，事件通知机制
        Selector selector = Selector.open();

        while (true){
            // 阻塞
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            if(socketChannel != null){
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for(SelectionKey selectionKey:selectionKeys){
                SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                if(selectionKey.isReadable()){
                    threadPoolExecutor.submit(()->{
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // TODO 堆外内存；
                        try {
                            socketChannel1.read(byteBuffer);
                            byteBuffer.flip(); // 转为读模式
                            System.out.println(new String(byteBuffer.array()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
                }
            }
        }
    }
}
