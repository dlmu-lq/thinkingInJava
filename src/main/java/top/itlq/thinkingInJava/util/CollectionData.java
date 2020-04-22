package top.itlq.thinkingInJava.util;


import java.util.ArrayList;
import java.util.Random;

/**
 * 一个用来生成Collection类容器的工具，适配 Generator
 * @param <T>
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> generator,int quantity){
        for(int i=0;i<quantity;i++){
            add(generator.next());
        }
    }

    public static <T> CollectionData<T> list(Generator<T> generator,int quantity){
        return new CollectionData<>(generator,quantity);
    }

    public static void main(String...args){
        System.out.println(CollectionData.list(new Generator<Integer>() {
            @Override
            public Integer next(){
                return new Random().nextInt(20);
            }
        },10));

        //
        System.out.println(CollectionData.list(() -> new Random().nextInt(20),10));
    }
}
