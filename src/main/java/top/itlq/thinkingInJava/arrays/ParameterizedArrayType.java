package top.itlq.thinkingInJava.arrays;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型与数组
 */
public class ParameterizedArrayType {
    @Test
    public void test(){
        // 注意不能实例化带泛型的数组，但是可以创建其引用，再转型（有警告），获得一些编译器检查，但最好用容器；
        List<Integer>[] ls;
        ls = new List[]{Arrays.asList(1,2,"111"),Arrays.asList(2,3,6)};
        // 有错
        // ls[0] = new ArrayList<String>();
        // 可以赋值
        ls[1] = new ArrayList<Integer>();
        new ClassParameter<Integer>().test(ls);
    }

    @Test
    public void test2(){
        new ClassParameter<String>().test1("1222");
        System.out.println(Arrays.toString(ClassParameter.<Integer>test2(1)));
    }
}

/**
 * 在类中创建泛型,可以用引用等，但不能直接操作T
 * 故泛型一般只在类方法边界处有效，内部总是被擦除变得不适用
 * @param <T>
 */
class ClassParameter<T>{
    // 一般不这样用，因为带泛型的数组不能直接实例化
    void test(List<T> [] arrayList){
        System.out.println(Arrays.toString(arrayList));
    }

    // 但这样可以，因为外部使用时是确定的对象当然可以创建，T在运行时已被擦除，只有外部创建类（别处可能为调用泛型方法时）时确定的对象
    void test1(T a){
        System.out.println(a);
    }

    static <T> T[] test2(T a){
        // 不能实例化
        // return new T[]{a};
        // 使用强制转型，获得一个警告，但类似创建了泛型数组
        return (T[])new Object[]{a};
    }
}
