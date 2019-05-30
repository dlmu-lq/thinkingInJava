package top.itlq.thinkingInJava.concurrency_21.threadBasic;

import java.util.concurrent.TimeUnit;

class Sleeper extends Thread{
    private int milliseconds;
    public Sleeper(String name, int milliseconds){
        super(name);
        this.milliseconds = milliseconds;
        this.start();
    }
    @Override
    public void run(){
        try {
            System.out.println(this + " sleeping...");
            TimeUnit.MILLISECONDS.sleep(milliseconds);
            System.out.println(this + " awaken");
        } catch (InterruptedException e) {
            // 抛出异常时中断状态已经被重置
            System.out.println("this.isInterrupted(): " + this.isInterrupted());
            System.out.println(this + " interrupted");
        }
    }
}

class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        this.start();
    }
    @Override
    public void run(){
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " finished");
    }
}

public class Joining {
    public static void main(String...args){
        Sleeper s1 = new Sleeper("s1", 1000),
                s2 = new Sleeper("s2", 1500);
        Joiner j1 = new Joiner("j1", s1),
                j2 = new Joiner("j2", s2);
        s2.interrupt();
    }
}
