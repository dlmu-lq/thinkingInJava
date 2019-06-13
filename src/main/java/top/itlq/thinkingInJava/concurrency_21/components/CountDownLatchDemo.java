package top.itlq.thinkingInJava.concurrency_21.components;

import java.util.concurrent.*;

/**
 * 将一个任务拆分为n，创建一个初始计数值为n的CountDownLatch，每个任务完成后，会调用CountDownLatch.countDown()
 * 需要等待的任务调用相同CountDownLatch对象的await()
 */

class Task implements Runnable{
    private static int count;
    private int id = count++;
    private CountDownLatch countDownLatch;
    public Task(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println("task" + id + " finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PreTask implements Runnable{
    private static int count;
    private int id = count++;
    private CountDownLatch countDownLatch;
    public PreTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("preTask" + id + " finished");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class CountDownLatchDemo {
    public static void main(String...args){
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = new ThreadPoolExecutor(0,7,
                60L,TimeUnit.SECONDS,
                new SynchronousQueue<>());
        for(int i=0;i<2;i++){
            executorService.execute(new Task(countDownLatch));
        }
        for(int i=0;i<5;i++){
            executorService.execute(new PreTask(countDownLatch));
        }
        executorService.shutdown();
    }
}
