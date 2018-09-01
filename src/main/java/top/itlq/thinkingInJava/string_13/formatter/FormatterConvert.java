package top.itlq.thinkingInJava.string_13.formatter;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Formatter;

public class FormatterConvert {
    @Test
    public void test1(){
        Formatter f = new Formatter(System.out);
        // 字符
        char c = 'a';
        System.out.println("char c = 'b'");
        f.format("s: %s\n",c);
        f.format("b: %b\n",c); // boolean
        f.format("c: %c\n",c); // Unicode 字符
        f.format("h: %h\n",c); // 散列码 十六进制

        // 整数
        int v= 120;
        System.out.println("int v= 120");
        f.format("s: %s\n",v);
        f.format("d: %d\n",v); // 整数
        f.format("b: %b\n",v);
        f.format("c: %c\n",v); // Unicode字符,对于没有对应字符的整数会报错
        f.format("x: %x\n",v); // 整数 十六进制
        f.format("h: %h\n",v); // 散列码 十六进制

        // BigInteger
        BigInteger w = new BigInteger("5000000000000000");
        System.out.println("BigInteger w = new BigInteger(\"5000000000000000\")");
        f.format("s: %s\n",w);
        f.format("d: %d\n",w);
        f.format("b: %b\n",w);
        f.format("x: %x\n",w);
        f.format("h: %h\n",w);

        // 浮点
        double d = 123.4;
        System.out.println("double d = 123.4");
        f.format("s: %s\n",d);
        f.format("b: %b\n",d);
        f.format("f: %f\n",d);
        f.format("e: %e\n",d); // 科学计数法
        f.format("h: %h\n",d);

        // 普通对象
        FormatterConvert o = new FormatterConvert();
        System.out.println("FormatterConvert o = new FormatterConvert()");
        f.format("s: %s\n",o);
        f.format("b: %b\n",o);
        f.format("h: %h\n",o);

        // boolean
        boolean b = false;
        System.out.println("boolean b = false");
        f.format("b: %b\n",b);
        f.format("h: %h\n",b);
        f.format("s: %s\n",b);

        // %
        f.format("%%");
        f.format("0: %b",0);
    }

    @Test
    public void test2(){
        System.out.println(String.format("%X",255));
    }
}
