package top.itlq.thinkingInJava.typeinfo_14.proxy;

public class SimpleProxy implements Interface{
    private Interface proxied;

    public SimpleProxy(){

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void doSomethingElse() {

    }
}

class RealObject implements Interface{

    @Override
    public void doSomething() {
        System.out.println("real doSomething");
    }

    @Override
    public void doSomethingElse() {
        System.out.println("real doSomethingElse");
    }
}
