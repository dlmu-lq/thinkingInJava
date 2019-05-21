package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程停止时不“讲理” 非后台线程一旦停止，后台线程立即停止，不会之前必须执行的东西
 */
public class ADaemon implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("1");
            TimeUnit.MILLISECONDS.sleep(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally");
        }
    }

    public static void main(String...args){
        Thread thread = new Thread(new ADaemon());
        thread.setDaemon(true);
        thread.start();
    }
}
