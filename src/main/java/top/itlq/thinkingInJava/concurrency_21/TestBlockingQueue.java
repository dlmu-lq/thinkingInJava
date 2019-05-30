package top.itlq.thinkingInJava.concurrency_21;

import top.itlq.thinkingInJava.concurrency_21.threadBasic.LiftOff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

/**
 * 使用同步队列存储任务，有一个任务封装了这个同步队列，它的运行即去取出队列任务运行，当队列为空时，挂起；新增时重新运行；
 */

class LiftOffRunner implements Runnable{
    private BlockingQueue<LiftOff> blockingQueue;
    public LiftOffRunner(BlockingQueue<LiftOff> blockingQueue){
        this.blockingQueue = blockingQueue;
    }
    public void add(LiftOff liftOff){
        try {
            // 添加至队列尾，没有空间时挂起等待
            blockingQueue.put(liftOff);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                // 取出队列首，队列为空时挂起等待
                LiftOff liftOff = blockingQueue.take();
                liftOff.run();
            }
        } catch (InterruptedException e) {
            System.out.println("LiftOffRunner interrupted");
        }
    }
}
public class TestBlockingQueue {
    static void test(String message, BlockingQueue<LiftOff> blockingQueue){
        LiftOffRunner liftOffRunner = new LiftOffRunner(blockingQueue);
        Thread thread = new Thread(liftOffRunner);
        thread.start();
        for(int i=0;i<5;i++){
            liftOffRunner.add(new LiftOff());
        }
        System.out.println("Press 'Enter'(" + message + ")");
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println("finish");
    }
    public static void main(String...args){
        test("LinkedBlockingQueue",new LinkedBlockingDeque<>());
        test("ArrayBlockingQueue",new ArrayBlockingQueue<>(3));
        test("SynchronousQueue",new SynchronousQueue<>());
    }
}
