/**
 * test1 括号捕获组；
 * test2 大小写不敏感等条件
 * test3 split方法
 * test1 替换操作 replaceFirst，replaceAll，appendReplacement()，appendTail()，reset();
 */
package top.itlq.thinkingInJava.string_13.regex;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternPro {
    // 正则表达式匹配中的组，捕获组，命名捕获组，忽略捕获组
    // 通过
    @Test
    public void test1(){
        String reWithGroup = "(?m)(\\d+)-(\\d+-(\\d+\\b))((\\s+)(\\d+:\\d+))$";
        CharSequence chars = "2018-09-05 22:26\r\n 2018-09-30 00:00";
        Matcher matcher = Pattern.compile(reWithGroup).matcher(chars);
        while (matcher.find()){
            System.out.print("年：" + matcher.group(1));
            System.out.print("日期：" + matcher.group(2));
            System.out.print("时间：" + matcher.group(6));
            System.out.println();
        }
    }

    /**
     * (?i) 大小写不敏感；
     * (?m) 识别\n \r
     */
    @Test
    public void test2(){
        String [] res = {
                "((?i)java)(c)",
                "(?i)(java)(c)",
                "((?m)^javaC)",
                "(^javaC)",
        };
        Pattern pattern = null;
        for(String re:res){
            pattern = Pattern.compile(re);
            System.out.println(re + ":" );
            Matcher matcher = pattern.matcher("javaC1\n(javaC2)(javaC3\njavaC4)");
            while (matcher.find()){
                System.out.print(" " + matcher.group());
            }
            System.out.println();
        }
    }

    // split
    @Test
    public void test3(){
        String string = "You are a liar,you owe me an apology";
        Pattern pattern = Pattern.compile("\\s+|,");
        System.out.println(Arrays.toString(pattern.split(string)));
        System.out.println(Arrays.toString(string.split("\\s+|,")));
    }

    // 替换操作，matcher的replaceFirst，replaceAll，appendReplacement()，appendTail();
    @Test
    public void test4(){
        String string = "Rafa Nadal quits this Asia season;";
        Matcher matcher = Pattern.compile(" (\\w)").matcher(string);
        System.out.println(matcher.replaceFirst("HA"));
        matcher.reset(string);
        System.out.println(matcher.replaceAll("HA"));
        matcher.reset(string);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            matcher.appendReplacement(sb,matcher.group().toUpperCase());
        }
        // 在appendTail之前，appendReplacement已经将前面为匹配的加上；appendTail只增加后面所有未匹配的内容；

        System.out.println(sb);
        matcher.appendTail(sb);
        System.out.println(sb);

        sb = new StringBuffer();
        matcher.reset();        // 不带参数的reset，将其重新设置到原字符序列的起始位置
        while (matcher.find()){
            matcher.appendReplacement(sb,matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        System.out.println(sb);
    }

}
