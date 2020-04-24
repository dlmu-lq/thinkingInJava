package top.itlq.thinkingInJava.io_18.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerNIO {

    public static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8081));
        serverSocketChannel.configureBlocking(false);

        // 底层，事件通知机制
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            // 阻塞，监听事件
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for(SelectionKey selectionKey:selectionKeys){
                if(selectionKey.isAcceptable()){
                    // 事件类型，客户端请求
                    assert Objects.equals(selectionKey.channel(), serverSocketChannel);
                    // 不阻塞模式下，立即返回，可能为null
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    if(socketChannel != null){
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                }else if(selectionKey.isReadable()){
                    // 事件类型，可读取
                    threadPoolExecutor.submit(()->{
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // TODO 堆外内存；
                        try {
                            int readed = socketChannel.read(byteBuffer);
                            if(readed > 0){
                                byteBuffer.flip(); // 转为读模式
                                System.out.println(new String(byteBuffer.array()));
                                socketChannel.write(ByteBuffer.wrap("test".getBytes()));
                                // socketChannel.close();
                            }else{
                                // 不取消 selector 似乎会一直有事件
                                selectionKey.cancel();
                            }
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
