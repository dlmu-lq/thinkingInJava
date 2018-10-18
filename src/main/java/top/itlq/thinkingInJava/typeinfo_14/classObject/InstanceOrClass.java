/**
 * isInstance instanceOf相同，判定了派生类
 * Class 的 == equals相同效果，因为类的对象只有类加载器创建一次
 * 但Class对象当然不能用来对比派生类和基类；
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;

public class InstanceOrClass {
    static void print(Object obj){
        System.out.println("obj.getClass() == B.class:" + (obj.getClass() == B.class));
        System.out.println("obj.getClass().equals(B.class):" + (obj.getClass().equals(B.class)));
        System.out.println("obj.getClass() == Bo.class:" + (obj.getClass() == Bo.class));
        System.out.println("obj.getClass().equals(Bo.class):" + (obj.getClass().equals(Bo.class)));

        System.out.println("B.class.isInstance(obj):" + B.class.isInstance(obj));
        System.out.println("Bo.class.isInstance(obj):" + Bo.class.isInstance(obj));
        System.out.println(" obj instanceof B :" + (obj instanceof B));
        System.out.println("obj instanceof Bo" + (obj instanceof Bo));
    }
    @Test
    public void test1(){
        print(new Bo());
    }
}

class B{}
class Bo extends B{}