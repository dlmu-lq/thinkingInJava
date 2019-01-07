package top.itlq.thinkingInJava.string_13.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String...args){
        String string = "Rafa Nadal quits this Asia season;";
        Matcher matcher = Pattern.compile(" (\\w)").matcher(string);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            matcher.appendReplacement(sb,matcher.group().toUpperCase());
        }
        matcher.appendTail(sb);
        System.out.print(sb);
    }
}
