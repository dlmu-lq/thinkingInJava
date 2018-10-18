package top.itlq.thinkingInJava.typeinfo_14.proxy;

public class SimpleProxy implements Interface{
    private Interface proxied;

    public SimpleProxy(Interface proxied){
        this.proxied = proxied;
    }

    @Override
    public void doSomething() {
        System.out.println("proxy doSomething");
        proxied.doSomething();
    }

    @Override
    public void doSomethingElse() {
        System.out.println("proxy doSomethingElse");
        proxied.doSomethingElse();
    }

    static void consumer(Interface obj){
        obj.doSomething();
        obj.doSomethingElse();
    }

    public static void main(String...args){
        SimpleProxy simpleProxy = new SimpleProxy(new RealObject());
        consumer(simpleProxy);
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
