package top.itlq.thinkingInJava.containers_17.collections;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * 优先级对列的升序出 直接打印队列 一个个出队列
 */
public class SInteger implements Comparable{
    private Integer i;
    public SInteger(){
        i = new Random().nextInt(100);
    }

    @Override
    public int compareTo(Object o) {
        return o instanceof SInteger
                ? ((SInteger) o).i > i
                    ? -1
                    : ((SInteger) o).i.equals(i)
                        ? 0
                        : 1
                : -1;
    }

    @Override
    public String toString(){
        return String.valueOf(i);
    }

    public static void main(String...args){
        Queue queue = new PriorityQueue<SInteger>();
        for(int i=0;i<100;i++){
            queue.offer(new SInteger());
        }
        // 直接打印队列可能是无序的
        System.out.println(queue);
        // 但一个个取出时是有序的
        while (queue.peek() != null){
            System.out.print(queue.remove() + " ");
        }
    }
}
