package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.TimeUnit;

/**
 * 执行任务过程中的中断
 */

class NeedsCleanup{

    private int id;

    public NeedsCleanup(int id){
        this.id = id;
    }

    public void cleanup(){
        System.out.println("cleaning up #" + id);
    }
}

class Blocked3 implements Runnable{
    // 仅仅防止编译器优化，在这里不涉及线程安全问题
    private volatile double d;
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                NeedsCleanup n1 = new NeedsCleanup(1);
                try{
                    System.out.println("Sleeping...");
                    TimeUnit.SECONDS.sleep(1);
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        System.out.println("Calculating...");
                        for(int i=0;i<2500000;i++)
                            d = d + (Math.PI + Math.E) / d;
                    }finally {
                        n2.cleanup();
                    }
                }finally {
                    // 即使由于阻塞中断抛出异常，仍然可以执行其finally子句
                    n1.cleanup();
                }
            }
            System.out.println("interrupted when running");
        } catch (InterruptedException e) {
            System.out.println("interrupted when sleeping");
        }
    }
}


public class InterruptingIdiom {
    public static void main(String...args) throws Exception{
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(200);
//        TimeUnit.MILLISECONDS.sleep(1010);
        thread.interrupt();
    }
}
