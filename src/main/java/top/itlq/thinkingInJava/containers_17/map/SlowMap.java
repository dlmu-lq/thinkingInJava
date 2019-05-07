package top.itlq.thinkingInJava.containers_17.map;

import java.util.*;

/**
 * 使用两个list实现一个map(对应put和get方法)，
 * 主要，使其entrySet()产生可修改map的视图，考虑实现AbstractSet提供索引视图即可，可实现修改entrySet修改map
 * @param <K>
 * @param <V>
 */
public class SlowMap<K,V> extends AbstractMap<K,V> {

    private List<K> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();
    private Set<Map.Entry<K,V>> entries = new EntrySet();

    public V put(K key, V value){
        V re = null;
        if(keys.contains(key)){
            re = value;
            values.set(keys.indexOf(key),value);
        }else{
            keys.add(key);
            values.add(value);
        }
        return re;
    }

    // 只能通过 get(Object) 而不是 get(K)
    public V get(Object key){
        if(keys.contains(key)){
            return values.get(keys.indexOf(key));
        }else{
            return null;
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entries;
    }

    class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<>() {
                private int index = -1;
                @Override
                public boolean hasNext() {
                    return index < keys.size() - 1;
                }

                @Override
                public Map.Entry<K, V> next() {
                    index++;
                    return new MapEntry<>(keys.get(index),values.get(index));
                }

                @Override
                public void remove(){
                    keys.remove(index);
                    values.remove(index--);// 需要--以保证hasNext 及 next正常使用
                }
            };
        }

        @Override
        public int size() {
            return keys.size();
        }
    };


    public static void main(String...args){
        SlowMap<Integer,String> slowMap = new SlowMap<>();
        slowMap.put(1,"1");
        slowMap.put(1,"2");
        slowMap.put(2,"1");
        Set entrySet = slowMap.entrySet();
        System.out.println(slowMap);
        // 成功移除了map中equals的键值
        entrySet.remove(new MapEntry(1,"2"));
        System.out.println(slowMap);
    }
}
