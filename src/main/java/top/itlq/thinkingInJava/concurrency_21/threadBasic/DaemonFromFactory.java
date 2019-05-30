package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 创建 ExecutorService时传入ThreadFactory
 * 后台线程关闭
 */
public class DaemonFromFactory implements Runnable {
    @Override
    public void run() {
        try {
            while (true){
                System.out.println(Thread.currentThread() + " " + this);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String...args) throws Exception{
        // 这个ThreadFactory放到ExecutorService的创建方法中被用来创建线程
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i=0;i<10;i++)
            executorService.execute(new DaemonFromFactory());
        executorService.shutdown();
        System.out.println("main thread sleeping...");
        TimeUnit.MILLISECONDS.sleep(150);
        System.out.println("main thread end");
    }
}
