package top.itlq.thinkingInJava.typeinfo_14.classObject;

public class Toy {
    static {
        System.out.println("Toy的static块!");
    }
    Toy(){
        System.out.println("Toy的构造器！");
    }
    public Toy(int i){
        System.out.println("Toy的构造器！" + i);
    }

    void test1(){

    }
    public void test2(){

    }
}
