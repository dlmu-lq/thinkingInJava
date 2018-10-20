/**
 * 代理实现了与应用类同样的方法接口，调用相同方法时可执行额外操作
 * 代理可以将应用执行的业务与额外操作分离，实现了一层封装
 * 例如，度量开销，执行次数
 */
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
