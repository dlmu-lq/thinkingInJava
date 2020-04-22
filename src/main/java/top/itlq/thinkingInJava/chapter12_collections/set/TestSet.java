package top.itlq.thinkingInJava.chapter12_collections.set;


import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class TestSet {
    /**
     * 不重复的性质，todo  运行完为何是排过序的
     */
    @Test
    public void nonRepeat(){
        // 重复元素add
        HashSet<Integer> a = new HashSet<Integer>();
        Random random = new Random(47);
        for(int i=0;i<10000;i++){
            a.add(random.nextInt(30));
        }
        System.out.println(a);
    }

    /**
     * 测试set不同形式排序
     * 构造器内传入排序方式 Comparator 将影响重复的判断法则
     */
    @Test
    public void treeSetOrder(){
        Set<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        treeSet.add("A");
        treeSet.add("b");
        treeSet.add("ab");
        treeSet.add("C");
        treeSet.add("B");
        System.out.println("排序忽略大小写，大小写重复认为相同：" + treeSet);

        Set<String> treeSet2 = new TreeSet<String>();
        treeSet2.addAll(treeSet);
        treeSet2.add("B");
        System.out.println("不忽略大小写" + treeSet2);
    }
}
