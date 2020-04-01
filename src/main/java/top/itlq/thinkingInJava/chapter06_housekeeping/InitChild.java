package top.itlq.thinkingInJava.chapter06_housekeeping;

public class InitChild extends InitBase{
    {
        System.out.println("child {}");
    }
    public InitChild(){
        System.out.println("child constructor");
    }
    public InitChild(int i){
        super(i);
        System.out.println("child constructor");
    }

    public static void main(String[] args) {
        new InitChild(1);
    }
}
