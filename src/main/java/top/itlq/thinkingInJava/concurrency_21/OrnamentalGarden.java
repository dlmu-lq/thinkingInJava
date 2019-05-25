package top.itlq.thinkingInJava.concurrency_21;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 一个统计花园不同入口进入的人次，及总人次
 */

class Count{
    private int count;
    public synchronized void increment(){
        count++;
    }
    // 防止读取不确定状态下的值，加同步
    public synchronized int value(){
        return count;
    }
}

class Entrance implements Runnable{
    // 所有入口
    private static List<Entrance> entrances = new ArrayList<>();
    // 此入口人总数
    private int number;
    // 计数
    private static Count count =  new Count();
    // 入口封闭标记
    private static volatile boolean closed = false;

    public Entrance(){
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!closed){
            synchronized (this){
                number++;
            }
            count.increment();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized int getNumber(){
        return number;
    }

    public static void close(){
        closed = true;
    }

    public static int getTotalCount(){
        return count.value();
    }

    public static int getEntrancesSum(){
        int re = 0;
        for(Entrance entrance:entrances){
            re += entrance.getNumber();
        }
        return re;
    }
}

public class OrnamentalGarden {
    public static void main(String[]args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<5;i++)
            executorService.execute(new Entrance());
        executorService.shutdown();
        System.out.println("awaiting termination...");
        if(!executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)){
            System.out.println("of course tasks not terminated");
        }
        System.out.println("finish awaiting");
        TimeUnit.MILLISECONDS.sleep(2000);
        Entrance.close();
        // ExecutorService.awaitTermination(100, TimeUnit.MILLISECONDS)线程池在这个时间内等待所有线程结束，如果结束，返回true，否则返回flase
        if(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)){
            System.out.println("Some tasks not terminated");
        }
        System.out.println(Entrance.getTotalCount());
        System.out.println(Entrance.getEntrancesSum());
    }
}
