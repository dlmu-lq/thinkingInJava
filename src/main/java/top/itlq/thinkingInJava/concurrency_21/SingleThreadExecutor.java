package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数量为 1的 FixedThreadPool
 */
public class SingleThreadExecutor {
    public static void main(String[]args){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new LiftOff());
        executorService.execute(new LiftOff());
        executorService.shutdown();
    }
}
