package top.itlq.thinkingInJava.chapter09_polymorphism;

public class TestChild extends TestBase{
    public int attr;

    public TestChild(){
        attr = 2;
    }

    public static void f(){
        System.out.println("child f()");
    }
}
