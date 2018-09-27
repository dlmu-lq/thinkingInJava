/**
 * 使用BufferedReader 扫描输入流
 */
package top.itlq.thinkingInJava.string_13.regex.read;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class SimpleRead {
    // 控制台输入
    public static void main(String...args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name?");
        String name = br.readLine();
        System.out.println("Enter your age?");
        int age = Integer.valueOf(br.readLine());
        System.out.println("name:" + name + "; age:" + age);
    }

    @Test
    public void test2() throws Exception{
        BufferedReader br = new BufferedReader(new StringReader("abc\r12"));
        String name = br.readLine();
        int age = Integer.valueOf(br.readLine());
        System.out.println("name:" + name + "; age:" + age);
    }
}
