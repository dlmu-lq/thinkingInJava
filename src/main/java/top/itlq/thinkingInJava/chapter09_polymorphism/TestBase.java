package top.itlq.thinkingInJava.chapter09_polymorphism;

public class TestBase {
    public int attr;

    TestBase(){
        attr = 1;
    }

    public int getAttr() {
        return attr;
    }

    public static void f(){
        System.out.println("base f()");
    }
}
