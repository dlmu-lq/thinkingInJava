package top.itlq.thinkingInJava.holdingObjects_11.diyCollection;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;

public class CollectionWithExtends<T> extends AbstractCollection<T>{
    private T [] arr;
    public CollectionWithExtends(T [] arr){
        this.arr = Arrays.copyOf(arr,arr.length);
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

    @Override
    public int size() {
        return arr.length;
    }

    public static void main(String...args){
        CollectionWithExtends<String> stringCollectionWithExtends = new CollectionWithExtends<>("a,b,c".split(","));
        for(String s:stringCollectionWithExtends){
            System.out.println(s);
        }
    }
}
