package top.itlq.thinkingInJava.concurrency_21.terminateThread;

import java.io.InputStream;
import java.util.concurrent.*;

/**
 * 阻塞中断
 */

class SleepBlocked implements Runnable{
    @Override
    public void run(){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (InterruptedException e){
            System.out.println("sleep interrupted");
            // 抛出异常后中断状态将复位
            System.out.println("sleepBlock Thread.interrupted(): " + Thread.interrupted());
            System.out.println("sleepBlock Thread.currentThread().interrupted(): " + Thread.currentThread().isInterrupted());
        }
    }
}

class IOBlocked implements Runnable{
    private InputStream is;

    public IOBlocked(InputStream is){
        this.is = is;
    }

    @Override
    public void run() {
        try {
            is.read();
        } catch (Exception e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("io interrupted");
                System.out.println("after io interrupted, Thread.interrupted(): "
                        + Thread.interrupted());
                System.out.println("after io interrupted, Thread.currentThread().isInterrupted(): "
                        + Thread.currentThread().isInterrupted());
            }else{
                throw new RuntimeException(e);
            }
        }
        System.out.println("Existing IOBlocked run()");
    }
}

class SynchBlocked implements Runnable{

    private synchronized void f(){
        while (true) // 用不释放锁
            Thread.yield();
    }

    public SynchBlocked(){
        new Thread(){
            @Override
            public void run(){
                f();
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        try {
            f();
        }catch (Exception e){
            System.out.println("synchronized interrupted");
        }
        System.out.println("f() called(never)");
    }
}

public class Interrupting {

    private static ExecutorService executorService = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS, new SynchronousQueue<>(), (ThreadFactory) Thread::new);

    public static void test(Runnable r) throws Exception{
        Future<?> future = executorService.submit(r);
        TimeUnit.MILLISECONDS.sleep(50);
        System.out.println("interrupting " + r.getClass().getName());
        future.cancel(true);
        System.out.println("interrupt sent to " + r.getClass().getName());
    }

    public static void main(String...args) throws Exception{
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting...");
        System.exit(0);
    }

}
