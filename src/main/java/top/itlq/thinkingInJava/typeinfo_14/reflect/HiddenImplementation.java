/**
 * 由访问权限控制的隐藏方法、域 通过反射均能实现
 */
package top.itlq.thinkingInJava.typeinfo_14.reflect;

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.reflect.hiddenPackage.A;
import top.itlq.thinkingInJava.typeinfo_14.reflect.hiddenPackage.HiddenC;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HiddenImplementation {

    static void callHiddenMethod(Object object,String methodName) throws Exception{
        // 获取的是实际对象的类型信息，包含实际对象的所有方法；
        Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(object);
    }

    /**
     * 反射访问私有方法，私有方法不能被派生类所有
     */
    @Test
    public void test1(){
        A a = HiddenC.makeA();
//        (C)a; 包访问权限，不能强转；
        try {
            callHiddenMethod(a,"test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // private不能被继承，但可以在原类中由反射拓展其访问权限
        System.out.println();
        A realA = new A();
        try {
            callHiddenMethod(realA,"test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射访问没有访问权限的域
     */
    @Test
    public void test2() throws Exception{
        A a = new A();
        System.out.println(a);
        // 访问改变 int
        Field field = a.getClass().getDeclaredField("i");
        field.setAccessible(true);
        field.setInt(a,2);
        System.out.println(a);

        // 访问改变 Integer
        field = a.getClass().getDeclaredField("j");
        field.setAccessible(true);
        field.set(a,2);
        System.out.println(a);

        // 访问改变 String
        field = a.getClass().getDeclaredField("s");
        field.setAccessible(true);
        field.set(a,"2s");
        System.out.println(a);

        // 访问改变 final 无异常，不会改变
        field = a.getClass().getDeclaredField("finalS");
        field.setAccessible(true);
        field.set(a,"2s");
        System.out.println(a);

    }
}
