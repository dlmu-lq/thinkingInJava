package top.itlq.thinkingInJava.chapter12_collections.diyCollection;

import java.util.Arrays;
import java.util.Iterator;

public class CollectionWithIterator<E> {
    private E [] arr;
    public CollectionWithIterator(E [] arr){
        this.arr = Arrays.copyOf(arr,arr.length);
    }
    public Iterator<E> iterator(){
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public E next() {
                return arr[index++];
            }
        };
    }
    public static void main(String...args){
        CollectionWithIterator<String> collectionWithIterator = new CollectionWithIterator<>("c,a,b".split(","));
        Iterator<String> it = collectionWithIterator.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
