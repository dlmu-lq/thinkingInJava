package top.itlq.thinkingInJava.string_13.regex;

import org.junit.Test;

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
            System.out.print("事件：" + matcher.group(6));
            System.out.println();
        }
    }

}
