package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.*;

/**
 * 使用BlockingQueue同步队列
 * 多线程对一批对象的三个步骤依次进行，提高效率；
 * 靠面包，抹黄油，涂果酱为三个任务线程，对应处理三个队列，三个队列间必须按照顺序进入；
 * 可考虑用三块烤面包涂上不同的东西，组成一个三明治，（存在不同的队列，组成三明治时会取多个队列）
 */

class Toast{
    private static int total = 0;
    private int id = ++total;
    private Status status = Status.DRY;
    public enum Status{
        DRY,BUTTERED,JAMMED
    }
    public void butter(){
        status = Status.BUTTERED;
    }
    public void jam(){
        status = Status.JAMMED;
    }
    public Status getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }
    @Override
    public String toString(){
        return "Toast #" + id + ": " + status;
    }
}

class Toaster implements Runnable{
    private BlockingQueue<Toast> toastQueue;
    public Toaster(BlockingQueue toastQueue){
        this.toastQueue = toastQueue;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(300);
                toastQueue.put(new Toast());
            }
        }catch (InterruptedException e){
            System.out.println("toaster interrupted");
        }
    }
}

class Butterer implements Runnable{
    private BlockingQueue<Toast> toastQueue;
    private BlockingQueue<Toast> butteredQueue;
    public Butterer(BlockingQueue<Toast> toastQueue, BlockingQueue<Toast> butteredQueue){
        this.toastQueue = toastQueue;
        this.butteredQueue = butteredQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = toastQueue.take();
                TimeUnit.MILLISECONDS.sleep(300);
                toast.butter();
                butteredQueue.put(toast);
            }
        }catch (InterruptedException e){
            System.out.println("butterer interrupted");
        }
    }
}

class Jammer implements Runnable{
    private BlockingQueue<Toast> butteredQueue;
    private BlockingQueue<Toast> jammedQueue;
    public Jammer(BlockingQueue<Toast> butteredQueue, BlockingQueue<Toast> jammedQueue){
        this.butteredQueue = butteredQueue;
        this.jammedQueue = jammedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = butteredQueue.take();
                TimeUnit.MILLISECONDS.sleep(300);
                toast.jam();
                jammedQueue.put(toast);
            }
        }catch (InterruptedException e){
            System.out.println("jammer interrupted");
        }
    }
}

class FatGuy implements Runnable{
    private BlockingQueue<Toast> jammedQueue;
    private int count;
    public FatGuy(BlockingQueue<Toast> jammedQueue){
        this.jammedQueue = jammedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = jammedQueue.take();
                if(toast.getId() != ++count || toast.getStatus() != Toast.Status.JAMMED){
                    System.out.println("Bad Error!");
                }else{
                    System.out.println("chomp !");
                }
            }
        }catch (InterruptedException e){
            System.out.println("fatGuy interrupted");
        }
    }
}

public class ToastOMatic {
    public static void main(String...args) throws Exception{
        BlockingQueue<Toast> toastQueue = new LinkedBlockingDeque<>(),
                butteredQueue = new LinkedBlockingDeque<>(),
                jammedQueue = new LinkedBlockingDeque<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(toastQueue));
        exec.execute(new Butterer(toastQueue, butteredQueue));
        exec.execute(new Jammer(butteredQueue, jammedQueue));
        exec.execute(new FatGuy(jammedQueue));
        // 每块面包需要 3个300ms,多线程工作，3s能烤 8块;
        TimeUnit.MILLISECONDS.sleep(3200);
        exec.shutdownNow();
    }
}
