/**
 * 使用适配器模式，将原来ArrayList实现Iterable接口
 * 写一个方法类似的反向的返回一个实现Iterable接口的对象以用于反向foreach
 */
package top.itlq.thinkingInJava.holdingObjects_11.iterableAndForeach;

import org.junit.Test;

import java.util.*;

public class AdapterMethodIdiom {
    @Test
    public void test(){

        ReversibleArrayList<String> reversibleArrayList = new ReversibleArrayList<>(Arrays.asList("11","2","33"));
        // 使用本身实现的Iterable，
        for(String s : reversibleArrayList){
            System.out.print(s + " ");
        }
        System.out.println();
        // 因为我们向同时使用正向，反向，所有未选择覆盖方法，而是再适配一个
        for(String s : reversibleArrayList.reveseIterable()){
            System.out.print(s + " ");
        }
        System.out.println();
        // 随机
        for(String s : reversibleArrayList.randomIterable()){
            System.out.print(s + " ");
        }
    }
}
class ReversibleArrayList<T> extends ArrayList<T> {
    // 继承
    public ReversibleArrayList(Collection<T> c){super(c);}
    // 为适配 foreach，返回一个Iterable，原ArrayList实现了一个正向的Iterable
    public Iterable<T> reveseIterable(){
        //内部类返回实例
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                // 第二个内部类返回实例
                return new Iterator<T>() {
                    private int index = size() - 1;
                    @Override
                    public boolean hasNext() {
                        return index >= 0;
                    }

                    @Override
                    public T next() {
                        return get(index--);
                    }
                    public void remove(){
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    // 再适配一个随机顺序遍历
    public Iterable<T> randomIterable(){
        List<T> _self = this; // 获得当前，下面进入内部类，不能获取指向此处的this
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                List<T> shuffled = new ArrayList<T>(_self);
                Collections.shuffle(shuffled,new Random(32));
                return shuffled.iterator();
            }
        };
    }
}
