package top.itlq.thinkingInJava.string_13.regex;

import org.junit.Test;

import java.util.Arrays;

public class StringRegexMethods {
    // matches
    @Test
    public void test1(){
        // 其他语言 \d 正则表达式中表示一个整数，java的正则表达式需要对\先转义； \\d 代表一个整数
        System.out.println("-2".matches("-?\\d"));
        System.out.println("223".matches("-?\\d+")); // ?代表前面的可无/可有一个； + 代表前面的至少有一个
        System.out.println("+888".matches("(-|\\+)?\\d+")); // | 代表或
    }
    // split 匹配的去掉，返回分割出的元素数组
    @Test
    public void test2(){
        // 第一个\\没有意义 \W 代表[^\w]
        System.out.println(Arrays.toString("400,222-444.aaa  vvv".split("-|\\,|\\.|\\W")));
        // 多个空格，尽量多匹配
        System.out.println(Arrays.toString("400,222-444.aaa  vvv".split("-|,|\\.|\\W+")));
        // 限制分割次数，左->右->左->右->左 从左对称优先分割；
        System.out.println(Arrays.toString("400,222-444.aaa  vvv".split("-|,|\\.|\\W+",2)));
        System.out.println(Arrays.toString("400,222-444.aaa  vvv".split("-|,|\\.|\\W+",3)));
        // \w 代表 [0-9a-zA-Z]
        System.out.println(Arrays.toString("400,222-444.aaa vvv".split("\\w")));  // 第一个\\没有意义

    }
    // replaceFirst() replaceAll() 替换
    @Test
    public void test3(){
        System.out.println("180,1896\\\\-2368".replaceFirst(",|\\\\+-+","-"));
        System.out.println("180,1896\\\\-2368".replaceAll(",|\\\\+-+","-"));
        // 匹配一句话中一个单词
        System.out.println("hey,lll".replaceFirst("h\\w+","hello"));
    }

    @Test
    public void test35(){
        System.out.println("中".matches("."));
    }

    public static boolean checkSentence(String s){
        return s.matches("[A-Z].+\\."); // []代表其中任意个
    }

    // 检查
    @Test
    public void test4(){
        System.out.println(checkSentence("I'm Ok."));
        System.out.println(checkSentence("I'm Ok。"));
        System.out.println(checkSentence("i'm Ok."));
        System.out.println("A-Z".matches("A-Z"));
        System.out.println("A-Z-".matches("A-Z[A\\-Z]"));

    }
}
