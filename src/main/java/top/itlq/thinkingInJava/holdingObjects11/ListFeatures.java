/**
 * List的一些方法，迭代，ArrayList与LinkedList
 *
 */
package top.itlq.thinkingInJava.holdingObjects11;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListFeatures {
//    List的一些方法
    @Test
    public void test1(){
        List<Integer> integerList=intArrayList();
        List<String> stringList=stringArrayList();
//        打印
        System.out.println("1:"+integerList);
//        添加
        integerList.add(6);
        stringList.add("6");
        System.out.println("2:"+integerList);
//        判断是否包含
        System.out.println(integerList.contains(6));
//        删除
//        list1.remove(6);//运行报错，对象为remove等可以接受索引也可接受对象时，优先找索引
        integerList.remove(5);
        System.out.println("3:"+integerList);

        System.out.println("before remove Object:"+stringList);
        stringList.remove("6");
        System.out.println("after remove Object:"+stringList);
//        indexOf(Object)
        System.out.println("indexOf:"+integerList.indexOf(5));
//        在索引处添加
        stringList.add(2,"newOne");
        System.out.println("索引2处添加"+stringList);
//        subList 截取（0，size）左闭右开
        List<String> subStringList1 = stringList.subList(0,3);
        System.out.println("subList(0,3):"+subStringList1);
//        是否为子集
        System.out.println("contains all subList:"+stringList.containsAll(subStringList1));
        System.out.println("contains self:"+stringList.containsAll(stringList));
//        sort，shuffle
        Collections.shuffle(subStringList1);
        System.out.println("after shuffle:"+subStringList1);
        Collections.sort(subStringList1);
        System.out.println("after sort:"+subStringList1);
//        retainAll 交集
        System.out.println("stringList:"+stringList);
        System.out.println("交集："+stringList.retainAll(subStringList1)+"  "+stringList);//改变集合返回Boolean
//        copy
        List<String> copy=new ArrayList<String>();
//        Collections.addAll(copy,stringList.toArray());


//
    }

    ArrayList<Integer> intArrayList(){
        Integer[] ints={1,2,3,4,5};
        ArrayList<Integer> re = new ArrayList<Integer>();
        Collections.addAll(re, ints);
        return re;
    }

    ArrayList<String> stringArrayList(){
        String[] ints={"1","2","3","4","5"};
        ArrayList<String> re = new ArrayList<String>();
        Collections.addAll(re, ints);
        return re;
    }


}
