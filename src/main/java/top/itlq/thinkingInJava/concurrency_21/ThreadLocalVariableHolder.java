package top.itlq.thinkingInJava.concurrency_21;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程本地存储，自动化机制，为每个线程都创建不同的本地存储，将状态与线程联系起来
 */

class Accessor implements Runnable{
    private final int id;

    public Accessor(int ident){
        id = ident;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString(){
        return "#" + id + " " + ThreadLocalVariableHolder.get();
    }
}

public class ThreadLocalVariableHolder {
    // ThreadLocal通常作为静态域，初始值方法默认返回null，重写其initialValue()覆盖，
    // 虽然只有一个静态对象，每个线程都创建不同的存储，所以每个线程都会初始化值；
    // ThreadLocal有 get() set() 方法，分别返回关联对象的副本、将对应线程的值设置为某个值，因为没有线程安全问题，所以不需要同步
    private static ThreadLocal<Integer> value = new ThreadLocal<>(){
        private Random random = new Random();
        // 默认初始值为null
        protected synchronized Integer initialValue(){
            return random.nextInt(10000);
        }
    };
    public static void increment(){
        value.set(value.get() + 1);
    }
    public static int get(){
        return value.get();
    }
    public static void main(String...args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            executorService.execute(new Accessor(i));
        }
        executorService.shutdown();
        TimeUnit.MILLISECONDS.sleep(50);
        System.exit(0);
    }
}
