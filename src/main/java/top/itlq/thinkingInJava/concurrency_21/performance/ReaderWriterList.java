package top.itlq.thinkingInJava.concurrency_21.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock可以同时有个多个读取锁，但只能获取一个写锁，且写锁执行时，不能读取
 */

class ReaderWriterListTest{
    private int readNums;
    private int writeNums;
    private static final int SIZE = 100;
    private Random random = new Random();
    private ReaderWriterList<Integer> readerWriterList = new ReaderWriterList<>(SIZE, 0);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public ReaderWriterListTest(int readNums, int writeNums){
        this.readNums = readNums;
        this.writeNums = writeNums;
        runReadWrite();
    }
    private void runReadWrite(){
        for(int i=0;i<readNums;i++){
            executorService.execute(new Reader());
        }
        for(int i=0;i<writeNums;i++){
            executorService.execute(new Writer());
        }
        executorService.shutdown();
    }
    class Reader implements Runnable{
        private volatile int a;
        @Override
        public void run() {
            for(int i=0;i<100;i++){
                a = readerWriterList.get(random.nextInt(SIZE));
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Writer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<20;i++){
                readerWriterList.set(random.nextInt(SIZE),random.nextInt(1000));
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class ReaderWriterList<T> {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private List<T> list;
    public ReaderWriterList(int size, T initialValue){
        list = new ArrayList<>(Collections.nCopies(size, initialValue));
    }
    public T get(int index){
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            if(lock.getReadLockCount() > 1)
                System.out.println(lock.getReadLockCount());
            return list.get(index);
        }finally {
            readLock.unlock();
        }
    }
    public T set(int index, T value){
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try{
            return list.set(index, value);
        }finally {
            writeLock.unlock();
        }
    }
    public static void main(String...args){
        new ReaderWriterListTest(30, 1);
    }
}
