/**
 * Collection,Map类容器的基础操作与特征
 * 创建，与数组关系
 * 一些基础方法，打印
 */
package top.itlq.thinkingInJava.chapter12_collections.collectionBase;


import org.junit.Test;

import java.util.*;


public class CollectionFeatures{
//    Collection ArrayList
    public static void main(String[]args){
//
        Collection<Apple> apples=new ArrayList<Apple>();
        apples.add(new Apple());
        for(Apple a:apples){
            a.f();
        }
    }
//    Set
    @Test
    public void test1(){
        Set<Apple> apples=new HashSet<Apple>();
        Apple apple=new Apple();
//        Set元素不能重复
        apples.add(apple);
        apples.add(apple);
        for(Apple a:apples){
            a.f();
        }
    }
//    添加一组元素，Arrays,Collection,ArrayList
    @Test
    public void test2(){
        Integer[] integers={1,2};
        Collection<Integer> list1 = Arrays.asList(integers); //new ArrayList(Array);
//        list1.add(1);//报错，数组尺寸不能变；

//        没有下面的效率高
        Collection<Integer> list2 = new ArrayList<Integer>(Arrays.asList(integers));
        list2.add(2);

//        首选方式，运行更快,先创建空，再addAll
        Collection list3=new ArrayList();
//        添加添加一个数组内元素,一组element
        Collections.addAll(list3,new Integer[2]);
        Collections.addAll(list3,3,4,new Integer[2]);
        Collections.addAll(list3, Arrays.asList(1,2)); //添加一个Collection至Collection
        // Arrays.asList 接收一个容器，
        System.out.println("Arrays.asList(Arrays.asList(1,2,3)):" + Arrays.asList(Arrays.asList(1,2,3)));
        //
//        list3.addAll(1,2) //Collection.addAll()不能接受可变参数，只能接受一个Collection
        list3.addAll(list2);

        for(Object o:list3){
            System.out.println(o);
        }
    }

//    多层继承时的编译器
    @Test
    public void test3(){
//        Collection<SomeThing> list1=Arrays.asList(new Apple(),new Apple());//识别不出
        Collection<SomeThing> list2=Arrays.<SomeThing>asList(new Apple(),new Apple());//线索
        Collection<SomeThing> list3=new ArrayList<SomeThing>(Arrays.asList(new Apple(),new Apple()));


        Collection<SomeThing> list4=new ArrayList<SomeThing>();

        list4.addAll(Arrays.asList(new Apple[2])); //不行
//        Collections.addAll(list4,Arrays.asList(new SomeThing(),new SomeThing()))   //不行为啥？类型不对，编译器认为你添加一个Collection至Collection
        Collections.addAll(list4,new Apple[2]);
        Collections.addAll(list4,new Apple(),new Apple()); //它行

    }

//    List Set Map容器打印,Queue暂等
//    注意打印出来的顺序问题,
//    ArrayList,LinkedList按照插入顺序存储，前者随机访问较快，插入，删除较慢，后者LinkedList插入删除较快，访问较慢
//    对于Set，Map :HashMap,HashSet,不考虑顺序，只考虑速度      //map顺序是对于键来说
//    TreeMap，TreeSet ，按照比较结果存储顺序
//    LinkedHashMap,LinkedHashSet,考虑顺序，按照插入顺序排列；
    @Test
    public void test4(){
        System.out.println(fill(new ArrayList<String>()));//[String]
        System.out.println(fill(new LinkedList<String>()));

        System.out.println(fill(new HashSet<String>()));//[String]
        System.out.println(fill(new TreeSet<String>()));
        System.out.println(fill(new LinkedHashSet<String>()));

        System.out.println(fill(new HashMap<String, String>())); //{String=String}
        System.out.println(fill(new TreeMap<String, String>()));
        System.out.println(fill(new LinkedHashMap<String, String>()));

    }

//    辅助的重载方法
    Collection fill(Collection<String> c){
        c.add("A");
        c.add("C");
        c.add("B");
        return c;
    }
    Map fill(Map<String,String> m){
        m.put("a","A");
        m.put("c","C");
        m.put("b","B");
        return m;
    }

}

class SomeThing{

}

class Fruit extends SomeThing{

}

class Apple extends Fruit{
    public void f(){
        System.out.println("apple f()");
    }
}