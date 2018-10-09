package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ClassObject {
    /**
     * 类在需要用时才加载
     * 类被加载时调用 static块，
     * 加载类方法 1、forName() 2、创建对象
     */
    @Test
    public void loadClass(){
        System.out.println("main function");
        try {
            Class.forName("Shape");
        } catch (ClassNotFoundException e) {
            System.out.println("class not found: Shape");
        }
        new Shape();
        // 加载类必须使用全名
        try {
            Class.forName("top.itlq.thinkingInJava.typeinfo_14.classObject.Shape");
        } catch (ClassNotFoundException e) {
            System.out.println("class not found: Shape");
        }

        try {
            Class.forName("top.itlq.thinkingInJava.typeinfo_14.classObject.Toy");
            System.out.println("Toy类加载完成！");
        } catch (ClassNotFoundException e) {
            System.out.println("class not found: Toy");
        }
        new Toy(); // 类的Class对象只加载一次；
    }

    /**
     * Class 对象的一些方法
     * getSuperClass() 获取基类；
     * getInterfaces() 获取实现的接口的Class对象数组
     * isInterface() getName() getSimpleName() getCanonicalName()
     * 通过类加载的Class对象创建实例 getDeclaredConstructor(...Class).newInstance(...args);
     *
     */
    @Test
    public void classMethods(){
        Class apple = null;
        try {
            apple = Class.forName("top.itlq.thinkingInJava.typeinfo_14.classObject.Apple");
            printClassInfo(apple);
            Class [] cls = apple.getInterfaces();
            for(Class cl:cls)
                printClassInfo(cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Class fruit = apple.getSuperclass();
        Object fruitObj = null;
        try {
            // 获得
            fruitObj = fruit.getDeclaredConstructor(int.class,String.class).newInstance(1,"s");
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(1);
        }
        printClassInfo(fruitObj.getClass());
        System.out.println("a".getClass().equals("b".getClass()));
    }

    /**
     * 练习
     */
    @Test
    public void exercise1(){
        /**
         * 强制转换类型,instanceof 判断
         */
        Orange orange = new Orange();
        Fruit orange1 = orange;
        try{
            Apple apple = (Apple) orange1;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orange1 instanceof Apple){
            Apple apple = (Apple) orange1;
        }
    }

    /**
     * 练习
     * 递归打印基类
     */
    @Test
    public void exercise2(){
        System.out.println("\"1\"");
        printSuperClass("1");
        System.out.println(1);
        printSuperClass(1);
    }

    /**
     * 练习
     * Class.getDeclaredFields()
     */
    @Test
    public void exercise3(){
        Field [] fields = "1".getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            System.out.println(fields[i].getName());
        }
    }

    /**
     * 练习
     * 判断是对象 or 基本类型
     */
    @Test
    public void exercise4(){
        char [] chars = {'a','1'};
        char a = 'a';
        System.out.println(chars instanceof Object);
        System.out.println(a);
        System.out.println(chars.getClass());
    }

    /**
     * 打印Class对象的信息
     * @param cl Class对象
     */
    static void printClassInfo(Class cl){
        System.out.println("Class name:" + cl.getName() + "; is interface:" +
                cl.isInterface() + "; Simple name:" + cl.getSimpleName() + "; Canonical name:" +
                cl.getCanonicalName());
    }

    /**
     * 打印基类
     */
    static void printSuperClass(Object obj){
        if(obj instanceof Class){
            System.out.println(((Class) obj).getName());
            if(((Class) obj).getSuperclass() != null)
                printSuperClass(((Class) obj).getSuperclass());
        }else{
            printSuperClass(obj.getClass());
        }
    }
}

//
interface Eatable{}
interface Vege{}
class Fruit{
    Fruit(){}
    Fruit(int i,String s){
        System.out.println("Fruit constructor: arg:" + i + "," + s);
    }
}
class Apple extends Fruit implements Eatable,Vege{
    Apple(){}
}
class Orange extends Fruit implements Eatable,Vege{
    Orange(){}
}