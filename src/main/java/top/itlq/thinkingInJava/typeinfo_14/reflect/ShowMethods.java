package top.itlq.thinkingInJava.typeinfo_14.reflect; // 有包名控制台运行需配置classPath

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.classObject.Toy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ShowMethods {

    public ShowMethods(){}

    //remove the package name
    private static final Pattern pattern = Pattern.compile("(\\w+\\.)|( native)|( final)");

    public static void main(String...args){
//        if(args.length < 1)
//            return;
        try {
            // 非public的方法包括构造器都获取不到
            Class<?> cl = Class.forName("top.itlq.thinkingInJava.typeinfo_14.classObject.Toy");
//            Class<?> cl = Class.forName("top.itlq.thinkingInJava.typeinfo_14.reflect.ShowMethods");
            Method [] methods = cl.getMethods();
            for(Method method:methods){
                System.out.println(pattern.matcher(method.toString()).replaceAll(""));
            }
            System.out.println("Constructors");
            Constructor [] constructors = cl.getConstructors();
            for (Constructor constructor:constructors){
                System.out.println(constructor);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过constructor创建对象
     */
    @Test
    public void test(){
        Constructor [] constructors = Toy.class.getConstructors();
        for(Constructor constructor:constructors){
            if(constructor.toString().indexOf("()") == -1){
                try {
                    System.out.println(constructor.newInstance(1));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
