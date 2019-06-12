package top.itlq.thinkingInJava.concurrency_21.threadCooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 汽车打蜡、抛光的的线程协作
 */

class Car{
    private boolean waxOn = false;

    /**
     * 改变为打蜡状态，并通知挂起的线程
     */
    public synchronized void waxed(){
        System.out.println("waxed");
        waxOn = true;
        notifyAll();
    }
    /**
     * 改变抛光未打蜡状态，并通知挂起的线程
     */
    public synchronized void buffered(){
        System.out.println("buffered");
        waxOn = false;
        notifyAll();
    }

    /**
     * 检查是否打蜡，在抛光前需要使用。如果没有，挂起线程（等待后被通知了，在循环中再进行检查）；如过打过蜡，结束方法，表示可进行抛光了
     * @throws InterruptedException
     */
    public synchronized void waitForWaxing() throws InterruptedException{
        System.out.println("waitForWaxing start");
        while (!waxOn){
            System.out.println("waitForWaxing while before wait");
            wait();
            System.out.println("waitForWaxing while after wait");
        }
        System.out.println("waitForWaxing end");
    }

    /**
     * 检查是否抛光/未打蜡，在打蜡前使用。如果处在打蜡状态，需要挂起线程等待通知再检查；如果处在未打蜡状态，可以结束等待，进行打蜡操作。
     * @throws InterruptedException
     */
    public synchronized void waitForBuffering() throws InterruptedException{
        System.out.println("waitForBuffering start");
        while (waxOn){
            System.out.println("waitForBuffering while before wait");
            wait();
            // notify后从wait后接着运行
            System.out.println("waitForBuffering while after wait");
        }
        System.out.println("waitForBuffering end");
    }
}

/**
 * 打蜡任务，需要判断等待去蜡
 */
class WaxOn implements Runnable{
    private Car car;
    public WaxOn(Car car){
        this.car = car;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax On!");
                TimeUnit.SECONDS.sleep(1);
                car.waxed();
                System.out.println("WaxOn waitForBuffering");
                // 如果没有抛光，加挂起打蜡线程，默认抛光状态
                car.waitForBuffering();
                System.out.println("WaxOn waitForBuffering end");
            }
        }catch (InterruptedException e){
            System.out.println("waxOn interrupted");
        }
        System.out.println("WaxOn task ended");
    }
}

/**
 * 抛光任务，需要等待打蜡
 */
class WaxOff implements Runnable{
    private Car car;
    public WaxOff(Car car){
        this.car = car;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                System.out.println("WaxOff waitForWaxing");
                // 如果没有打蜡，就挂起抛光线程
                car.waitForWaxing();
                System.out.println("WaxOff waitForWaxing end");
                System.out.println("Wax Off!");
                TimeUnit.SECONDS.sleep(1);
                car.buffered();
            }
        }catch (InterruptedException e){
            System.out.println("waxOn interrupted");
        }
        System.out.println("WaxOff task ended");
    }
}

public class WaxOnMatic {
    public static void main(String...args) throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Car car = new Car();
        executorService.execute(new WaxOff(car));
        executorService.execute(new WaxOn(car));
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}
