package top.itlq.thinkingInJava.chapter12_collections.collectionBase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestIteration {
    //iteractor 遍历 list
    @Test
    public void testIterator(){
//        List<String> stringList = Arrays.asList(new String[]{"1","2","3"}); //由数组创建的list不能改变大小
        String [] strings = new String[]{"1","2","3"};
        List<String> stringList = new ArrayList<String>(Arrays.asList(strings)); //new或addAll 创建新的能改变的list
        Iterator<String> stringIterator = stringList.iterator();
        while (stringIterator.hasNext()){
            String o = stringIterator.next();
            System.out.println(o);
            if(o.equals("2")){
                stringIterator.remove();
            }
        }
        System.out.println(stringList);
    }
    //foreach遍历list，只是循环不改变Collection（remove）,此方法较简便，原理相似，数组类list此方法较快，链表类迭代器较好
    @Test
    public void testForeach(){
        List<String> stringList = Arrays.asList(new String[]{"1","2","3"});
        for(String o:stringList){
            System.out.println(o);
        }
    }
}
