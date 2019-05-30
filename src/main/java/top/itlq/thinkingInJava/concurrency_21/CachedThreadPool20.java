package top.itlq.thinkingInJava.concurrency_21;

import top.itlq.thinkingInJava.concurrency_21.threadBasic.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class LiftOff20 implements Runnable{
    private int countDown = 10;
    private static int taskCount = 0;
    private final int taskId = taskCount++;

    @Override
    public void run(){
        try {
            while (countDown > 0){
                System.out.println("#" + taskId + " " + countDown--);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }
}

public class CachedThreadPool20 {
    public static void main(String[] args) throws Exception {
        System.out.println("Using LiftOff:");
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++) {
            Future<?> f = exec.submit(new LiftOff());
//            f.cancel(true);
        }
        exec.shutdownNow();
        if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
            System.out.println("Some tasks were not terminated");
        // Using modified LiftOff:
        System.out.println("\nUsing LiftOff20:");
        ExecutorService exec20 = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++) {
            Future<?> f = exec20.submit(new LiftOff20());
//            f.cancel(true);
        }
        exec20.shutdownNow();
        if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
            System.out.println("Some tasks were not terminated");
    }
}
