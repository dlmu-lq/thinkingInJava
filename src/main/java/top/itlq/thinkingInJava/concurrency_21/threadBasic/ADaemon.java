package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程停止时不“讲理” 非后台线程一旦停止，后台线程立即停止，不会之前必须执行的东西
 */
public class ADaemon implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("1");
            new Thread(new ChildRun()).start();
            TimeUnit.MILLISECONDS.sleep(50);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally");
        }
    }

    class ChildRun implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " " + this);
            }
        }
    }

    public static void main(String...args) throws Exception{
        Thread thread = new Thread(new ADaemon());
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(20);
    }
}
