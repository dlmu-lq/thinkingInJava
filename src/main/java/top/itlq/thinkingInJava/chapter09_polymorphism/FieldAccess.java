package top.itlq.thinkingInJava.chapter09_polymorphism;

public class FieldAccess {
    public static void main(String[] args) {
        TestBase testBase = new TestChild();
        System.out.println(testBase.attr);
        // 方法，运行期绑定，
        // 如果子类没有继承方法，会返回父类的属性值
        System.out.println(testBase.getAttr());
        System.out.println(((TestChild) testBase).attr);
        // 静态方法，编译器解析
        testBase.f();
        ((TestChild)testBase).f();
    }
}
