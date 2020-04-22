package top.itlq.thinkingInJava.chapter14_streams;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 非静态方法的函数接口指代，
 * 函数接口
 *      第一个参数为非静态方法的类的对象，
 *      第二个为方法参数，
 *      返回类型与方法返回类型相同
 */

/**
 * 函数接口
 */
interface StreamMatcher extends BiPredicate<Stream<Integer>, Predicate<Integer>> {

}

public class StreamMatcherTest {
    public static void main(String[] args) {
        // 会短路
        StreamMatcher matcher = Stream::anyMatch;
        System.out.println(
                matcher.test(
                        IntStream.range(1, 3)
                                .boxed()
                                .peek(System.out::println),
                        i->i<5
                )
        );
        matcher = Stream::allMatch;
        System.out.println(
                matcher.test(
                        IntStream.range(1, 3)
                                .boxed()
                                .peek(System.out::println),
                        i->i<5)
        );
    }
}