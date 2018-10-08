package top.itlq.thinkingInJava.typeinfo_14.classObject;

public class Toy {
    static {
        System.out.println("Toy的static块!");
    }
    Toy(){
        System.out.println("Toy的构造器！");
    }
}
