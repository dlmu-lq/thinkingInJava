package top.itlq.thinkingInJava.chapter13_functional;

import java.util.function.IntSupplier;

/**
 * 测试闭包
 * 类的成员变量在类的闭包方法中当作"引用"传入；可以改变，共享存储空间；
 * 而局部变量传入方法时传的是值（地址值），改变不会影响外部局部变量，会造成数据不同步，因此编译器要求其只能以final等同效果传入；
 */
public class TestClosure {
    private int i;

    private IntSupplier getFuc(int k){
        // this.i 更好理解
        return () -> k + (i++);
    }

    public static void main(String[] args) {
        TestClosure tc = new TestClosure();
        IntSupplier intSupplier = tc.getFuc(1);
        intSupplier.getAsInt();
        intSupplier = tc.getFuc(1);
        intSupplier.getAsInt();
        System.out.println(tc.i);
    }
}
