package top.itlq.thinkingInJava.concurrency_21.performance;

import org.apache.poi.ss.formula.functions.Count;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于测试不同互斥同步方法的性能：
 *     并发多个任务；
 *     需要不仅有读操作，也有写操作；
 *     尽量不让编译器进行优化；
 *     需要随机产生的数在运行前先放在数组中；
 *
 * 一个测试任务抽象类 Accumulator ，将实际的读操作、写操作（需要同步的方法）隐藏在子类中实现；
 *     包含测试读操作、写操作方法的任务，内部类任务可以并发操作Accumulator的域；
 *     包含一个FixedThreadPoolExecutor，固定任务数量，以减少对测试的影响；
 *     包含，循环执行次数，同时启动的任务次数，用于监听任务全部完成的 CyclicBarrier；
 *     包含一个方法，用于根据这些配置，启动这些任务进行循环，并计算出最终时间，输出各个同步方式所用时间的对比；
 * Accumulator的 不同步，使用synchronized同步，使用Lock同步，使用原子类同步的实现类；
 * public类，循环测试几次，每次增加循环次数；
 *
 * 多步操作的原子类已经不能保证并发正确使用，这里只为性能测试
 * 初始几次运行，循环次数少时，synchronized的性能相对lock和atomic较好，
 *     但当达到一定大量的循环次数时，synchronized相对的互斥同步效率将明显降低，而Lock和atomic的效率变化不大；
 *     不一定因此要选择Lock和atomic而不选择synchronized，因为并发程序不仅要考虑互斥同步效率，有时方法内部运行耗时更大；
 *     synchronized可读性更高，因此更应该使用，在性能需要调优时再考虑使用Lock，Atomic(特定条件)
 */

abstract class Accumulator{
    protected String id = "error";
    public static int cycles = 50000;
    private static final int taskNums = 5;
    static ExecutorService executorService = Executors.newFixedThreadPool(taskNums * 2);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(taskNums * 2 + 1);
    protected long value;
    protected int index;
    protected static int size = 100000;
    protected static final int[] values = new int[size];
    private long duration;
    static {
        Random random = new Random();
        for(int i=0;i<size;i++){
            values[i] = random.nextInt();
        }
    }
    class Modifier implements Runnable{
        @Override
        public void run() {
            try {
                for(int i=0;i<cycles;i++){
                    modify();
                }
                cyclicBarrier.await();
            }catch (InterruptedException e){
                System.out.println("modifier interrupted");
            }catch (BrokenBarrierException e){
                System.out.println("modifier broken");
            }
        }
    }
    class Reader implements Runnable{
        private volatile long a;
        @Override
        public void run() {
            try{
                for(int i=0;i<cycles;i++){
                    a = read();
                }
                cyclicBarrier.await();
            }catch (InterruptedException e){
                System.out.println("modifier interrupted");
            }catch (BrokenBarrierException e){
                System.out.println("modifier broken");
            }
        }
    }
    public void timeTest() throws InterruptedException, BrokenBarrierException{
        long start = System.nanoTime();
        for(int i=0;i<taskNums;i++){
            executorService.execute(new Modifier());
            executorService.execute(new Reader());
        }
        cyclicBarrier.await();
        duration = System.nanoTime() - start;
        System.out.printf("%-12s : %15d\n", this.id, duration);
    }
    public static void report(Accumulator accumulator1, Accumulator accumulator2){
        System.out.printf("%-22s : %5.2f\n",
                accumulator1 + "/" + accumulator2,
                ((double)accumulator1.duration / (double)accumulator2.duration));
    }
    public abstract long read();
    public abstract void modify();
    @Override
    public String toString(){
        return id;
    }
}

class BaseLine extends Accumulator{
    {
        id = "baseline";
    }
    @Override
    public long read() {
        return value;
    }

    @Override
    public void modify() {
        if(++index >= size)
            index = 0;
        value += values[index];
    }
}

class SynchronousAccumulator extends Accumulator{
    {
        id = "synchronized";
    }
    @Override
    public synchronized long read() {
        return value;
    }
    @Override
    public synchronized void modify() {
        value += values[index++];
        if(index == size)
            index = 0;
    }
}

class LockAccumulator extends Accumulator{
    private Lock lock = new ReentrantLock();
    {
        id = "lock";
    }
    @Override
    public long read() {
        lock.lock();
        try {
            return value;
        }finally {
            lock.unlock();
        }
    }
    @Override
    public void modify() {
        lock.lock();
        try{
            value += values[index++];
            if(index == size)
                index = 0;
        }finally {
            lock.unlock();
        }
    }
}

class AtomicAccumulator extends Accumulator{
    protected AtomicLong value = new AtomicLong(0);
    protected AtomicInteger index = new AtomicInteger(0);
    {
        id = "atomic";
    }
    @Override
    public long read() {
        return value.get();
    }
    @Override
    public void modify() {
        // 复合操作已不能保证原子性，只为测试效果
        value.set(values[index.getAndAdd(1)]);
        if(index.get() == size)
            index.set(0);
    }
}

public class SynchronizationComparisons {
    static BaseLine baseLine = new BaseLine();
    static SynchronousAccumulator synchronousAccumulator = new SynchronousAccumulator();
    static LockAccumulator lockAccumulator = new LockAccumulator();
    static AtomicAccumulator atomicAccumulator = new AtomicAccumulator();
    static void test() throws Exception{
        System.out.println("============================");
        System.out.printf("%1$-12s : %2$15d\n", "cycles", Accumulator.cycles);
//        baseLine.timeTest();
//        synchronousAccumulator.timeTest();
//        lockAccumulator.timeTest();
//        atomicAccumulator.timeTest();
//        Accumulator.report(synchronousAccumulator, baseLine);
//        Accumulator.report(lockAccumulator, baseLine);
//        Accumulator.report(atomicAccumulator, baseLine);
//        Accumulator.report(synchronousAccumulator, lockAccumulator);
//        Accumulator.report(synchronousAccumulator, atomicAccumulator);
//        Accumulator.report(lockAccumulator, atomicAccumulator);
    }
    public static void main(String...args) throws Exception{
        baseLine.timeTest();
        for(int i=0;i<1;i++){
            test();
            Accumulator.cycles *= 2;
        }
        // 不关闭，主线程不会停止
        Accumulator.executorService.shutdown();
    }
}
