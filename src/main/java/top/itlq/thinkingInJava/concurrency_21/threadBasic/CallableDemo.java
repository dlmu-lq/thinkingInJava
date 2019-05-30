package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.*;

/**
 * 带返回参数的线程任务
 */
public class CallableDemo implements Callable<String> {
    @Override
    public String call() throws Exception{
        System.out.println("new thread start...");
        System.out.println("new thread sleeping...");
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("new thread awake");
        return "called";
    }

    public static void main(String...args) throws Exception{
        System.out.println("main thread start...");
        long startTime = System.currentTimeMillis();

        // 添加带返回值任务到新线程异步执行
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new CallableDemo());
        executorService.shutdown();

        // 休眠主线程
        System.out.println("main thread sleeping...");
        Thread.sleep(1000);
        System.out.println("main thread awake");

        // 查看新线程是否完成
        System.out.println(future.isDone());
        // 调用新线程返回结果，会阻塞主线程
        System.out.println(future.get());

        System.out.println("用时：" + (System.currentTimeMillis() - startTime));
    }
}
