package top.itlq.thinkingInJava.string_13;

import org.junit.Test;

public class StringBaseTest {
    /**
     * 循环连接中使用字符串
     * 初始化大小；
     */
    @Test
    public void test1(){
        StringBuilder sb = new StringBuilder(70);
        for(int i=0;i<20;i++) {
            sb.append(i);
            sb.append("j");
//            sb.append(i + "j"); // bad idea  括号内编译器重新创建StringBuilder用于连接
        }
        System.out.println(sb.toString());
    }

    /**
     *
     */
    @Test
    public void test2(){

    }
}
