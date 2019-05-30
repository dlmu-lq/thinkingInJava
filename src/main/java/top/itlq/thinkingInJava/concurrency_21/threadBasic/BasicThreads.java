package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import top.itlq.thinkingInJava.concurrency_21.threadBasic.LiftOff;

/**
 * 通过Thread创建线程执行任务
 */
public class BasicThreads {
    public static void main(String...args){
        for(int i=0;i<5;i++){
            new Thread(new LiftOff()).start();
        }
        System.out.println("main thread");
    }
}
