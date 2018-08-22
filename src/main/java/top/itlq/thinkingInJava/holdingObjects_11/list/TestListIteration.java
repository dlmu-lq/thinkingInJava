package top.itlq.thinkingInJava.holdingObjects_11.list;

import org.junit.Test;

import java.util.*;

public class TestListIteration {
    // ListIterator 的双向遍历
    @Test
    public void test1(){
        String[] strings1 = new String[]{"1","2","3"};
        String[] strings2 = new String[]{"6","5","4"};
        List<String> stringList1 = new ArrayList<String>(Arrays.asList(strings1));
        List<String> stringList2 = new ArrayList<String>(Arrays.asList(strings2));
        ListIterator<String> it2 = stringList2.listIterator(stringList2.size());
        while (it2.hasPrevious()){
            stringList1.add(it2.previous());
        }
        System.out.println(stringList1);
    }
}
