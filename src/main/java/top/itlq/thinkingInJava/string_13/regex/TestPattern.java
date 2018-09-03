package top.itlq.thinkingInJava.string_13.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
    @Test
    public void test1(){
        String regex = "a+";
        CharSequence charSequence = "abcabcabcabcaa";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 产生 Matcher 对象
        Matcher matcher = pattern.matcher(charSequence);
        // Matcher的find() 是否仍有匹配项，如果有，移动到下一个匹配项，group() 当前匹配向内容，start()当前匹配项第一个字符位置，end() 第一个不匹配字符位置
        while (matcher.find()){
            System.out.println( "group：" + matcher.group() + " ," + matcher.start() + "-" + (matcher.end() - 1));
        }

        // find(start)，不能自动移动开始索引，用while会产生无限循环
        matcher.reset("bbbaaacccaaa");
        int index = 3;
        while (matcher.find(index)){
            System.out.println( "group：" + matcher.group() + " ," + matcher.start() + "-" + (matcher.end() - 1));
            index = matcher.end();
        }

        // Matcher 的matchers 方法，是否匹配整个
        matcher.reset("aa");
        System.out.println("(matches)aa:" + matcher.matches());
        matcher.reset("ab");
        System.out.println("(matches)ab:" + matcher.matches());


        // Matcher 的 lookingAt方法，是否匹配首,如果是 a* 则全部匹配首（无字符匹配）
        matcher.reset("a");
        System.out.println("(lookingAt)a:" + matcher.lookingAt());
        matcher.reset("b");
        System.out.println("(lookingAt)b:" + matcher.lookingAt());
        matcher.reset("ab");
        System.out.println("(lookingAt)ab:" + matcher.lookingAt());
    }

    // 练习
    @Test
    public void test2(){
        // (?i) 忽略大小写
        // ^ 放在[]外意思是一行首
        // $ 一行的结束
        // \\b 为词边界
        // \\B 为非词边界
        Matcher m = Pattern.compile("(?i)((^[aeiou])|(\\s+[aeiou]))\\w+?[aeiou]\\b").matcher("Arline ate eight apples and one orange while Anita hadn't any");
        while (m.find()){
            System.out.println(m.group());
        }
    }
}
