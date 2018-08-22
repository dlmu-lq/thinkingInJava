/**
 * 优先级队列
 */
package top.itlq.thinkingInJava.holdingObjects_11.queue;

import org.junit.Test;

import java.util.*;

public class TestPriorityQueue {
    static void printQ(Queue queue){
        while (!queue.isEmpty()){
            System.out.print(queue.poll());
        }
    }
    /**
     * PriorityQueue的基本方法特点
     */
    @Test
    public void test1(){
        Queue<Integer> integers = new PriorityQueue<Integer>();
        integers.addAll(Arrays.asList(5,3,2,4,1,2,1));
        integers.offer(5);
        integers.offer(3);
        integers.offer(2);
        integers.offer(4);
        integers.offer(1);
        integers.offer(2);
        integers.offer(1);
        System.out.println(integers); // 直接打印未排序
        Queue<Integer> reverseIntegers = new PriorityQueue<Integer>(Collections.<Integer>reverseOrder());
        reverseIntegers.addAll(integers);
        printQ(integers);
        System.out.println();
        printQ(reverseIntegers);
    }

    /**
     *字符串队列
     */
    @Test
    public void test2(){
        Queue<String> strings = new PriorityQueue<>();
        strings.offer(" test");
        strings.offer("test");
        strings.offer("llll");
        printQ(strings);
    }

    /**
     * 自定义类
     */
    @Test
    public void test3(){
        Queue<Command> commands = new PriorityQueue<>();
        try {
            commands.offer(new Command("1"));
            printQ(commands);
            commands.offer(new Command("2"));
            commands.offer(new Command("3"));
            printQ(commands);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
