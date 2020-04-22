package top.itlq.thinkingInJava.chapter14_streams;

import org.junit.Test;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 使用流 声明式编程
 */
public class Streams {
    /**
     * 随机数流
     */
    @Test
    public void testRandomInts() {
        new Random(47)
                .ints(5, 20)
                .distinct()
                .limit(7)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void testRandomIntsBoxed() {
        new Random(47)
                .ints(2, 5, 20)
                .limit(3)
                .boxed()
                .forEach(System.out::println);
    }
    /**
     * 使用接口（函数式）生成
     */
    @Test
    public void testStreamGenerate(){
        Stream.generate(Streams::string)
                .limit(3)
                .forEach(System.out::println);
    }

    public static String string(){
        return "ddd";
    }

    /**
     * 种子，计算，skip
     */
    @Test
    public void testStreamIterate(){
        Stream.iterate(0, i->++i)
                .skip(3)
                .limit(3)
                .forEach(System.out::println);
    }

    /**
     * 排序操作后流的方式改变；
     */
    @Test
    public void testStreamSorted(){
        Stream.of(1,2,3)
                .peek(System.out::println)
                .sorted(Comparator.reverseOrder())
                .peek(System.out::println)
                .map(i->++i)
                .forEach(System.out::println);
    }

    /**
     * flatMap 扁平化流元素
     */
    @Test
    public void testStreamFlatMap(){
        Stream.of(1,2,3)
                .flatMapToInt(i->IntStream.iterate(i, j->++j).limit(i))
                .forEach(System.out::println);
    }

    /**
     * 终端操作 forEach，并发操作 parallel
     */
    @Test
    public void testStreamParallelForeach(){
        IntStream.iterate(1, i->++i)
                .limit(10)
                .parallel()
                .forEach(System.out::println);

        IntStream.iterate(1, i->++i)
                .limit(10)
                .parallel()
                .forEachOrdered(System.out::println);
    }

    /**
     * 终端操作 收集
     */
    @Test
    public void testStreamCollectWithCollectors(){
        // 集合
        System.out.println(
                IntStream.iterate(1, i->++i % 3)
                        .limit(10)
                        .parallel()
                        .boxed()
                        .collect(Collectors.toCollection(TreeSet::new))
                        .toString()
        );
        // 结果分组map，list
        System.out.println(
                IntStream.iterate(1, i->++i)
                        .limit(10)
                        .parallel()
                        .boxed()
                        .collect(Collectors.groupingBy(i->i%3))
                        .toString()
        );
        // 结果Boolean分组，map，list
        System.out.println(
                IntStream.iterate(1, i->++i)
                        .limit(10)
                        .parallel()
                        .boxed()
                        .collect(Collectors.partitioningBy(i -> i % 3 > 1))
                        .toString()
        );
        // 组合string
        System.out.println(
                IntStream.iterate(1, i->++i)
                        .limit(10)
                        .parallel()
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", "))
        );
    }
}
