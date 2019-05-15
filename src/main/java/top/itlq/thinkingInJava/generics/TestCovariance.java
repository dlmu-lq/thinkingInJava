package top.itlq.thinkingInJava.generics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class B{}

class C extends B{}

public class TestCovariance {
    /**
     * 数组具有协变性
     */
    @Test
    public void test1(){
        B[] cs = new C[3];
        cs[0] = new C();
        try {
            cs[1] = new B();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cs));
    }

    /**
     * 容器不具有协变性，编译器检查类型
     * 使用泛型 & 通配符解决
     */
    @Test
    public void test2(){
//        List<B> bs = new ArrayList<C>(); // 编译不通过
        List<? extends B> mayBeBs = new ArrayList<>(Arrays.asList(new C(),new C()));
        B definiteB = mayBeBs.get(0);
        System.out.println(definiteB);
//        mayBeBs.add(new C()); // 编译不通过

        List<? super B> definiteBs = new ArrayList<>();
        definiteBs.add(new C());
        definiteBs.add(new B());
//        B maybeB = definiteBs.get(0); // 编译不通过

    }

    /**
     * add 需要知道下界 逆变
     * 返回需要知道上界 协变
     */
    @Test
    public void test3(){
        Generic1<? super B> generic1 = new Generic1<>();
        Generic2<? extends B> generic2 = new Generic2<>();
        generic1.f(new C());
        B b = generic2.f();
    }


    /**
     * 协变，逆变泛型参数
     * @param generic2
     * @param <T>
     * @return
     */
    public static <T> T f1(Generic2<? extends T> generic2){
        return generic2.f();
    }

    public static <T> void f1(Generic1<? super T> generic1, T item){
        generic1.f(item);
    }

    /**
     * 无界通配符
     */
    @Test
    void test4(){
        Generic2<?> generic2 = new Generic2<>();
        Object o = generic2.f();
        Generic1<?> generic1 = new Generic1<>();
//        generic1.f(new Object()); // 编译不通过
        Generic1 generic11 = new Generic1();
        generic11.f(new Object());
        Generic1<Object> generic12 = new Generic1<Object>();
        generic12.f(new Object());
    }
}

class Generic1<T>{
    void f(T t){
        System.out.println(t);
    }
}
class Generic2<T>{
    T f(){
        System.out.println(this);
        return null;
    }
}