package top.itlq.thinkingInJava.concurrency_21.deadlock;

import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable{
    private int id;
    private ChopStick left;
    private ChopStick right;
    private int pondFactor;
    public Philosopher(ChopStick left, ChopStick right, int id, int pondFactor){
        this.left = left;
        this.right = right;
        this.id = id;
        this.pondFactor = pondFactor;
    }
    public void pause() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(pondFactor * 250);
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                System.out.println(this + " start thinking");
                pause();
                System.out.println(this + " start taking chopsticks");
                left.take();
                right.take();
                System.out.println(this + " start eating");
                pause();
                System.out.println(this + " finished eating");
                left.drop();
                right.drop();
            }
        }catch (InterruptedException e){
            System.out.println(this + "interrupted");
        }
    }
    @Override
    public String toString(){
        return "Philosopher #" + id;
    }
}
