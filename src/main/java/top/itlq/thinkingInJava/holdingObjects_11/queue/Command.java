package top.itlq.thinkingInJava.holdingObjects_11.queue;

public class Command {
    String name;
    public Command(String name){
        this.name = name;
    }
    public void operation(){
        System.out.println(name);
    }
}
