package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.TimeUnit;

/**
 * 通过实现runnable接口，在管理类内部创建线程
 * 在构造器中实现线程可能会有问题，因为另一个任务可能会在构造器结束之前开始执行，能够访问处于不稳定状态的对象
 *     所以优选 Executor 而不是显示创建Thread对象的另一个原因
 */
public class SelfManaged implements Runnable{
    private int countDown = 5;

    public SelfManaged(){
        new Thread(this).start();
    }

    @Override
    public String toString(){
        return Thread.currentThread().getName() + "(" + countDown + ")";
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(this);
                if(--countDown <= 0){
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
            new SelfManaged();
    }
}
