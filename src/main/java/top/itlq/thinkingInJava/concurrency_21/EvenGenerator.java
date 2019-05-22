package top.itlq.thinkingInJava.concurrency_21;

/**
 * 一个看起来正确的（在多线程中会失败）的偶数生成器
 */
public class EvenGenerator extends IntGenerator {
    private int currentValue = 0;
    @Override
    public int next(){
        currentValue++; // 多个线程情况下，此操作执行完可能被别的线程执行即产生奇数
        // 单纯的递增操作没有原子性，也会出现问题
        ++currentValue;
//        currentValue += 2; // 此操作能够保证是奇数，但由于没有原子性，最终结果与执行的次数可能不匹配；
        return currentValue;
    }

    public static void main(String...args){
        EvenChecker.test(new EvenGenerator(), 10);
    }
}
