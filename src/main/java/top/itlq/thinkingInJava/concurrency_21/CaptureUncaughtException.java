package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionTask implements Runnable{

    @Override
    public void run() {
        throw new RuntimeException("线程异常");
    }
}

// 线程异常捕获处理器
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught in myUncaughtExceptionHandler" + e);
    }
}

class HandlerThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        // 在线程上附着异常捕获处理器，会在线程因未捕获的异常而濒临死亡时被调用
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return thread;
    }
}

public class CaptureUncaughtException {
    public static void main(String[]args){
        // 不能捕获
        try {
            new Thread(new ExceptionTask()).start();
        }catch (Exception e){
            System.out.println("caught in main thread");
        }
        // 可以捕获
        new HandlerThreadFactory().newThread(new ExceptionTask()).start();
        // 可以捕获
        ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
        executorService.execute(new ExceptionTask());
        executorService.shutdown();
        // 可以设置Thread的静态域，默认未捕获异常处理器，在线程无自己的 UncaughtExceptionHandler 时被调用
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }
}
