package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 常用的线程管理 Executor CachedThreadPool，代替Thread用来创建管理线程
 */
public class CachedThreadPool {
    public static void main(String...args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<5;i++)
            executorService.execute(new LiftOff());
        executorService.shutdown();
    }
}
