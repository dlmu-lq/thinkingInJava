package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 因为互斥而阻塞的线程中断
 */
class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex(){
        lock.lock();
    }

    public void f(){
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }
}

class Blocked2 implements Runnable{

    BlockedMutex blockedMutex = new BlockedMutex();

    // 创建时已获取锁，被主线程任务获取且不释放，因此在此线程将阻塞
    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        blockedMutex.f();
        System.out.println("Broken out of blocked call");
    }
}

public class Interrupting2{
    public static void main(String...args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Blocked2());
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdownNow();
    }
}