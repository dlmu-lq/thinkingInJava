package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;


public class ClassLoaderTest {
    /**
     * todo,为什么前两个classloader的加载路径系统属性为null，jdk9+ 后均是null
     */
    @Test
    public void testClassLoaderDirs(){
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println();
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println();
        System.out.println(String.join("\r\n", System.getProperty("java.class.path")
                .split(";")));
    }

    @Test
    public void testClassForName() throws ClassNotFoundException {
        Class<?> iClass = Class.forName("[I");
        System.out.println(iClass);
        System.out.println(int[].class == iClass);
        try {
            ClassLoader.getSystemClassLoader().loadClass("[I");
        }catch (Exception e){
            System.err.println("加载失败:" + e);
        }
        ClassLoader.getSystemClassLoader().loadClass("java.lang.Integer");
    }
}
