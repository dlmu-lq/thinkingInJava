package top.itlq.thinkingInJava.concurrency_21;

/**
 * 修改多线程情况下错误的偶数生成器，将生成方法改为同步
 */
public class SynchronizedEvenGenerator extends IntGenerator {
    private int currentValue = 0;
    @Override
    public synchronized int next(){
        currentValue++;
        Thread.yield(); // 如果将发生错误，那么会加快发生，但此处不会发生
        ++currentValue;
        return currentValue;
    }

    public static void main(String...args){
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}
