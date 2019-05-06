package top.itlq.thinkingInJava.containers_17.map;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用两个list实现一个map(对应put和get方法)，
 * 主要，使其entrySet()产生可修改map的视图，考虑实现AbstractSet提供索引视图即可
 * todo
 * @param <K>
 * @param <V>
 */
public class SlowMap<K,V> extends AbstractMap<K,V> {
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    class EntrySet extends AbstractSet<K>{

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }
}
