package top.itlq.thinkingInJava.concurrency_21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 创建时传入尺寸作为计数，通过 acquire()方法获得，如果计数为0，阻塞挂起，大于0，计数减1；
 * 调用 release() 后计数加1
 */

class Fat{
    private volatile double d;
    private static int counter;
    private final int id = counter++;
    public Fat(){
        for(int i=0;i<10000;i++){
            d = (Math.PI + Math.E) / i;
        }
    }
    public void operation(){
        System.out.println(this);
    }
    @Override
    public String toString(){
        return "Fat #" + id;
    }
}

class CheckOutTask<T> implements Runnable{
    private Pool<T> pool;
    public CheckOutTask(Pool<T> pool){
        this.pool = pool;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                T item = pool.checkOut();
                System.out.println("checked out " + item);
                TimeUnit.MILLISECONDS.sleep(100);
                pool.checkIn(item);
                System.out.println("checked in " + item);
            }
        }catch (InterruptedException e){
            System.out.println("CheckOutTask interrupted");
        }
    }
}

public class SemaphoreDemo{
    final static int SIZE = 5;
    public static void main(String...args) throws InterruptedException{
        Pool<Fat> fatPool = new Pool<>(Fat.class, SIZE);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<SIZE;i++){
            executorService.execute(new CheckOutTask<>(fatPool));
        }
        List<Fat> checkOutList = new ArrayList<>();
        for(int i=0;i<SIZE;i++){
            Fat fat = fatPool.checkOut();
            System.out.println("main thread checkout" + fat);
            checkOutList.add(fat);
        }
        Future<?> blocked = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Fat fat = fatPool.checkOut();
                    throw new RuntimeException("shouldn't check out " + fat);
                }catch (InterruptedException e){
                    System.out.println("blocked interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(1);
        blocked.cancel(true);
        for(Fat fat:checkOutList){
            fatPool.checkIn(fat);
        }
        executorService.shutdownNow();
    }
}
