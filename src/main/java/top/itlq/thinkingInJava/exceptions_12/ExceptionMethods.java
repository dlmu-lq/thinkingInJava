package top.itlq.thinkingInJava.exceptions_12;

import org.junit.Test;

import java.util.Arrays;

public class ExceptionMethods {
    static void f() throws Exception {
        h();
    }

    static void h() throws Exception {
        throw new Exception("test message");
    }

    public static void main(String...args){
        try {
            f();
        }catch (Exception e){
            info(e);
        }
    }

    static void info(Exception e){

        System.out.println("getMessage:" + e.getMessage());
        System.out.println("getLocalizedMessage:" + e.getLocalizedMessage());
        System.out.println("toString" + e);
        System.out.print("printStackTrace:");
        e.printStackTrace(System.out);

        System.out.println("getClass():" + e.getClass());
        System.out.println("getClass().getName():" + e.getClass().getName());
        System.out.println("getClass().getSimpleName():" + e.getClass().getSimpleName());

        System.out.println("stack trace element:");
        StackTraceElement [] stes = e.getStackTrace();
        for(StackTraceElement ste:stes){
            System.out.println(ste.getMethodName());
        }
    }
}
