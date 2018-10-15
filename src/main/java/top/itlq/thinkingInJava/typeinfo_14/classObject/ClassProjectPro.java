/**
 * Class类字面常量及一些应用
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ClassProjectPro {
    /**
     * 类加载使用的三步
     * 加载  链接  初始化
     */
    @Test
    public void test1(){
        Class<?> initable = Initable.class; //加载，无需初始化 forName需初始化
        System.out.println("after getting Class of Initable");
        String a = Initable.s; // 编译器常量无需初始化
        System.out.println("after getting const of Initable");
        a = Initable.a;  // 链接 初始化
        System.out.println("after getting static final(not compiled const) of Initable");
        System.out.println();
        a = Initable2.s;
        System.out.println("after getting static(not const) of Initable2");
    }

    /**
     * 泛化的Class应用
     */
    @Test
    public void test2(){
        // 同一类型的加载Class对象只有一个，为不同的引用；
        System.out.println(int.class == Integer.TYPE);

        // 基本类型对应的Class对象 == 包装类.TYPE !=  包装类.class
        System.out.println(int.class == Integer.class);

        Class<Integer> integerClass = int.class;
        // can not compile 固定类型泛化不能放松限制；
        // integerClass = double.class;
        // Class<Number> numberClass = Integer.class;

        // newInstance可以确定类型
        Class<? extends CharSequence> numberClass = String.class;
        // numberClass = int.class;
        try {
            CharSequence charSequence = numberClass.getDeclaredConstructor(String.class).newInstance("1");
            System.out.println(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 超类不能确定类型，可理解为：超类不知道超几层，只能确定其最顶层Object 比如其父类可能还有父类
        Class<? super String> charsClass = String.class.getSuperclass();
        try {
            Object object = charsClass.getDeclaredConstructor(String.class).newInstance("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转型
     * 显示（强制）向下转型
     */
    @Test
    public void test3(){
        Initable initable = new Initable4();
        // cast新式转型
        Class<Initable4> initable4Class = Initable4.class;
        Initable4 initable4 = initable4Class.cast(initable);
        // 老,常用
        initable4 = (Initable4) initable;
    }
}

class Initable{
    static final String s = "1";
    static final String a = Initable3.getA();
    static {
        System.out.println("Initable 初始化！");
    }
}

class Initable2 extends Initable{
    static String s = "1";
    static {
        System.out.println("Initable2 初始化！");
    }
}

class Initable3{
    static final String a = "a";
    static final String getA(){
        return a;
    }
    static {
        System.out.println("Initable3 初始化！");
    }
}
class Initable4 extends Initable{

}