package top.itlq.thinkingInJava.containers_17.collections;

import java.util.*;

/**
 * listIterator的一些操作
 */
public class ListIteratorOperations {
    /**
     * listIterator 的操作 add next remove set
     * @param list
     */
    static void iterManipulation(List<String> list){
        ListIterator<String> listIterator = list.listIterator();
//        listIterator.remove();//        set  remove 等操作均针对上一个访问的元素，在其之前需保证访问过元素，即next()
        listIterator.add("new"); // add 之后当前访问索引会加1以保证在add之前next()访问与之后next()访问一致，元素被插入当前位置
        System.out.println(list);
//        set  remove 等操作均针对上一个访问的元素，在其之前需保证访问过元素，即next()
        listIterator.next();
        listIterator.remove(); // remove之后位置不变
        System.out.println(list);
        //        set  remove 等操作均针对上一个访问的元素，在其之前需保证访问过元素，即next()
        listIterator.next();
        listIterator.set("new");
        System.out.println(list);
    }

    /**
     * 隔元素插入，add之后 游标会 + 1；
     * @param list
     */
    static void testPreviousInsert(List<String> list){
        ListIterator<String> listIterator = list.listIterator(list.size() - 1);
        listIterator.add("2.5");
        listIterator.previous();
        listIterator.previous();
        listIterator.add("1.5");
        System.out.println(list);
    }


    public static void main(String...args){
        iterManipulation(new ArrayList<>(Arrays.asList("1","2","3")));
        testPreviousInsert(new LinkedList<>(Arrays.asList("1","2","3")));
    }
}
