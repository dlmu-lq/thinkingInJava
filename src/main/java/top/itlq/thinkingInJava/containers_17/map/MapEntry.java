package top.itlq.thinkingInJava.containers_17.map;

import java.util.Map;

public class MapEntry<K,V> implements Map.Entry<K,V>{
    K key;
    V value;

    MapEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    /**
     * 一致性 返回旧值
     * @param value
     * @return
     */
    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof MapEntry)) return false;
        MapEntry oe = (MapEntry) o;
        return (oe.getKey() == null
                ? key == null : oe.getKey().equals(key)) &&
                (oe.getValue() == null ?
                        value == null : oe.getValue().equals(value));
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }
}
