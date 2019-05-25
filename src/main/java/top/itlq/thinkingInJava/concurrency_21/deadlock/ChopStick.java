package top.itlq.thinkingInJava.concurrency_21.deadlock;

public class ChopStick {
    private int id;
    private boolean taken = false;
    public ChopStick(int id){
        this.id = id;
    }
    public synchronized void take(){
        try {
            while (taken) {
                wait();
            }
            taken = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void drop(){
        taken = false;
        notifyAll();
    }
}
