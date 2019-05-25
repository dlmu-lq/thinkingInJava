package top.itlq.thinkingInJava.concurrency_21.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLockingDiningPhilosophers {
    public static void main(String...args) throws Exception{
        int ponder = 0;
        int size = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ChopStick[] chopSticks = new ChopStick[5];
        for(int i=0;i<size;i++){
            chopSticks[i] = new ChopStick(i);
        }
        for(int i=0;i<size;i++){
            executorService.execute(new Philosopher(chopSticks[i], chopSticks[(i+1)%size], i, ponder));
        }
        executorService.shutdown();
        System.out.println("Press 'Enter' to stop");
        System.in.read();
        executorService.shutdownNow();
    }
}
