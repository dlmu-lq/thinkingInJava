/**
 * json在前端处理key可能会不加引号，此处利用正则的appendPlacement appendTail将key未加引号的
 *  json字符串处理未正确的json字符串形式
 */
package top.itlq.thinkingInJava.string_13.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongJsonParse {
    @Test
    public void test1(){
        System.out.println(parse("{a:[1,2],b:\"3\",c:2}"));
    }

    /**
     * 接受一个key没有引号的json字符串，返回一个正确的json字符串；
     * @param wrongJson
     * @return
     */
    public String parse(String wrongJson){
        StringBuilder reSb = new StringBuilder();
        Pattern pattern = Pattern.compile("[{,]\\w+:");
        Matcher matcher = pattern.matcher(wrongJson);
        // 需要函数可变式替换；
        while (matcher.find()){
            String g = matcher.group();
            matcher.appendReplacement(reSb,
                    g.substring(0,1) + "\"" + g.substring(1,g.length() - 1) + "\":");
        }
        matcher.appendTail(reSb);
        return reSb.toString();
    }
}
