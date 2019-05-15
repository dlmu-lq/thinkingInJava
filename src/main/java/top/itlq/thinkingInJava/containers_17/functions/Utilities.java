package top.itlq.thinkingInJava.containers_17.functions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 容器的实用方法
 */
public class Utilities {
    /**
     * 产生Collection具体类类型的动态类型安全的视图；
     */
    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        // 带泛型的容器，可传入不带泛型的方法中，动态运行时泛型被擦除，可能会添加进不是泛型类型的元素
        addString(list);
        System.out.println(list.get(0));
        // 只在转型时才会发现
        try {
            Integer integer = list.get(0);
        }catch (Exception e){
            System.out.println(e);
        }

        // 而使用动态类型安全检查，运行时强制保证类型安全，不会受泛型擦除而插入不正确的类型
        List<Integer> list1 = Collections.checkedList(new ArrayList<>(),Integer.class);
        try {
            addString(list1);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static void addString(List list){
        list.add("string");
    }

}
