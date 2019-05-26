package top.itlq.thinkingInJava.concurrency_21.components;

import java.util.Random;
import java.util.concurrent.*;

/**
 * DelayQueue是一个无界的BlockingQueue,用于放置实现了Delayed接口的对象，其中对象只能在期到期时才能从队列中取走；
 * 队列是有序的，延迟时间最短的在队头，如果没有延迟已经到期，即没有头元素，poll()返回null，不能插入null
 * 最紧急的任务最先取出；
 */

class DelayTask implements Runnable, Delayed{
    private static int count;
    private final int id = count++;
    private long trigger;
    private int delta;

    public DelayTask(int milliseconds){
        delta = milliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(milliseconds,TimeUnit.MILLISECONDS);
    }

    /**
     * Delayed继承了了Comparable接口
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        DelayTask that = (DelayTask)o;
        return Long.compare(trigger, that.trigger);
    }

    @Override
    public void run() {
        System.out.println(this);
    }

    /**
     * 告知延迟到期还有多长时间，或多长时间之前已到期，参数用来转换单位，策略设计模式
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public String toString(){
        return "(" + id + ":" + delta +  ")";
    }

    public static class LastDelayTask extends DelayTask{
        private ExecutorService executorService;
        public LastDelayTask(int milliseconds, ExecutorService executorService){
            super(milliseconds);
            this.executorService = executorService;
        }
        @Override
        public void run(){
            System.out.println(this);
            executorService.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable{
    private DelayQueue<DelayTask> delayTasks;
    public DelayedTaskConsumer(DelayQueue<DelayTask> delayTasks){
        this.delayTasks = delayTasks;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                delayTasks.take().run();
            }
        }catch (InterruptedException e){
            System.out.println("DelayedTaskConsumer interrupted");
        }
    }
}

public class DelayQueueDemo {
    public static void main(String...args){
        Random random = new Random();
        DelayQueue<DelayTask> delayTasks = new DelayQueue<>();
        for(int i=0;i<20;i++){
            delayTasks.put(new DelayTask(random.nextInt(2000)));
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        delayTasks.put(new DelayTask.LastDelayTask(2000, executorService));
        executorService.execute(new DelayedTaskConsumer(delayTasks));
        executorService.shutdown();
    }
}
