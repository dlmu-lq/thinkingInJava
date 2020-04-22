package top.itlq.thinkingInJava.concurrency_21.threadCooperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义
 */
public class FixedSizeThreadPool {

    /**
     * 任务队列，阻塞队列
     */
    private BlockingQueue<Runnable> blockingQueue;

    /**
     * 线程容器
     */
    private List<Thread> workers;

    public FixedSizeThreadPool(int poolSize, int taskSize) {
        this.blockingQueue = new LinkedBlockingQueue<>(taskSize);
        this.workers = Collections.synchronizedList(new ArrayList<>(poolSize));
        for(int i=0;i<poolSize;i++){
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    /**
     * 线程
     */
    public static class Worker extends Thread{

        private FixedSizeThreadPool pool;

        public Worker(FixedSizeThreadPool pool) {
            this.pool = pool;
        }


        @Override
        public void run(){
            while (true){
                Runnable task = null;
                try {
                    task = pool.blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(task != null){
                    task.run();
                    System.out.println("线程：" + Thread.currentThread().getName() + "执行完成");
                }
            }
        }
    }

    public boolean submit(Runnable task){
        return blockingQueue.offer(task);
    }

    public static void main(String[] args) {
        FixedSizeThreadPool fixedSizeThreadPool = new FixedSizeThreadPool(3, 6);
        for (int i=0;i<6;i++){
            fixedSizeThreadPool.submit(()->{
                
            });
        }
    }
}
