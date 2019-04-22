package top.itlq.thinkingInJava.annotations_20.basic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class HandleAnnotation {

    // 没默认值的必须指定
    @UseCase(id = 1)
    public void test1(){

    }

    @UseCase(id = 2,description = "test description")
    public void test2(){

    }

    /**
     * 利用Class的反射机制，获取被注解的方法
     * 例，获取源代码方法上的注解，获取其数据
     */
    @Test
    void test(){
        Class utilsClass = HandleAnnotation.class;
        // 反射获取方法
        for(Method m:utilsClass.getDeclaredMethods()){
            // 获取注解对象
            UseCase uc = m.getAnnotation(UseCase.class);
            if(uc != null){
                System.out.println("name:" + m.getName() + ",id:" + uc.id() + ",description:" + uc.description());
            }else{
                System.out.println(m.getName());
            }
        }
    }
}
