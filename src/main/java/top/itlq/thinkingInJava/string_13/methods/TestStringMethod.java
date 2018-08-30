package top.itlq.thinkingInJava.string_13.methods;

import org.junit.Test;

import java.util.Arrays;

public class TestStringMethod {
    @Test
    public void test1(){
        String s1 = "string";
        System.out.println("s1:" + s1);
        // length()
        System.out.println("s1.length(): " + s1.length());
        // charAt()
        System.out.println("s1.charAt(s1.length() - 2): " + s1.charAt(s1.length() - 2));
        // getCharts()
        char [] reArr = new char[4];
        s1.getChars(1,s1.length() - 2,reArr,1);
        System.out.println("s1.getChars(1,s1.length() - 2,reArr,1): reArr :" + Arrays.toString(reArr));
        // getBytes()
        byte [] bytes = s1.getBytes();
        System.out.println("s1.getBytes():" + Arrays.toString(bytes));
        System.out.println("new String(s1.getBytes()):" + new String(s1.getBytes()));
        System.out.println("中.getBytes():" + Arrays.toString("中".getBytes()));

    }
}
