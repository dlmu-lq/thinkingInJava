package top.itlq.thinkingInJava.concurrency_21.perfomance;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于测试不同互斥同步方法的性能：
 *     并发多个任务；
 *     需要不仅有读操作，也有写操作；
 *     尽量不让编译器进行优化；
 *     需要随机产生的数在运行前先放在数组中；
 *
 * 一个测试任务抽象类 Accumulator ，将实际的读操作、写操作（需要同步的方法）隐藏在子类中实现；
 *     包含测试读操作、写操作方法的任务；
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
    private final int taskNums = 5;
    private ExecutorService executorService = Executors.newFixedThreadPool(taskNums * 2);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(taskNums * 2 + 1);
    protected int value;
    protected int index;
    protected static int size = 100000;
    protected static final int[] values = new int[size];
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
        @Override
        public void run() {
            try{
                int a;
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
        System.out.println(this.id + ": " + (System.nanoTime() - start));
    }
    public abstract int read();
    public abstract void modify();
}

class BaseLine extends Accumulator{
    {
        id = "baseline";
    }
    @Override
    public int read() {
        return value;
    }

    @Override
    public void modify() {
        value = values[index++];
        if(index == size)
            index = 0;
    }
}

class SynchronousAccumulator extends Accumulator{
    {
        id = "synchronousAccumulator";
    }
    @Override
    public synchronized int read() {
        return value;
    }
    @Override
    public synchronized void modify() {
        value = values[index++];
        if(index == size)
            index = 0;
    }
}

public class SynchronizationComparisons {
    public static void main(String...args) throws Exception{
        new BaseLine().timeTest();
        new SynchronousAccumulator().timeTest();
    }
}
