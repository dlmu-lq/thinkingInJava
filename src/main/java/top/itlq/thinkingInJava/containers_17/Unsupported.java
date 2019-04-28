package top.itlq.thinkingInJava.containers_17;

import org.junit.Test;

import java.util.*;

/**
 * 测试UnsupportedOperationException
 * @param <T>
 */
public class Unsupported<T> extends AbstractCollection<T> {
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    public static void main(String...args){
        // 未或实现的操作
        new Unsupported<>().add(new Object());
    }

    static void testUnsupported(List<String> list){
        List subList = list.subList(0,2);
        try {
            list.retainAll(subList);
        }catch (UnsupportedOperationException e){
            System.out.println(e.toString());
        }
        try {
            list.set(0,"-1");
        }catch (UnsupportedOperationException e){
            System.out.println(e.toString());
        }
        System.out.println(list);
    }

    @Test
    public void test(){
        List<String> list = Arrays.asList("1","2","3");
        testUnsupported(list);
        System.out.println();
        testUnsupported(new ArrayList<>(list));
        System.out.println();
        testUnsupported(Collections.unmodifiableList(new ArrayList<>(list)));
    }
}
