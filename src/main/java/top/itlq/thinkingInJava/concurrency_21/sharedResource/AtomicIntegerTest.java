package top.itlq.thinkingInJava.concurrency_21.sharedResource;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用原子类如AtomicInteger使基本类型的非原子性复合操作具有原子性，调优时使用
 */
public class AtomicIntegerTest implements Runnable{


    private AtomicInteger value = new AtomicInteger();

    @Override
    public void run() {
        while (true)
            increment();
    }

    private void increment(){
        value.addAndGet(2);
    }

    // 若像正确，所有相关的方法都应设为同步
    public int getValue(){
        return value.get();
    }

    public static void main(String...args){
        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){
                System.out.println("after 5s, Aborting...");
                System.exit(0);
            }
        },5000);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicIntegerTest att = new AtomicIntegerTest();
        executorService.execute(att);
        executorService.shutdown();
        while (true){
            int value = att.getValue();
            if(value %2 != 0){
                System.out.println(value);
                System.exit(0);
            }
        }
    }
}
