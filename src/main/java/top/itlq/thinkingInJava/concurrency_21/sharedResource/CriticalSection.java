package top.itlq.thinkingInJava.concurrency_21.sharedResource;

/**
 * 将一个线程不安全的类封装为线程安全的（控制其所有访问）；
 * 在决定同步方法还是代码块时，考虑那些需要防护，那些不需要防护，
 * 如果存在不需要防护的耗时操作则可以放在同步代码块之外，使对象加锁的时间减短
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程不安全类
 */
class Pair{

    private int x;
    private int y;

    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pair(){
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX(){
        x++;
    }

    public void incrementY(){
        y++;
    }

    public void checkState(){
        if(x != y)
            throw new RuntimeException("Pair 无效");
    }
}

/**
 * 保存一个递增的Pair序列，并封装Pair，使其线程安全；
 */
abstract class PairManager{
    protected Pair pair = new Pair();
    // 同步的list
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
    // 记录检查线程访问，使用原子类
    AtomicInteger checkCounter = new AtomicInteger(0);

    public synchronized Pair getPair(){
        return new Pair(pair.getX(), pair.getY());
    }

    protected void store(Pair p){
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 需要控制同步，在子类中使用不同方法同步
    public abstract void increment();

    @Override
    public String toString(){
        return "x: " + pair.getX() + ", y: " + pair.getY() + ",checkCounter: " + checkCounter;
    }
}

/**
 * 同步方法控制的PairManager实现类
 */
class PairManager1 extends PairManager{
    public synchronized void increment(){
        pair.incrementX();
        pair.incrementY();
        store(getPair());
    }
}

/**
 * 同步代码块，将不需要锁对象的代码放到外部以减少锁对象的时间，控制的PairManager实现类
 */
class PairManager2 extends PairManager{
    public void increment(){
        // 定义一个临时对象，避免非同步的保存时对象已改变
        Pair temp;
        synchronized (this){
            pair.incrementX();
            pair.incrementY();
            temp = getPair();
        }
        store(getPair());
    }
}

class PairManagerTask implements Runnable{

    private PairManager pm;

    public PairManagerTask(PairManager pairManager){
        pm = pairManager;
    }

    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }
}

class PairCheck implements Runnable{

    private PairManager pm;

    public PairCheck(PairManager pm){
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true){
            // 将获取锁
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

public class CriticalSection {
    public static void main(String...args) throws Exception{
        PairManager pairManager1 = new PairManager1(),
                pairManager2 = new PairManager2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new PairManagerTask(pairManager1));
        executorService.execute(new PairManagerTask(pairManager2));
        executorService.execute(new PairCheck(pairManager1));
        executorService.execute(new PairCheck(pairManager2));
        executorService.shutdown();
        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println("pm1: " + pairManager1 + "\r\n" + "pm2: " + pairManager2);
        System.exit(0);
    }
}
