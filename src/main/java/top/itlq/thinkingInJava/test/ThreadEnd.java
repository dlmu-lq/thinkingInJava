package top.itlq.thinkingInJava.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable{

    @Override
    public void run() {
        System.out.println(1);
    }
}
public class ThreadEnd {
    public static void main(String...args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Task());
//        executorService.shutdown(); //使用ExecutorService，不 shutdown 主线程不会结束

//        new Thread(new Task()).start();
    }
}
