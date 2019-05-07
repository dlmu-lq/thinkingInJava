package top.itlq.thinkingInJava.containers_17.map;

import java.util.*;

/**
 * 使用散列、散列表实现Map
 */
public class SimpleHashMap<K,V> extends AbstractMap<K,V> {
    static final int SIZE = 997;
    private int size = 0;

    private LinkedList<Entry<K,V>>[] buckets = new LinkedList[SIZE];

    public V put(K key, V value){
        int index = Math.abs(key.hashCode()) % SIZE;
        LinkedList<Entry<K,V>> bucket = buckets[index];
        if(bucket == null){
            bucket = buckets[index] = new LinkedList<>();
        }
        Iterator<Entry<K,V>> iterator = bucket.iterator();
        while (iterator.hasNext()){
            Entry<K,V> entry = iterator.next();
            if(entry.getKey().equals(key)){
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        bucket.add(new MapEntry<>(key,value));
        size++;
        return null;
    }

    public V get(Object key){
        int index = Math.abs(key.hashCode()) % SIZE;
        LinkedList<Entry<K,V>> bucket = buckets[index];
        if(bucket == null){
            return null;
        }
        Iterator<Entry<K,V>> iterator = bucket.iterator();
        while (iterator.hasNext()){
            Entry<K,V> entry = iterator.next();
            if(entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }

    private Set<Entry<K,V>> entries = new EntrySet();

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }

    class EntrySet extends AbstractSet<Entry<K, V>> {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new Iterator<Entry<K, V>>() {
                private int index = -1;
                @Override
                public boolean hasNext() {
                    return index < size - 1;
                }

                @Override
                public Entry<K, V> next() {
                    int flg = -1;
                    for(LinkedList<Entry<K,V>> bucket:buckets){
                        if(bucket != null){
                            for(Entry<K,V> entry:bucket){
                                flg++;
                                if(flg == index + 1){
                                    index++;
                                    return entry;
                                }
                            }
                        }
                    }
                    return null;
                }

                @Override
                public void remove(){
                    int flg = -1;
                    for(LinkedList<Entry<K,V>> bucket:buckets){
                        if(bucket != null){
                            for(Entry<K,V> entry:bucket){
                                flg++;
                                if(flg == index){
                                    bucket.remove(entry);
                                    size--;
                                    index--;
                                }
                            }
                        }
                    }
                }
            };
        }

        @Override
        public int size() {
            return size;
        }
    }

    public static void main(String...args){
        SimpleHashMap<Integer,String> simpleHashMap = new SimpleHashMap<>();
        simpleHashMap.put(1,"1");
        simpleHashMap.put(2,"1");
        System.out.println(simpleHashMap);
        simpleHashMap.put(1,"2");
        System.out.println(simpleHashMap);
        simpleHashMap.remove(1);
        System.out.println(simpleHashMap);
        simpleHashMap.remove(2,"1");
        System.out.println(simpleHashMap);
        simpleHashMap.put(1,"1");
        simpleHashMap.put(2,"1");
        System.out.println(simpleHashMap);
        simpleHashMap.clear();
        System.out.println(simpleHashMap);
    }
}
