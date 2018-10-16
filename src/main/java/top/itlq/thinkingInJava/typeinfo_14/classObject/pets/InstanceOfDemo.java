package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import org.junit.Test;

public class InstanceOfDemo {

    void f(){System.out.println("f");}

    static Object example(){return new InstanceOfDemo();}

    @Test
    public void test(){
        Object obj = InstanceOfDemo.example();
        ((InstanceOfDemo)obj).f(); // 子类通过向下转换检查

        Object i = 2;
        Integer j = 2;
        if(i instanceof String)
            System.out.println("String:" + ((String) i).length());
        else if(i instanceof Integer)
            System.out.println("Integer:" + i);
        // can not cast
//        j instanceof String

    }
}