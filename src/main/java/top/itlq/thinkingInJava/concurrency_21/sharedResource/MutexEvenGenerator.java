package top.itlq.thinkingInJava.concurrency_21.sharedResource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock的互斥锁偶数生成器
 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentValue = 0;

    private Lock lock = new ReentrantLock();

    @Override
    public int next(){
        lock.lock();
        try{
            currentValue++;
            ++currentValue;
            return currentValue;
        }finally {
            // 必然执行
            lock.unlock();
        }
    }

    public static void main(String...args){
        EvenChecker.test(new MutexEvenGenerator(), 10);
    }
}
