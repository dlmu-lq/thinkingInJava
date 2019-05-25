package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 汽车打蜡、抛光的的线程协作
 */

class Car2{
    private boolean waxOn = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 改变为打蜡状态，并通知挂起的线程
     */
    public void waxed(){
        lock.lock();
        try {
            waxOn = true;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    /**
     * 改变抛光未打蜡状态，并通知挂起的线程
     */
    public void buffered(){
        lock.lock();
        try {
            waxOn = false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 检查是否打蜡，在抛光前需要使用。如果没有，挂起线程（等待后被通知了，在循环中再进行检查）；如过打过蜡，结束方法，表示可进行抛光了
     * @throws InterruptedException
     */
    public void waitForWaxing() throws InterruptedException{
        lock.lock();
        try {
            while (!waxOn){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * 检查是否抛光/未打蜡，在打蜡前使用。如果处在打蜡状态，需要挂起线程等待通知再检查；如果处在未打蜡状态，可以结束等待，进行打蜡操作。
     * @throws InterruptedException
     */
    public void waitForBuffering() throws InterruptedException{
        lock.lock();
        try {
            while (waxOn){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 打蜡任务，需要判断等待去蜡
 */
class WaxOn2 implements Runnable{
    private Car2 car;
    public WaxOn2(Car2 car){
        this.car = car;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax On!");
                TimeUnit.SECONDS.sleep(1);
                car.waxed();
                // 如果没有抛光，加挂起打蜡线程，默认抛光状态
                car.waitForBuffering();
            }
        }catch (InterruptedException e){
            System.out.println("waxOn2 interrupted");
        }
        System.out.println("WaxOn2 task ended");
    }
}

/**
 * 抛光任务，需要等待打蜡
 */
class WaxOff2 implements Runnable{
    private Car2 car;
    public WaxOff2(Car2 car){
        this.car = car;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                // 如果没有打蜡，就挂起抛光线程
                car.waitForWaxing();
                System.out.println("Wax Off!");
                TimeUnit.SECONDS.sleep(1);
                car.buffered();
            }
        }catch (InterruptedException e){
            System.out.println("waxOff2 interrupted");
        }
        System.out.println("WaxOff2 task ended");
    }
}

public class WaxOnMatic2 {
    public static void main(String...args) throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Car2 car = new Car2();
        executorService.execute(new WaxOff2(car));
        executorService.execute(new WaxOn2(car));
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}
