package top.itlq.thinkingInJava.concurrency_21.sharedResource;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock可以尝试获取锁，以干其他事情
 */
public class AttemptLocking {
    private Lock lock = new ReentrantLock();
    public void utimed(){
        boolean captured = lock.tryLock();
        try {
            System.out.println("utimed captured:" + captured);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(captured){
                System.out.println("utimed unlock");
                lock.unlock();
            }
        }
    }
    public void timed(){
        try {
            long start = System.currentTimeMillis();
            boolean captured = lock.tryLock(1, TimeUnit.SECONDS);
            try {
                System.out.println("timed captured:" + captured +
                        "; time:" + (System.currentTimeMillis() - start) + "ms");
            }finally {
                if(captured)
                    lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String...args) throws Exception{
        AttemptLocking al = new AttemptLocking();
        al.utimed();
        // 单线程不会被utime阻塞
        al.timed();
        new Thread(){
            @Override
            public void run(){
                al.lock.lock();
            }
        }.start();
        TimeUnit.MILLISECONDS.sleep(100);
        al.utimed();
        al.timed();
    }
}
