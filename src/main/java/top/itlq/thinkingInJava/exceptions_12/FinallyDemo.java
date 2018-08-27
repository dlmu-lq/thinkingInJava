package top.itlq.thinkingInJava.exceptions_12;

import org.junit.Test;

public class FinallyDemo {
    // 容易忘记捕获的异常 RuntimeException 容易出现未清理情况
    @Test
    public void test1(){
        try {
            throw new RuntimeException();
        }catch (NullPointerException e){
            System.out.println("caught " + e);
        }
    }
    // 无论什么情况 finally总会运行，runtimeException，或 return
    @Test
    public void test2(){
        try {
            throw new RuntimeException();
        }finally {
            System.out.println("runtime finally");
        }
    }
    @Test
    public void test3(){
        try {
            System.out.println("here we return");
            return;
        }finally {
            System.out.println("return finally");
        }
    }
    @Test
    public void test4(){
        int i = 0;
        try {
            System.out.println("first return");
            if(i == 0) return;
            System.out.println("first return");
            return;
        }finally {
            System.out.println("finally");
        }
    }
}
