package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ClassObject {
    /**
     * 类在需要用时才加载
     * 类被加载时调用 static块
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
        new Toy();
    }

    /**
     * Class 对象的一些方法
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

    static void printClassInfo(Class cl){
        System.out.println("Class name:" + cl.getName() + "; is interface:" +
                cl.isInterface() + "; Simple name:" + cl.getSimpleName() + "; Canonical name:" +
                cl.getCanonicalName());
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