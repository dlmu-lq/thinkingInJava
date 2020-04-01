package top.itlq.thinkingInJava.chapter08_reuse;

public class TestPrivateBase {
    private void f(){
        System.out.println("base f()");
    }

    public static void main(String[] args) {
        TestPrivateChild testPrivateChild = new TestPrivateChild();
        testPrivateChild.f();
        TestPrivateBase testPrivateBase = new TestPrivateChild();
        // 类内部private方法调用，编译器确定；
        testPrivateBase.f();
        // 转型后，调用的是子类的
        ((TestPrivateChild) testPrivateBase).f();
    }
}
