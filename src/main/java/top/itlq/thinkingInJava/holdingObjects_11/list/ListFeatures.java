/**
 * List的一些方法，迭代，ArrayList与LinkedList
 *
 */
package top.itlq.thinkingInJava.holdingObjects_11.list;

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
        List<Pet> petList = petArrayList();
//        打印
        System.out.println("1:"+integerList);
//        添加
        integerList.add(6);
        stringList.add("6");
        System.out.println("2:"+integerList);
//        判断是否包含
        System.out.println(integerList.contains(6));
//        删除
//        integerList.remove(6);//运行报错，对象为remove等可以接受索引也可接受对象时，优先找索引
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
        Collections.shuffle(subStringList1);
        System.out.println("sublist顺序改变后原list："+stringList);
        subStringList1.add("insert sublist");
        System.out.println("sublist添加元素后原list："+stringList);
//        stringList.add("insert 原list");后面访问即报错，不能进行扩展大小的结构性修改,但sublist进行修改
        stringList.set(1,"新2");
        System.out.println("原list大小改变后sublist会出异常，值变化后，sublist："+subStringList1);
        List<String> testContainsAll = Arrays.asList("4","5"); //新String对象不是原对象，但containsAl使用equals，所以下面包含为true
        System.out.println("包含String值相同(对象不同)的list："+stringList.containsAll(testContainsAll));//
        List<Pet> petContainsAll = Arrays.asList(new Pet(),new Pet()); //新对象不是原对象，所以下面不能包含
        System.out.println("包含pet属性相同(对象不同)的list："+petList.containsAll(petContainsAll));//
        List<String> shuffleOriObjList = new ArrayList<String>(); //乱序添加原list内对象，下面包含为true
        shuffleOriObjList.add(stringList.get(1));
        shuffleOriObjList.add(stringList.get(0));
        System.out.println("包含原有list内乱序对象的list:"+stringList.containsAll(shuffleOriObjList));
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
        Collections.addAll(copy,stringList.toArray(new String[]{}));
        System.out.println("copy方法，toArray泛型使用函数中加空对象说明类型："+copy);
        copy = new ArrayList<String>(stringList);
        System.out.println("new ArrayList(objects)copy方法："+copy);
//        addAll方法
        petList.addAll(Arrays.asList(new Pet(),new Pet()));
        System.out.println(petList);

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

    ArrayList<Pet> petArrayList(){
        Pet[] pets={new Pet(),new Pet(),new Pet()};
        ArrayList<Pet> re = new ArrayList<Pet>();
        Collections.addAll(re, pets);
        return re;
    }
}

class Pet{
    int age = 5;
}