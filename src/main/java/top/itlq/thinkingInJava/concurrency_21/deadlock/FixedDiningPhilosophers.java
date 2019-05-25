package top.itlq.thinkingInJava.concurrency_21.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedDiningPhilosophers {
    public static void main(String...args) throws Exception{
        int ponder = 0;
        int size = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ChopStick[] chopSticks = new ChopStick[5];
        for(int i=0;i<size;i++){
            chopSticks[i] = new ChopStick(i);
        }
        for(int i=0;i<size;i++){
            if(i == size - 1){
                executorService.execute(new Philosopher(chopSticks[0], chopSticks[i], i, ponder));
            }else{
                executorService.execute(new Philosopher(chopSticks[i], chopSticks[(i+1)%size], i, ponder));
            }
        }
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
