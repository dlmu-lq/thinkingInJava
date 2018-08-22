package top.itlq.thinkingInJava.holdingObjects_11.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class TestLinkedList {
    @Test
    public void test1(){
        String[] strings1 = new String[]{"1呀","2呀","3呀"};
        LinkedList<String> stringList1 = new LinkedList<String>(Arrays.asList(strings1));
        //访问第一个元素，空容器，抛异常；
        System.out.println("stringList1.getFirst() " + stringList1.getFirst());
        System.out.println("stringList1.element() " + stringList1.element());
        // 访问第一个元素，空容器，返回null
        System.out.println("stringList1.peek() " + stringList1.peek());
        System.out.println(stringList1);

        // 移除第一个元素，空容器，抛异常；
        System.out.println("stringList1.remove() " + stringList1.remove());
        System.out.println("stringList1.removeFirst() " + stringList1.removeFirst());
        //移除第一个元素，空容器，返回null
        System.out.println("stringList1.poll() " + stringList1.poll());
        System.out.println(stringList1);
        // 另外 removeLast，addFirst,addLast与add()
        stringList1.addAll(Arrays.asList(strings1));
        stringList1.add("？呀");
        stringList1.addFirst("0呀");
        stringList1.addLast("l呀");
        System.out.println(stringList1);
    }
}
