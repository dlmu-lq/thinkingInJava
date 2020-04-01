package top.itlq.thinkingInJava.chapter12_collections.iterableAndForeach;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class IterableDemo<T> implements Iterable<T>{
    private T [] arr;
    public IterableDemo(){}
    public IterableDemo(T [] arr){
        this.arr = arr;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    public static <E> void iterablePrint(Iterable<E> iterable){
        for(E a : iterable){
            System.out.println("iterable print:" + a);
        }
    }

    /**
     *
     */
    public static void main(String...args){
        // 实现 Iterable接口的类可以使用foreach遍历
        IterableDemo<String> song = new IterableDemo<>("we three -- The Ink Spots".split(" "));
        iterablePrint(song);

        // 获取系统环境变量
        for(Map.Entry entry:System.getenv().entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 数组没有实现Iterable 但可以使用foreach,被编译成普通for循环
        int [] ints = {1,3,5,6,4,2};
        // iterablePrint(ints);
        for(int i:ints){
            System.out.println(i);
        }
        // 将数组转换为Iterable
        String [] strings = {"1","2","3"};
        iterablePrint(Arrays.asList(ints));   //基本类型数组asList会有陷阱
        iterablePrint(Arrays.asList(strings));

        // Arrays.asList用于基本类型数组会出问题
        System.out.println(Arrays.asList(new Integer[]{1,2,3}));
        System.out.println(Arrays.asList(new int[]{1,2,3}));
    }

}
