package top.itlq.thinkingInJava.exceptions_12;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LostException {
    // 因为再finally中抛出异常而丢失RuntimeException
    @Test
    public void test1(){
        try {
            throw new RuntimeException();
        }finally {
            throw new NullPointerException();
        }
    }
    // 因为再finally中return而丢失RuntimeException
    @Test
    public void test2(){
        try {
            throw new RuntimeException();
        }finally {
            return;
        }
    }

    // 与下一个例子对比
    @Test
    public void test3(){
        int value = 0;
        try {
            value = 100;
        }finally {
            value = 200;
        }
        System.out.println(value);
    }

    public static int f(){
        // 返回100
        /**
         * 运行步骤，
         * 1、try 赋值
         * 2、return 保存临时局部变量（返回值）
         * 3、重新赋值，
         * 4、返回2中保存的临时变量；
         */
        int value = 0;
        try {
            value = 100;
            return value;
        }finally {
            value = 200;
        }
    }

    public static Object g(){
        // 返回100
        /**
         * 运行步骤，
         * 1、try 赋值
         * 2、return 保存临时局部变量（返回值）
         * 3、重新赋值，
         * 4、返回2中保存的临时变量；对象为引用
         */
        Map<Integer,Integer> values = new HashMap<>();
        values.put(1,0);
        try {
            values.put(1,100);
            return values;
        }finally {
            values.put(1,200);
        }
    }

    public static void main(String...ar){
        System.out.println(f());
        System.out.println(g());
    }
}
