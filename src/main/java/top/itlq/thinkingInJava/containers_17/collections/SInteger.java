package top.itlq.thinkingInJava.containers_17.collections;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

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
        Queue queue = new PriorityQueue();
        for(int i=0;i<100;i++){
            queue.offer(new SInteger());
        }
        System.out.println(queue);
    }
}
