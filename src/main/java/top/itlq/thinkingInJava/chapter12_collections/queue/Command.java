package top.itlq.thinkingInJava.chapter12_collections.queue;

public class Command {
    String name;
    public Command(String name){
        this.name = name;
    }
    public void operation(){
        System.out.println(name);
    }
}
