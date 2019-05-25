package top.itlq.thinkingInJava.concurrency_21;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 当在sleep之前标志位设为中断，进入sleep之后立即中断，在捕获异常后重设中断标志位
 */
public class InterruptBeforeSleep implements Runnable{

    private int interruptCount;

    @Override
    public void run() {
        while (true){
            try {
                if(interruptCount < 3){
                    TimeUnit.SECONDS.sleep(1);
                }else{
                    break;
                }
            } catch (InterruptedException e) {
                interruptCount++;
                System.out.println("InterruptBeforeSleep InterruptedException");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String...args){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new InterruptBeforeSleep());
        executorService.shutdown();
        executorService.shutdownNow();
    }
}