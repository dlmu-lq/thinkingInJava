package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.TimeUnit;

/**
 * 相对创建任务类 Runnable  可以直接从 Thread 继承，覆盖 run 方法
 */
public class SimpleThread extends Thread{
    private int countDown = 5;
    public SimpleThread(String name){
        // 可以创建具名线程，通过getName()获得
        super(name);
        this.start();
    }
    @Override
    public void run(){
        try {
            while (true){
                System.out.println("#" + getName() + " " + countDown);
                if(--countDown == 0){
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String...args){
        for(int i=0;i<5;i++)
            new SimpleThread(Integer.toString(i));
    }
}
