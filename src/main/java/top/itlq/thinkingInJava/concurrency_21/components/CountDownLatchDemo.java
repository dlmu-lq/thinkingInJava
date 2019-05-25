package top.itlq.thinkingInJava.concurrency_21.components;

import java.util.concurrent.CountDownLatch;

/**
 * 将一个任务拆分为n，创建一个初始计数值为n的CountDownLatch，每个任务完成后，会调用CountDownLatch.countDown()
 * 需要等待的任务调用相同CountDownLatch对象的await()
 */
public class CountDownLatchDemo {
    // 一个调用countDown()的被等待任务；
    CountDownLatch countDownLatch;
    // 一个调用await()的等待任务；
}
