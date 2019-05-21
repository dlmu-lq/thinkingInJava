package top.itlq.thinkingInJava.concurrency_21;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数量为 1的 FixedThreadPool
 */
public class SingleThreadExecutor {
    public static void main(String[]args){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new ListOff());
        executorService.execute(new ListOff());
        executorService.shutdown();
    }
}
