/**
 * Scanner 构造器接受File，Readable，InputStream，String等
 */
package top.itlq.thinkingInJava.string_13.regex.read;

import org.junit.Test;

import java.io.BufferedReader;

import java.io.StringReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanRead {

    public static void main(String...args){
        // 接收任何的输入类型
        // 接受控制台InputStream
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name?");
        String name = scanner.next();
        System.out.println("Enter your age?");
        int age = scanner.nextInt();
        System.out.println("name:" + name + "; age:" + age);
    }

    // 接受Reader
    @Test
    public void test1(){
        Scanner scanner1 = new Scanner(new BufferedReader(new StringReader("a\r1")));
        Scanner scanner2 = new Scanner("a\r1");
        printScanner(scanner1);
        printScanner(scanner2);
        System.out.println(scanner1.ioException());
    }

    private static void printScanner(Scanner scanner){
        String name = scanner.next();
        System.out.println(scanner.hasNextInt());
        System.out.println(scanner.hasNext("\\W"));
        int age = scanner.nextInt();
        System.out.println("name:" + name + "; age:" + age);
    }

    /**
     * scanner 与 正则
     */
    @Test
    public void test2(){
        Scanner scanner = new Scanner("a,b 1");
        while (scanner.hasNext())
            System.out.println(scanner.next());
        System.out.println("delimiter:" + scanner.delimiter());
        // 改变输入分词
        System.out.println("改变输入分词");
        scanner = new Scanner("a,b 1");
        scanner.useDelimiter("[\\s*,]");
        while (scanner.hasNext())
            System.out.println(scanner.next());
    }

    /**
     * 正则匹配扫描
     */
    @Test
    public void test3(){
        String str = "2018/10/01:放假 2018/10/07:放假结束1";// 第二个不能完全匹配，不能输出
        Pattern pattern = Pattern.compile("(\\d+/\\d+/\\d+):(\\W+?)");
        Scanner scanner = new Scanner(str);
        while (scanner.hasNext(pattern)){
            scanner.next(pattern);
            MatchResult matchResult = scanner.match();
            System.out.format("日期：%s  ,  事件：%s",matchResult.group(1),matchResult.group(2));
            System.out.println();
        }
        // 注意scanner匹配正则时，一次只匹配一个输入分词
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test4(){
        StringTokenizer stringTokenizer = new StringTokenizer("1 2 3,4"," ");
        while (stringTokenizer.hasMoreTokens())
            System.out.println(stringTokenizer.nextToken());
        stringTokenizer = new StringTokenizer("1 2 3,4",",");
        while (stringTokenizer.hasMoreTokens())
            System.out.println(stringTokenizer.nextToken());
        stringTokenizer = new StringTokenizer("1 2 3,4",",",true);
        while (stringTokenizer.hasMoreTokens())
            System.out.println(stringTokenizer.nextToken());
    }
}
