package top.itlq.thinkingInJava.concurrency_21.performance;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个用来测试各种容器的读写性能，以测试免锁容器和容器加锁两种不同方式的并发读写性能；
 *     读任务、写任务数量；测试次数；一个读/写任务调用读/写循环次数；容器尺寸
 *     持有容器的域，产生容器的抽象方法；
 *     开始测试的入口方法：计数，循环测试，每一个测试中，调用读写方法，阻塞计时；
 *
 *     一个用于测试计时的任务内部抽象类，需要实现运行方法，输出方法，保存运行时间；
 *     一个用于初始化并输出表头的方法，用于初始化可更改的次数，测试次数，循环次数，容器尺寸;
 *     抽象方法，用来根据同时读取、写入任务数添加启动线程。
 */
abstract class Tester<C> {
    static int testReps = 10;
    static int testCycles = 1000;
    static int testSize = 1000;
    protected int readNums;
    protected int writeNums;
    protected C testContainer;
    protected CountDownLatch countDownLatch;
    private final String id; // 实现类覆盖
    protected long writeTime;
    protected long readTime;
    protected long readResult;
    protected Integer[] writeData = new Integer[testSize];
    protected static ExecutorService executorService = Executors.newCachedThreadPool();

    public Tester(String id, int readNums, int writeNums) {
        this.id = id;
        this.readNums = readNums;
        this.writeNums = writeNums;
        Random random = new Random();
        for(int i=0;i<testSize;i++){
            writeData[i] = random.nextInt();
        }
        for (int i = 0; i < testReps; i++) {
            runTest();
        }
    }
    public void runTest() {
        countDownLatch = new CountDownLatch(readNums + writeNums);
        testContainer = containerInitializer();
        writeTime = 0;
        readTime = 0;
        startReadersAndWriters();
        try {
            countDownLatch.await();
        }catch (InterruptedException e){
            System.out.println("runTest await() interrupted");
        }
        System.out.printf("%-25s%20d%20d\n", id, readTime, writeTime);
    }
    public static void initMain(){
        System.out.printf("%-25s%20s%20s\n", "Type", "readTime", "writeTime");
    }

    public abstract C containerInitializer();
    public abstract void startReadersAndWriters();

    // 给Reader，Writer的通用部分提出
    abstract class TestTask implements Runnable{
        public abstract void test();
        public abstract void putResults();
        long duration;
        @Override
        public void run(){
            long start = System.nanoTime();
            test();
            duration = System.nanoTime() - start;
            synchronized (Tester.this){
                putResults();
            }
            countDownLatch.countDown();
        }
    }
}
