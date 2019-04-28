package top.itlq.thinkingInJava.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * 生成Map数据
 */
public class MapData<K,V> extends LinkedHashMap<K,V> {
    /**
     * key value均由生成器生成
     * @param kGenerator
     * @param vGenerator
     * @param quantity
     */
    public MapData(Generator<K> kGenerator, Generator<V> vGenerator, int quantity){
        for(int i=0;i<quantity;i++){
            put(kGenerator.next(),vGenerator.next());
        }
    }

    /**
     * key 由生成器生成，value固定
     * @param kGenerator
     * @param value
     * @param quantity
     */
    public MapData(Generator<K> kGenerator, V value, int quantity){
        for(int i=0;i<quantity;i++){
            put(kGenerator.next(), value);
        }
    }

    /**
     * key由可迭代对象产生，value由生成器生成
     * @param kIterable
     * @param vGenerator
     */
    public MapData(Iterable<K> kIterable, Generator<V> vGenerator){
        for(K key:kIterable){
            put(key,vGenerator.next());
        }
    }

    /**
     * key由可迭代对象产生，value固定
     * @param kIterable
     * @param value
     */
    public MapData(Iterable<K> kIterable, V value){
        for(K key:kIterable){
            put(key,value);
        }
    }

    public static <K,V> MapData<K,V> map(Generator<K> kGenerator,
                                         Generator<V> vGenerator, int quantity){
        return new MapData<K,V>(kGenerator, vGenerator, quantity);
    }

    public static <K,V> MapData<K,V> map(Generator<K> kGenerator,
                                         V value, int quantity){
        return new MapData<K,V>(kGenerator, value, quantity);
    }

    public static <K,V> MapData<K,V> map(Iterable<K> kIterable, Generator<V> vGenerator){
        return new MapData<K,V>(kIterable,vGenerator);
    }

    public static <K,V> MapData<K,V> map(Iterable<K> kIterable, V value){
        return new MapData<K,V>(kIterable,value);
    }

    public static void main(String...args){
        MapData<Integer,String> mapData = MapData.map(
                () -> new Random().nextInt(100), "Pop", 10);
        System.out.println(mapData); // Generator,V ,quantity
        System.out.println(MapData.map(Arrays.asList(10,20,30),() -> String.valueOf(System.currentTimeMillis())));
        System.out.println(new TreeMap<>(mapData));
    }
}
