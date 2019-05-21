package top.itlq.thinkingInJava.enum_19;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public enum EnumDemo {
    E1("test1"),E2("test2");
    private String description;
    private EnumDemo(String str){
        description = str;
    }
    public String getDescription(){
        return description;
    }
//    @Test
//    public void test(){
//        System.out.println(Arrays.toString(EnumDemo.values()));
//        System.out.println(E1.getDescription());
//        System.out.println(E2.getDescription());
//    }
    public static void main(String...args){
        System.out.println(Arrays.toString(EnumDemo.values()));
        System.out.println(E1.getDescription());
        System.out.println(E2.getDescription());
    }

}
