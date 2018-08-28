package top.itlq.thinkingInJava.exceptions_12;

class MyException1 extends Exception{}
class MyException2 extends Exception{}
class TestExtendsP{
    protected void f() throws MyException1{}
    protected void f2() throws MyException1{}
}
interface TestImplementsP{
    void f() throws MyException2;
}
public class TestExtendsException extends TestExtendsP implements TestImplementsP{

    // 为了保证多态的有效性，这种情况下，
    // 异常被限制，派生类不能抛出任何异常，只要抛出，使用其基类或接口时便会出问题；
    @Override
    public void f(){}
    // 可以说明不抛异常，也可以说明抛出基类的异常；不能抛出基类未说明的异常；
    @Override
    public void f2() throws MyException1{}
}
