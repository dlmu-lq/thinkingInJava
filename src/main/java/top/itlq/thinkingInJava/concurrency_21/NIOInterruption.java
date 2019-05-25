package top.itlq.thinkingInJava.concurrency_21;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 被阻塞的nio通道会自动地响应中断
 */

class NIOBlocked implements Runnable{
    private SocketChannel socketChannel;

    public NIOBlocked(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            socketChannel.read(ByteBuffer.allocate(1));
        }catch (ClosedByInterruptException e){
            System.out.println("ClosedByInterruptException");
            System.out.println("Thread.interrupted(): "
                    + Thread.interrupted());
        }catch (AsynchronousCloseException e){
            System.out.println("AsynchronousCloseException");
            System.out.println("Thread.interrupted(): "
                    + Thread.interrupted());
        }catch (IOException e){
            System.out.println("IOException");
        }
    }
}

public class NIOInterruption {
    public static void main(String...args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        InetSocketAddress isa = new InetSocketAddress("localhost",8080);
        SocketChannel socketChannel1 = SocketChannel.open(isa),
                socketChannel2 = SocketChannel.open(isa);
        Future<?> future = executorService.submit(new NIOBlocked(socketChannel1));
        executorService.execute(new NIOBlocked(socketChannel2));
        // 终止提交任务
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(1);
        future.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        socketChannel2.close();
    }
}
