package top.itlq.thinkingInJava.concurrency_21.performance;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多个线程操作对象时，可使用乐观锁；
 * 当模型有性能需求且可以接受一些失败操作时使用
 */
public class FastSimulation {
    private static final int GENES_SIZE = 100000;
    private static final int GENE_LENGTH = 30;
    private static final int EVOLUTE_NUMS = 30;
    private static AtomicInteger[][] GENES = new AtomicInteger[GENES_SIZE][GENE_LENGTH];
    private static Random random = new Random();
    static {
        for(int i=0;i<GENES_SIZE;i++){
            GENES[i] = new AtomicInteger[GENE_LENGTH];
            for(int ii=0;ii<GENE_LENGTH;ii++){
                GENES[i][ii] = new AtomicInteger(random.nextInt(1000));
            }
        }
    }
    static class Evolute implements Runnable{
        @Override
        public void run() {
            int index = random.nextInt(GENES_SIZE);
            int previous = index - 1;
            if(index < 0)
                previous = GENE_LENGTH - 1;
            int next = index + 1;
            if(index >= GENE_LENGTH){
                next = 0;
            }
            for(int i=0;i<GENE_LENGTH;i++){
                int oldValue = GENES[index][i].get();
                int newValue = oldValue + GENES[previous][i].get() + GENES[next][i].get();
                newValue /= 3;
                if(!GENES[index][i].compareAndSet(oldValue, newValue)){
                    System.err.println("modified while this computing");
                }
                System.out.println("oldValue: " + oldValue + "; newValue: " + newValue);
            }
        }
    }
    public static void main(String...args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<EVOLUTE_NUMS;i++){
            executorService.execute(new Evolute());
        }
        executorService.shutdown();
    }
}
