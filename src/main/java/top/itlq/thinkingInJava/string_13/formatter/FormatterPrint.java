package top.itlq.thinkingInJava.string_13.formatter;

import org.junit.Test;

import java.util.Formatter;

public class FormatterPrint {
    @Test
    public void test1(){
        // PrintStream 、 PrintWriter有printf方法；
        // System.out 为一个PrintSteam
        System.out.printf("int:%d,float:%f",1,1.2);
        System.out.format("int:%d,float:%f",1,1.2);
    }
    @Test
    public void test2(){
        // Formatter 构造器可以接受格式化输出位置，PrintSteam OutputStream File
        Formatter f = new Formatter(System.out);
        f.format("%s  int:%d,float:%f","呵呵呵",1,1.2);

        f = new Formatter(System.err);
        f.format("%s  int:%d,float:%f","呵呵呵",1,1.2);
    }
    @Test
    public void test3(){
        Formatter f = new Formatter(System.out);
        f.format("%2$-10s,%1$-5d\n",5,"aaa");
        f.format("%5.2f,%10s",5.1,"aaa");
    }

    @Test
    public void testFormatWidth(){
        Formatter f = new Formatter(System.out);
        f.format("%03d",5);
        f.format("%03d",51);
        f.format("%03d",510);
        f.format("%03d",5);
        f.format("%03d",51);
        f.format("%03d",510);
    }
}
