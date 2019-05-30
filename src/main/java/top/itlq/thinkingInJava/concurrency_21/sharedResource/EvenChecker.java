package top.itlq.thinkingInJava.concurrency_21.sharedResource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {

    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator generator, int ident){
        this.generator = generator;
        this.id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()){
            int value = generator.next();
            if(value % 2 != 0){
                System.out.println(value + "not even");
                generator.cancel(); // 会关闭所有的 EvenChecker线程
            }
        }
    }

    // 测试方法
    public static void test(IntGenerator g, int count){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<count;i++){
            executorService.execute(new EvenChecker(g, i));
        }
        executorService.shutdown();
    }

    // 重载带默认次数的测试方法
    public static void test(IntGenerator g){
        test(g, 10);
    }
}
