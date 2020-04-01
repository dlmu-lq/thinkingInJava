package top.itlq.thinkingInJava.chapter11_innerclasses;

import org.junit.jupiter.api.Assertions;

/**
 * 内部类 .this 和 .new
 */
public class OuterTest {
    public class InnerTest{
        OuterTest outer(){
            return OuterTest.this;
        }
    }

    public static void main(String[] args) {
        OuterTest outerTest = new OuterTest();
        InnerTest innerTest = outerTest.new InnerTest();
        Assertions.assertEquals(outerTest, innerTest.outer());
    }
}
