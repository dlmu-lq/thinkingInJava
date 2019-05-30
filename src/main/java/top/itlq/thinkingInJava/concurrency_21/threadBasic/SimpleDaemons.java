package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程的停止
 */
public class SimpleDaemons implements Runnable{

    @Override
    public void run() {
        try{
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(this);
            }
        }catch (InterruptedException e){
            System.out.println("interrupted");
        }
    }

    public static void main(String...args) throws Exception{
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new SimpleDaemons());
            thread.setDaemon(true);
            thread.start();
        }
        TimeUnit.MILLISECONDS.sleep(150);
    }
}
