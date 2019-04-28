package top.itlq.thinkingInJava.containers_17;

import java.util.*;

/**
 * 使用 享元设计模式 来用自定义的Map，Entry，Set 访问原数据，而不用另外创建全部数据占太大空间
 * 只读数据时
 * 此处数据为countries数据
 */
public class FlyweightCountries {
    // 享元数据
    static final String [][] DATA = {
            {"CHINA", "Beijing"},
            {"USA", "Washington, D.C."},
            {"CANADA", "Ottawa"}
    };

    // 通过它取数据却不重新创建对象,
    private static class FlyweightMap extends AbstractMap<String,String>{

        private Set<Map.Entry<String, String>> entries = new EntrySet(DATA.length);
        /**
         * 实现一个map最少实现的方法
         * @return
         */
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }

        /**
         * 自定义Entry不存储具体对象而只存储索引来读取原数据
         * 泛型容器虽然没有协变性不能直接使用子类，但没关系，只有在返回Entry时需要多态，Entry已经不处在泛型括号内了
         */
        static class Entry implements Map.Entry<String,String>{
            int index;

            Entry(int index){
                this.index = index;
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            /**
             * 新加入Map的元素需要判断Entry相等可能
             * @param o hashMap的实现是分别判断key value equals，均满足返回true
             * @return
             */
            @Override
            public boolean equals(Object o) {
                System.out.println(o instanceof Entry);
                return DATA[index][0].equals(o);
            }

            /**
             * hashMap的实现为 key和value的hashCode的次方
             * @return
             */
            @Override
            public int hashCode() {
                return DATA[index][0].hashCode();
            }
        }

        /**
         * 必须创建新的EntrySet来实现享元
         * 使用已存在的Set实现时，必须将Map所持有的所有对象存入Set
         */
        static class EntrySet extends AbstractSet<Map.Entry<String,String>> {
            private int size;
            EntrySet(int size){
                this.size = size < 0
                        ? 0
                        : size > DATA.length
                        ? DATA.length
                        : size;
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iterator<Map.Entry<String, String>>() {
                    // 这里使用Entry而不是int的index，可以只创建一个工具Entry，遍历时改变索引用来读取数据即可，不是真正的entrySet
                    private Entry entry = new Entry(-1);
                    @Override
                    public boolean hasNext() {
                        return entry.index < size - 1;
                    }

                    @Override
                    public Map.Entry<String, String> next() {
                        entry.index++;
                        return entry;
                    }
                };
            }

            @Override
            public int size() {
                return size;
            }
        }
    }


    public static Map<String,String> select(){
        return new FlyweightMap();
    }

    public static Map<String,String> select(int size){
        return new FlyweightMap(){
            @Override
            public Set<Map.Entry<String,String>> entrySet(){
                return new EntrySet(size);
            }
        };
    }

    public static void main(String...args){
        System.out.println(select());
        // 这里继承实现的map是最基础的只读的，添加操作需要继承实现更多的原来抛出异常的方法
//        select().entrySet().add(new FlyweightMap.Entry(1));
        System.out.println();
        System.out.println(select(2));
        System.out.println();
    }
}

